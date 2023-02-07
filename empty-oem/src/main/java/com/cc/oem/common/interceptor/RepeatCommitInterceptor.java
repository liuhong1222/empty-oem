/**
 *
 */
package com.cc.oem.common.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cc.oem.common.annotation.RepeatCommitToken;
import com.cc.oem.common.redis.RedisClient;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cc.oem.common.utils.RedisKeys;
import com.cc.oem.common.utils.ShiroUtils;


/**
 * @author ChuangLan
 */
@Component
public class RepeatCommitInterceptor {

    /**
     * repeat commit token
     */
    private static int repeatTokenExpireSeconds = 30;

    /**
     * 重复提交token,存放heards
     */
    private static final String HEADER_NAME = "commit_token";

    @Autowired
    private RedisClient redisClient;

    /**
     * This implementation always returns {@code true}.
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean result = true;

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatCommitToken annotation = method.getAnnotation(RepeatCommitToken.class);

            if (annotation != null) {

                boolean validate = annotation.validate();
                SysUserEntity user = ShiroUtils.getUserEntity();
                String redisValue = redisClient.get(RedisKeys.getUserRepeatCommitKey(user.getId()) + method.getName());
                if (validate) {
                    String commitToken = request.getHeader(HEADER_NAME);
                    if (StringUtils.isEmpty(commitToken)) {
                        for (Cookie cookie : request.getCookies()) {
                            if (cookie.getName().equals(HEADER_NAME)) {
                                commitToken = cookie.getValue();
                                break;
                            }
                        }
                    }
                    if (user != null && user.getId() != null) {

                        if (StringUtils.isNotBlank(commitToken) && commitToken.equals(redisValue) && redisValue != null) {
                            result = false;
                        } else {
                            result = true;
                        }
                    }
                }
                if (user != null && user.getId() != null && result && redisValue == null) {
                    String randomStr = UUID.randomUUID().toString();
                    redisClient.set(RedisKeys.getUserRepeatCommitKey(user.getId()) + method.getName(), randomStr, repeatTokenExpireSeconds);
                    Cookie cookie = new Cookie(HEADER_NAME, randomStr);
                    cookie.setHttpOnly(true);
                    cookie.setPath("/");
                    cookie.setSecure(false);
                    cookie.setMaxAge(repeatTokenExpireSeconds);
                    response.addCookie(cookie);
                    //response.flushBuffer();
                }
            }
        }

        return result;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        SysUserEntity user = ShiroUtils.getUserEntity();
        if (handler instanceof HandlerMethod && user != null && user.getId() != null) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String redisValue = redisClient.get(RedisKeys.getUserRepeatCommitKey(user.getId()) + method.getName());
            if (redisValue != null) {
            	redisClient.remove(RedisKeys.getUserRepeatCommitKey(user.getId()) + method.getName());
            }
        }
    }


}
