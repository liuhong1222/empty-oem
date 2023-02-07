package com.cc.oem.modules.agent.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName Spring
 * author   zhangx
 * date     2018/11/14 15:32
 * description
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    public static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.context = applicationContext;
    }

    /// 获取当前环境
    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }
}
