package com.cc.oem.modules.agent.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 常见问题列表查询参数
 *
 * @author liuh
 */
@Data
public class ProductFaqSaveParam {
	/**
	 * 问题id
	 */
	private Long id;

	/**
	 * 产品id
	 */
	@NotNull(message = "产品id不能为空")
	private Long productId;

	/**
	 * 状态 0-上架  1-下架
	 */
	@NotNull(message = "状态不能为空")
	private Integer status;

	/**
	 * 排序
	 */
	@NotNull(message = "排序号不能为空")
	private Integer order;

	/**
	 * 问题标题
	 */
	@NotBlank(message = "问题标题不能为空")
	private String question;

	/**
	 * 答案
	 */
	@NotBlank(message = "问题答案不能为空")
	private String answer;
}
