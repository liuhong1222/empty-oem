package com.cc.oem.modules.agent.entity;

/**
 * 代理商级别关联
 */
public class TempEntity {

    /**
     * 代理商id
     */
    private Long agentId;

    /**
     * 产品id或产品线id
     */
    private Long objectId;

    public TempEntity() {
    	
    }
    
    public TempEntity(Long agentId,Long objectId) {
    	this.agentId = agentId;
    	this.objectId = objectId;
    }

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
}
