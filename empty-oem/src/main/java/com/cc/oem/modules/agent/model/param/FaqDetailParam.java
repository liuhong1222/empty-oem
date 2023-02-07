package com.cc.oem.modules.agent.model.param;

import lombok.Data;

/**
 * 代理商常见问题详情参数
 *
 */
@Data
public class FaqDetailParam {
    /**
     * 常见问题id
     */
    private String productFaqId;

    /**
     * 代理商id
     */
    private Long agentId;
	
	public FaqDetailParam(String productFaqId , Long agentId) {
		this.productFaqId = productFaqId;
		this.agentId = agentId;
	}
}
