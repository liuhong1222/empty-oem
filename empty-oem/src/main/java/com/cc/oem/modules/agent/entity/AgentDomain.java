package com.cc.oem.modules.agent.entity;

import java.util.Date;

/**
 * 代理商域名
 */
public class AgentDomain {
    /**
     * 主键
     */
    private Long id;

    /**
     * 代理商id
     */
    private Long agentId;

    /**
     * 代理商域名
     */
    private String name;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 电信许可
     */
    private String licence;

    /**
     * 版权信息
     */
    private String copyright;

    /**
     * icp备案
     */
    private String icpRecord;

    /**
     * 公安备案
     */
    private String policeRecord;

    /**联系方式 20181208添加*/
    private String contactInfo;

    private Date createTime;

    private Date updateTime;
    private String address;

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence == null ? null : licence.trim();
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright == null ? null : copyright.trim();
    }

    public String getIcpRecord() {
        return icpRecord;
    }

    public void setIcpRecord(String icpRecord) {
        this.icpRecord = icpRecord == null ? null : icpRecord.trim();
    }

    public String getPoliceRecord() {
        return policeRecord;
    }

    public void setPoliceRecord(String policeRecord) {
        this.policeRecord = policeRecord == null ? null : policeRecord.trim();
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