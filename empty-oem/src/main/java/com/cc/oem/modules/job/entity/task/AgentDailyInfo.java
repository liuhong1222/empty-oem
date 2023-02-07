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
public class AgentDailyInfo implements Serializable {

	private static final long serialVersionUID = 14L;


	private Long id;

	/**
	 * 代理商id
	 */
	private Long agentId;



	/**
	 * 日期，格式YYYYmmdd
	 */
	private Long dayInt;

	/**
	 * 空号充值条数
	 */
	private Long emptyRechargeNum=0l;

	/**
	 * 空号充值金额
	 */
	private Long emptyRechargeMoney=0l;
	
	/**
	 * 空号消耗条数
	 */
	private Long emptyConsume=0l;
	
	/**
	 * 空号剩余条数
	 */
	private Long emptyCounts=0l;

	/**
	 * 实时充值条数
	 */
	private Long realtimeRechargeNum=0l;

	/**
	 * 实时充值金额
	 */
	private Long realtimeRechargeMoney=0l;
	
	/**
	 * 实时消耗条数
	 */
	private Long realtimeConsume=0l;
	
	/**
	 * 实时剩余条数
	 */
	private Long realtimeCounts=0l;
	
	/**
	 * 国际充值条数
	 */
	private Long internationalRechargeNum=0l;

	/**
	 * 国际充值金额
	 */
	private Long internationalRechargeMoney=0l;
	
	/**
	 * 国际消耗条数
	 */
	private Long internationalConsume=0l;
	
	/**
	 * 国际剩余条数
	 */
	private Long internationalCounts=0l;
	
	/**
	 * 定向通用充值条数
	 */
	private Long directCommonRechargeNum=0l;

	/**
	 * 定向通用充值金额
	 */
	private Long directCommonRechargeMoney=0l;
	
	/**
	 * 定向通用消耗条数
	 */
	private Long directCommonConsume=0l;
	
	/**
	 * 定向通用余额
	 */
	private Long directCommonCounts;
	
	/**
	 * line定向充值条数
	 */
	private Long lineDirectRechargeNum=0l;

	/**
	 * line定向充值金额
	 */
	private Long lineDirectRechargeMoney=0l;
	
	/**
	 * line定向消耗条数
	 */
	private Long lineDirectConsume=0l;
	
	/**
	 * line定向余额
	 */
	private Long lineDirectCounts;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 客户数
	 */
	private Integer custNum=0;
	/**
	 * 日增加客户数
	 */
	private Integer dailyAddCustNum=0;

	/**
	 * 产品种类，0空号，1实时
	 */
	private Integer category;

	/**
	 *
	 */
	private Long totalNum;
	/**
	 *
	 */
	private Long totalPayment;

	private String companyName;












}
