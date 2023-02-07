package com.cc.oem.modules.agent.model.data;

import lombok.Data;

import java.util.Date;

/**
 * @author liuh
 */
@Data
public class FaqInfoData {

	private Long id;
	private String agentName;
	private String productName;
	private String title;
	private String content;
	private String state;
	private Date createTime;
	private Date updateTime;
	private Integer sort;
	private String applyState;
	private String remark;


}
