package com.cc.oem.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.cc.oem.modules.sys.entity.SysUserEntity;


public interface SysSendMsgService extends IService<SysUserEntity> {

    boolean SendMsg(String mobile);

    void SendNotifyMsg(String mobile,String msg);

}
