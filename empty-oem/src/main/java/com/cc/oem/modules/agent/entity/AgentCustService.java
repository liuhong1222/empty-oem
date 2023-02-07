package com.cc.oem.modules.agent.entity;

import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class AgentCustService {
    private Long id;

    private Long agentId;

    private String hotline;

    private String qq;

    private String bizNo;

    private Byte meiqiaStatus;

    private String meiqiaEntid;

    private Byte rightDisplay;

    private String weixinUrl;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline == null ? null : hotline.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo == null ? null : bizNo.trim();
    }

    public Byte getMeiqiaStatus() {
        return meiqiaStatus;
    }

    public void setMeiqiaStatus(Byte meiqiaStatus) {
        this.meiqiaStatus = meiqiaStatus;
    }

    public String getMeiqiaEntid() {
        return meiqiaEntid;
    }

    public void setMeiqiaEntid(String meiqiaEntid) {
        this.meiqiaEntid = meiqiaEntid == null ? null : meiqiaEntid.trim();
    }

    public Byte getRightDisplay() {
        return rightDisplay;
    }

    public void setRightDisplay(Byte rightDisplay) {
        this.rightDisplay = rightDisplay;
    }

    public String getWeixinUrl() {
        if(StringUtils.isBlank(weixinUrl)){
            return null;
        }
        return weixinUrl;
    }

    public void setWeixinUrl(String weixinUrl) {
        this.weixinUrl = weixinUrl == null ? null : weixinUrl.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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