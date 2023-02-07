package com.cc.oem.modules.agent.model.param;

/**
 * author zhangx
 * date  2019/3/21 15:34
 */
public class ProductListParam extends TimesParam {
    /**
     * 代理商id
     */
    private Long agentId;
    /**
     * '0上架，1下架
     */
    private Integer shelfStatus;

    /**
     * 0待审核,1审核通过，2审核驳回
     */
    private Integer auditStatus;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Integer getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(Integer shelfStatus) {
        this.shelfStatus = shelfStatus;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
}
