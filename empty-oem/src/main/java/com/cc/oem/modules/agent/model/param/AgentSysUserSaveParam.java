package com.cc.oem.modules.agent.model.param;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author chenzj
 * @since 2018/8/17
 */
public class AgentSysUserSaveParam {

    @ApiModelProperty("姓名")
    @NotBlank(message = "姓名不能为空")
    private String realName;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    private String password;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
