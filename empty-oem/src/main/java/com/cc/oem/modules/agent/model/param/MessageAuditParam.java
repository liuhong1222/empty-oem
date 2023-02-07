package com.cc.oem.modules.agent.model.param;

import javax.validation.constraints.NotNull;

/**
 * 消息审核参数
 *
 * @author chenzj
 */
public class MessageAuditParam {
    /**
     * 消息id
     */
    @NotNull(message = "消息id不能为空")
    private String agentMessageId;

    /**
     * 审批状态，0：初始化，1：创建待审核，2：修改待审核，3：已审核，4：已驳回，5：已删除
     */
    @NotNull(message = "审核状态不能为空")
    private Integer auditState;

    /**
     * 审核备注信息
     */
    private String auditRemark;

    public String getAgentMessageId() {
        return agentMessageId;
    }

    public void setAgentMessageId(String agentMessageId) {
        this.agentMessageId = agentMessageId;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }
}
