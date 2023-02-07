package com.cc.oem.modules.agent.util;

import com.cc.oem.modules.agent.service.impl.UploadPictureServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 *
 */
@Component
public class SpringBeanUtil{
    private static final Logger logger = LoggerFactory.getLogger(SpringBeanUtil.class);

    public static ApplicationContext context = null;

    public static Integer helpUpdateColumn(Class c,Object source, Object target){
        Integer count = 0;
        Field[] declaredFields = c.getDeclaredFields();
        for(Field f:declaredFields){
            f.setAccessible(true);
            try {
                if(f.get(source)!=null&&f.get(source).equals(f.get(target))) {
                    f.set(target, null);
                }else if(f.get(target)==null){

                }
                else{
                    count++;
                }
            } catch (IllegalAccessException e) {
                logger.error("检查所有更改字段时发生异常，异常信息：",e);
            }
        }
        return count;
    }











































































































}
