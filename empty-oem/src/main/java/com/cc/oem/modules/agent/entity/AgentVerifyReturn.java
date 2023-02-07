package com.cc.oem.modules.agent.entity;

/**
 * 代理商级别关联
 */
public class AgentVerifyReturn {
    /**
     * 营业执照图片地址
     */
    private String licenseUrl;

    /**
     *代理商等级名称
     */
    private String levelName;

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	public AgentVerifyReturn() {
		
	}
	
	public AgentVerifyReturn(String licenseUrl,String levelName) {
		this.licenseUrl = licenseUrl;
		this.levelName = levelName;
	}
}
