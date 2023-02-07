package com.cc.oem.common.aspect;

import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.HttpContextUtils;
import com.cc.oem.common.utils.IPUtils;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.entity.SysLogEntity;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.service.SysLogService;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志，切面处理类
 *
 * @author wade
 */
@Aspect @Component public class SysLogAspect {
    @Autowired private SysLogService sysLogService;

    @Autowired
    private Snowflake snowflake;

    @Qualifier("taskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Pointcut("@annotation(com.cc.oem.common.annotation.SysLog)") public void logPointCut() {

    }


    @Around("logPointCut()") public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        executor.execute(() -> {
            //保存日志
            saveSysLog(point,time,request);
        });
        return result;
    }


    private void saveSysLog(ProceedingJoinPoint joinPoint, long time,HttpServletRequest request) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLogEntity sysLog = new SysLogEntity();
        sysLog.setType(request.getMethod());
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setOperation(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = new Gson().toJson(args[0]);
            sysLog.setContent(params);
        } catch (Exception e) {

        }


        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        //用户名
        try {
            Long userId = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getId();
            sysLog.setCreateId(userId);
        } catch (Exception e) {
            System.out.println("该用户已经不在线，操作不计入日志");
        }

        sysLog.setTime(time);
        sysLog.setCreateTime(new Date());
        //保存系统日志
        sysLog.setLogId(snowflake.nextId());
        sysLogService.insert(sysLog);
    }
}
