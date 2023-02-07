package com.cc.oem.modules.agent.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商账户
 */
@Data
public class AgentAccount {
    /**
     * 主键
     */
    private Long id;

    /**
     * 代理商id
     */
    private Long agentId;

    private Long emptyBalance = 0L;

    private BigDecimal emptyRechargeMoney = BigDecimal.ZERO;;

    private Long emptyRechargeNumber = 0L;

    private Long realtimeBalance = 0L;

    private BigDecimal realtimeRechargeMoney = BigDecimal.ZERO;;

    private Long realtimeRechargeNumber = 0L;
    
    private Long directCommonBalance = 0L;

    private BigDecimal directCommonRechargeMoney = BigDecimal.ZERO;;

    private Long directCommonRechargeNumber = 0L;
    
    private Long lineDirectBalance = 0L;

    private BigDecimal lineDirectRechargeMoney = BigDecimal.ZERO;;

    private Long lineDirectRechargeNumber = 0L;
    
    private Long internationalBalance = 0L;

    private BigDecimal internationalRechargeMoney = BigDecimal.ZERO;;

    private Long internationalRechargeNumber = 0L;
    
    private BigDecimal custRechargeMoney = BigDecimal.ZERO;
    private Long custRechargeNum = 0L;
    private BigDecimal custRealtimeRechargeMoney = BigDecimal.ZERO;;
    private Long custRealtimeRechargeNum = 0L;
    private BigDecimal custInternationalRechargeMoney = BigDecimal.ZERO;;
    private Long custInternationalRechargeNum = 0L;
    
    private BigDecimal custDirectCommonRechargeMoney = BigDecimal.ZERO;;
    private Long custDirectCommonRechargeNum = 0L;
    private BigDecimal custLineDirectRechargeMoney = BigDecimal.ZERO;;
    private Long custLineDirectRechargeNum = 0L;
    
    private Date createTime;

    private Date updateTime;

    private Integer category;

}