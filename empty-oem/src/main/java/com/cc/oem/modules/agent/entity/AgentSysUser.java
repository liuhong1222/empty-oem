package com.cc.oem.modules.agent.entity;

import java.util.Date;

/**
 * 代理商和账号关联
 */
public class AgentSysUser {
    /**
     * 主键
     */
    private Long id;

    /**
     * 代理商id
     */
    private Long agentId;

    /**
     * 账号id
     */
    private Long sysUserId;

    /**
     * 主账号标识  0-主账号   1-副账号
     */
    private Integer flag;

    /**
     * 创建时间
     */
    private Date createTime;

    public AgentSysUser() {
    	
    }
    
    public AgentSysUser(Long agentId,Long sysUserId,Integer flag) {
    	this.flag = flag;
    	this.agentId = agentId;
    	this.sysUserId = sysUserId;
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

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
