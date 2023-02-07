package com.cc.oem.modules.job.entity.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  dell
 * @date 2021-11-10 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustEmptyInfo implements Serializable {

	private static final long serialVersionUID = 14L;


	private Long id;

	/**
	 * 代理商id
	 */
	private Long agentId;

	private Long customerId;

	/**
	 * 日期，格式YYYYmmdd
	 */
	private Long dayInt;

	/**
	 * 空号统计参数
	 */
	/**
	 * 实号
	 */
	private Long realNumber;
	/**
	 * 沉默
	 */
	private Long silentNumber;
	/**
	 * 空号
	 */
	private Long emptyNumber;
	/**
	 * 风险号
	 */
	private Long riskNumber;


	/**
	 * 空号检测总条数
	 */
	private Long emptyTotal=0l;

	private String customerName;

	private String phone;

	private String companyName;
}
