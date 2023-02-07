package com.cc.oem.config;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.cc.oem.common.xss.XssFilter;

/**
 * Filter配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-21 21:56
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> shiroFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean<Filter> xssFilterRegistration() {
        //配置无需过滤的路径或者静态资源，如：css，imgage等，多个用","隔开
        StringBuffer excludedUriStr = new StringBuffer();
        excludedUriStr.append("/open/agent/news/**");
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("excludedUri", excludedUriStr.toString());
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}
