package com.cc.oem.modules.agent.model.data;

import lombok.Data;

/**
 * @author liuh
 */
@Data
public class FaqInfoWithUpdateData {

	private Long id;
	private Long productId;
	private String productName;
	private String title;
	private String content;
	private Integer state;
	private Integer orders;
	private Integer sort;

}
