package com.cc.oem.modules.agent.model.param;

import javax.validation.constraints.NotEmpty;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName UpdateSysUserMobileParam
 * author   zhangx
 * date     2018/8/16 16:02
 * description
 */
public class UpdateSysUserMobileParam {
    @NotEmpty(message = "旧手机号不能为空")
    private String oldMobile;
    @NotEmpty(message = "旧手机号验证码不能为空")
    private String oldCode;
    @NotEmpty(message = "新手机号不能为空不能为空")
    private String newMobile;
    @NotEmpty(message = "新手机号验证码不能为空")
    private String newCode;

    public String getOldMobile() {
        return oldMobile;
    }

    public void setOldMobile(String oldMobile) {
        this.oldMobile = oldMobile;
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }
}
