package com.cc.oem.modules.config.resolver;

import com.cc.oem.modules.config.interceptor.AuthorizationInterceptor;
import com.cc.oem.modules.config.annotation.LoginUser;
import com.cc.oem.modules.config.entity.UserEntity;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 22:02
 */
@Component public class LoginUserHandlerMethodArgumentResolver
        implements HandlerMethodArgumentResolver {

    @Override public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserEntity.class) && parameter
                .hasParameterAnnotation(LoginUser.class);
    }


    @Override public Object resolveArgument(MethodParameter parameter,
                                            ModelAndViewContainer container,
                                            NativeWebRequest request, WebDataBinderFactory factory)
            throws Exception {
        //获取用户ID
        Object object = request
                .getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (object == null) {
            return null;
        }

        //获取用户信息
        UserEntity user = this.getById((Long) object);

        return user;
    }


    private UserEntity getById(Long userId) {
        //:TODO 根据ID获取用户信息
        return new UserEntity();
    }
}