package com.cc.oem.modules.agent.model.param;

/**
 * 是否存在常见问题记录查询参数
 *
 * @author liuh
 * @since 2019/03/25
 */
public class IsExistFaqParam {
	
	 /**
     * 问题
     */
    private String question;
    
    /**
     * 答案
     */
    private String answer;
    
    /**
     * 代理商id
     */
    private Long agentId;
    
    /**
     * 问题id
     */
    private String productFaqId;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getProductFaqId() {
		return productFaqId;
	}

	public void setProductFaqId(String productFaqId) {
		this.productFaqId = productFaqId;
	}

	public IsExistFaqParam(String question,String answer,Long agentId,String productFaqId) {
		this.question = question;
		this.answer = answer;
		this.agentId = agentId;
		this.productFaqId = productFaqId;
	}
}
