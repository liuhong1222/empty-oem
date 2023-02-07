package com.cc.oem.modules.agent.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AgentTrdOrderId {
    private Long id;

    private Long trdOrderId;

    private Long creUserId;

    private String creUserName;

    private String creUserPhone;

    private Integer creUserType;

    private BigDecimal trdOrderPrice;

    private Long trdOrderProductId;

    private String trdOrderPackageName;

    private String remark;

    private Long agentId;

    private Long agentOrderId;

    private String agentCompanyName;

    private String agentMobile;

    private Integer updated;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrdOrderId() {
        return trdOrderId;
    }

    public void setTrdOrderId(Long trdOrderId) {
        this.trdOrderId = trdOrderId;
    }

    public Long getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(Long creUserId) {
        this.creUserId = creUserId;
    }

    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName == null ? null : creUserName.trim();
    }

    public String getCreUserPhone() {
        return creUserPhone;
    }

    public void setCreUserPhone(String creUserPhone) {
        this.creUserPhone = creUserPhone == null ? null : creUserPhone.trim();
    }

    public Integer getCreUserType() {
        return creUserType;
    }

    public void setCreUserType(Integer creUserType) {
        this.creUserType = creUserType;
    }

    public BigDecimal getTrdOrderPrice() {
        return trdOrderPrice;
    }

    public void setTrdOrderPrice(BigDecimal trdOrderPrice) {
        this.trdOrderPrice = trdOrderPrice;
    }

    public Long getTrdOrderProductId() {
        return trdOrderProductId;
    }

    public void setTrdOrderProductId(Long trdOrderProductId) {
        this.trdOrderProductId = trdOrderProductId;
    }

    public String getTrdOrderPackageName() {
        return trdOrderPackageName;
    }

    public void setTrdOrderPackageName(String trdOrderPackageName) {
        this.trdOrderPackageName = trdOrderPackageName == null ? null : trdOrderPackageName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getAgentOrderId() {
        return agentOrderId;
    }

    public void setAgentOrderId(Long agentOrderId) {
        this.agentOrderId = agentOrderId;
    }

    public String getAgentCompanyName() {
        return agentCompanyName;
    }

    public void setAgentCompanyName(String agentCompanyName) {
        this.agentCompanyName = agentCompanyName == null ? null : agentCompanyName.trim();
    }

    public String getAgentMobile() {
        return agentMobile;
    }

    public void setAgentMobile(String agentMobile) {
        this.agentMobile = agentMobile == null ? null : agentMobile.trim();
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
