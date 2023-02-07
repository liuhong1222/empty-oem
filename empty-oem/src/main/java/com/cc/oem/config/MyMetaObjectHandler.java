package com.cc.oem.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.common.utils.ShiroUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

public class MyMetaObjectHandler extends MetaObjectHandler {

    protected final static Logger logger = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Object createTime = getFieldValByName("createTime", metaObject);
        if (createTime == null) {
            setFieldValByName("createTime", time, metaObject);
        }
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (updateTime == null) {
            setFieldValByName("updateTime", time, metaObject);
        }
        Object createUser = getFieldValByName("createUser", metaObject);
        if (createUser == null) {
            if(getUser().getId() != null){
                setFieldValByName("createUser", getUser().getId().intValue(), metaObject);
            }
        }
        Object createUserName = getFieldValByName("createUserName", metaObject);
        if (createUserName == null) {
            setFieldValByName("createUserName", getUser().getUsername(), metaObject);
        }
        Object dataVersion = getFieldValByName("dataVersion", metaObject);
        if (dataVersion == null) {
            setFieldValByName("dataVersion", 1, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (updateTime == null) {
            setFieldValByName("updateTime", time, metaObject);
        }
        Object updateUser = getFieldValByName("updateUser", metaObject);
        if (updateUser == null) {
            if(getUser().getId() != null){
                setFieldValByName("updateUser",getUser().getId().intValue(), metaObject);
            }
        }
    }

    private SysUserEntity getUser(){
       SysUserEntity  user = ShiroUtils.getUserEntity();
       if(user == null){
           user = new SysUserEntity();
           user.setId(1L);
           user.setUsername("系统");
       }
       return user;
    }
}
