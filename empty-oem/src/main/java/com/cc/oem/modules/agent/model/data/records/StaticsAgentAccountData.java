package com.cc.oem.modules.agent.model.data.records;

import lombok.Data;

@Data
public class StaticsAgentAccountData {

    private Long totalEmptyRechargeMoney = 0L;
    private Long totalEmptyRechargeNumber = 0L;
    private Long totalEmptyBalance = 0L;

    private Long totalRealtimeRechargeNumber = 0L;
    private Long totalRealtimeRechargeMoney = 0L;
    private Long totalRealtimeBalance = 0L;
    
    private Long totalInternationalRechargeNumber = 0L;
    private Long totalInternationalRechargeMoney = 0L;
    private Long totalInternationalBalance = 0L;

    private Long totalDirectCommonRechargeNumber = 0L;
    private Long totalDirectCommonRechargeMoney = 0L;
    private Long totalDirectCommonBalance = 0L;
    
    private Long totalLineDirectRechargeNumber = 0L;
    private Long totalLineDirectRechargeMoney = 0L;
    private Long totalLineDirectBalance = 0L;
    
    /**
     * 客户充值总计
     */
    private Long custRechargeMoney = 0L;
    private Long custRechargeNum = 0L;
    private Long custRealtimeRechargeMoney = 0L;
    private Long custRealtimeRechargeNum = 0L;
    private Long custInternationalRechargeMoney = 0L;
    private Long custInternationalRechargeNum = 0L;
    
    private Long custDirectCommonRechargeMoney = 0L;
    private Long custDirectCommonRechargeNum = 0L;
    private Long custLineDirectRechargeMoney = 0L;
    private Long custLineDirectRechargeNum = 0L;
    
    private Long emptyConsume = 0L;
    private Long realTimeConsume = 0L;
    private Long internationalConsume = 0L;
    
    private Long lineDirectConsume = 0L;
    private Long directCommonConsume = 0L;
    

    /**
     * 代理商数量
     */
    private Integer agentNum = 0;

}
