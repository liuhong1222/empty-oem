package com.cc.oem.modules.agent.model.param;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author chenzj
 * @since 2018/8/17
 */
public class AgentSysUserUpdateParam extends AgentSysUserSaveParam {

    @ApiModelProperty("账户id")
    @NotNull(message = "账户id不能为空")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}


