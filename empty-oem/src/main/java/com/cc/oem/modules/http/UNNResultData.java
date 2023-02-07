package com.cc.oem.modules.http;

import java.util.Map;

/**
 * @author songb
 * @since  2018年3月29日
 *
 */
public class UNNResultData{

	/**
	 * 返回码
	 */
	private String resultCode;

	/**
	 * 返回消息
	 */
	private String resultMsg;
	
	/**
	 * data数据
	 */
	private Map<String, String> resultObj;


	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Map<String, String> getResultObj() {
		return resultObj;
	}


	public void setResultObj(Map<String, String> resultObj) {
		this.resultObj = resultObj;
	}
	
	
}
