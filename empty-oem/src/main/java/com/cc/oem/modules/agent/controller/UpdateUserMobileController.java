package com.cc.oem.modules.agent.controller;


import com.cc.oem.common.annotation.RepeatCommitToken;
import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.UpdateSysUserMobileParam;
import com.cc.oem.modules.agent.service.AgentDeskService;
import com.cc.oem.modules.agent.service.UpdateMobileService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * author zhangx
 * date  2018/8/16 13:07
 */
@RestController
@RequestMapping("/open/agent/sms")
public class UpdateUserMobileController {

    @Autowired
    UpdateMobileService updateMobileService;

    @Autowired
    AgentDeskService agentDeskService;

    /**
     * 发送验证码
     */
    @RepeatCommitToken
    @SysLog("发送验证码")
    @RequiresPermissions("open:agent:sms:sendVerifyCode")
    @PostMapping(value = "/sendVerifyCode")
    public R sendVerifyCode(@Valid String mobile) {
        return updateMobileService.sendVerifyCode(mobile);
    }

    /**
     * 更新手机号
     */
    @RepeatCommitToken
    @SysLog("更新手机号")
    @RequiresPermissions("open:agent:sms:updateMobile")
    @PostMapping(value = "/updateMobile")
    public R updateMobile(@Valid UpdateSysUserMobileParam param) {
        return updateMobileService.updateMobile(param);
    }

    /**
     * 检测账号手机号是否存在
     */
    @RequiresPermissions("open:agent:sms:checkMobile")
    @PostMapping(value = "/checkMobile")
    public R checkMobile(String mobile) {
        return updateMobileService.checkMobile(mobile);
    }


}
