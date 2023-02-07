package com.cc.oem.modules.agent.model.data;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 代理商总充值信息
 *
 */
@Data
public class AgentRechargeTotalData {

    /**
     * 代理商id
     */
    private Long agentId;

    /**
     * 空号检测剩余条数
     */
    private Long emptyBalance;

    /**
     * 空号检测充值总计
     */
    private Long emptyRechargeMoney;
    /**
     * 空号检测充值总计
     */
    private Long emptyRechargeNumber;

    /**
     * 实时检测充值剩余条数
     */
    private Long realtimeBalance;
    /**
     * 实时检测充值总计
     */
    private Long realtimeRechargeMoney;
    /**
     * 实时检测充值总计
     */
    private Long realtimeRechargeNumber;
    
    /**
     * 国际检测充值剩余条数
     */
    private Long internationalBalance;
    /**
     * 国际检测充值总计
     */
    private Long internationalRechargeMoney;
    /**
     * 国际检测充值总计
     */
    private Long internationalRechargeNumber;
    
    /**
     * 国际检测充值剩余条数
     */
    private Long directCommonBalance;
    /**
     * 国际检测充值总计
     */
    private Long directCommonRechargeMoney;
    /**
     * 国际检测充值总计
     */
    private Long directCommonRechargeNumber;
    
    /**
     * 国际检测充值剩余条数
     */
    private Long lineDirectBalance;
    /**
     * 国际检测充值总计
     */
    private Long lineDirectRechargeMoney;
    /**
     * 国际检测充值总计
     */
    private Long lineDirectRechargeNumber;

}
