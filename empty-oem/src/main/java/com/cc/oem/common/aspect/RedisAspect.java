package com.cc.oem.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Redis切面处理类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-17 23:30
 */
public class RedisAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //是否开启redis缓存  true开启   false关闭
    @Value("${spring.redis.open: false}")
    private boolean open;

//    @Around("execution(* com.cc.oem.common.redis.RedisClient.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
//        Object result = null;
//        if(open){
//            try{
//                result = point.proceed();
//            }catch (Exception e){
//                logger.error("redis error", e);
//                throw new RRException("Redis服务异常");
//            }
//        }
        return null;
    }
}
