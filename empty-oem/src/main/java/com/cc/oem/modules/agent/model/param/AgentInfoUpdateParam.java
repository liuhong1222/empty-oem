package com.cc.oem.modules.agent.model.param;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 更新代理商参数
 *
 * @author chenzj
 * @since 2018/8/10
 */
public class AgentInfoUpdateParam extends AgentInfoSaveParam {

    @ApiModelProperty("代理商id")
    @NotNull(message = "代理商id不能为空")
    private Long agentId;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

}
