package com.cc.oem.modules.agent.model.data.records;

import lombok.Data;

/**
 * 工作台消耗趋势图数据
 * @author liuh
 * @date 2022年10月19日
 */
@Data
public class DeskConsumeData {

	private Integer dayInt;
	
	private Long totalConsume;
}
