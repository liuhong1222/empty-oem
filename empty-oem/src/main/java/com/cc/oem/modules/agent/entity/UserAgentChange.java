package com.cc.oem.modules.agent.entity;

import java.util.Date;

public class UserAgentChange {
    private Long id;

    private String mobile;

    private Integer creuserid;

    private String custName;

    private String outAgentName;

    private Long outAgentId;

    private String inAgentName;

    private Long inAgentId;

    private String remark;

    private Date createTime;

    private Date registerTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getCreuserid() {
        return creuserid;
    }

    public void setCreuserid(Integer creuserid) {
        this.creuserid = creuserid;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getOutAgentName() {
        return outAgentName;
    }

    public void setOutAgentName(String outAgentName) {
        this.outAgentName = outAgentName == null ? null : outAgentName.trim();
    }

    public Long getOutAgentId() {
        return outAgentId;
    }

    public void setOutAgentId(Long outAgentId) {
        this.outAgentId = outAgentId;
    }

    public String getInAgentName() {
        return inAgentName;
    }

    public void setInAgentName(String inAgentName) {
        this.inAgentName = inAgentName == null ? null : inAgentName.trim();
    }

    public Long getInAgentId() {
        return inAgentId;
    }

    public void setInAgentId(Long inAgentId) {
        this.inAgentId = inAgentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}