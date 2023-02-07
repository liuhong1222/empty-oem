package com.cc.oem.modules.agent.model.param;

import javafx.util.converter.TimeStringConverter;
import lombok.Data;

/**
 * 常见问题审核列表查询参数
 *
 * @author liuh
 */
@Data
public class ProductFaqParam extends TimesParam {
	/**
	 * 代理商id
	 */
	private Long agentId;

	/**
	 * 状态   0-上架   1-下架
	 */
	private String state;

	/**
	 * 审核状态 0-新增待审核  3-修改待审核 1-已审核 2-新增已驳回 4- 修改驳回
	 */
	private String applyState;

	/**
	 * 搜索类型  question-标题   answer-内容
	 */
	private String queryType;

	/**
	 * 搜索内容
	 */
	private String content;

	/**
	 *产品id
	 */
	private String productId;

}
