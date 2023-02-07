package com.cc.oem.modules.agent.entity;

import java.util.Date;

/**
 * 代理商微信资料
 */
public class AgentWeixinpay {
    /**
     * 主键
     */
    private Long id;

    /**
     * 代理商id
     */
    private Long agentId;

    /**
     * 微信appid
     */
    private String appid;

    /**
     * 微信商户号
     */
    private String mchid;

    /**
     * 微信key
     */
    private String wxkey;

    /**
     * 调用地址
     */
    private String callUrl;

    /**
     * 回调地址
     */
    private String callbackUrl;

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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid == null ? null : mchid.trim();
    }

    public String getWxkey() {
        return wxkey;
    }

    public void setWxkey(String wxkey) {
        this.wxkey = wxkey;
    }

    public String getCallUrl() {
        return callUrl;
    }

    public void setCallUrl(String callUrl) {
        this.callUrl = callUrl == null ? null : callUrl.trim();
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl == null ? null : callbackUrl.trim();
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
