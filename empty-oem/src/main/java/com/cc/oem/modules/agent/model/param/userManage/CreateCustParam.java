package com.cc.oem.modules.agent.model.param.userManage;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateCustParam {
    @NotEmpty(message = "请输入手机号")
    private String phone;
    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String pwd;
    /**
     * 第二次确认得密码
     */
    private String secondPwd;

    private Long agentId;
}
