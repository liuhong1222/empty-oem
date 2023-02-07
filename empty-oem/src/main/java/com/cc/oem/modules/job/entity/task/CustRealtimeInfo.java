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
public class CustRealtimeInfo implements Serializable {

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
	 * 实号
	 */
	private Long totalNumber;

	/**
	 * 实时统计参数
	 */
	/**
	 * 正常号
	 */
	private Long normalNumber;
	/**
	 * 空号
	 */
	private Long realtimeEmptyNumber;
	/**
	 * 通话中
	 */
	private Long oncallNumber;
	/**
	 * 不在网
	 */
	private Long notOnlineNumber;
	/**
	 * 关机
	 */
	private Long shutdownNumber;
	/**
	 * 疑似关机
	 */
	private Long likeShutdownNumber;
	/**
	 * 停机
	 */
	private Long tingjiNumber;
	/**
	 * 携号转网
	 */
	private Long mnpNumber;
	/**
	 * 号码错误
	 */
	private Long moberrNumber;
	/**
	 * 未知
	 */
	private Long unknownNumber;


	/**
	 * 统计类型
	 * 1，空号，2空号api，3实时，4实时api
	 *
	 */
	private Integer staticType;


	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 实时检测总条数
	 */
	private Long realtimeTotal=0l;

	private String customerName;

	private String phone;

	private String companyName;
}
