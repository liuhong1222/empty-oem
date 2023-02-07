package com.cc.oem.modules.agent.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomerBalance {

    private static final long serialVersionUID =  928240205394692646L;

    private Long id;

    /**
     * 用户id
     */
    private Long customerId;

    /**
     * 空号检测余额,单位：条
     */
    private Long emptyCount;

    /**
     * 实时检测余额,单位：条
     */
    private Long realtimeCount;
    
    /**
     * 国际检测余额,单位：条
     */
    private Long internationalCount;
    
    private Long directCommonCount = 0L;
    
    private Long lineDirectCount = 0L;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 空号充值条数
     */
    private Long emptyRechargeNum;

    /**
     * 空号充值金额
     */
    private BigDecimal emptyRechargeMoney;

    /**
     * 实时检测充值条数
     */
    private Long realtimeRechargeNum;

    /**
     * 实时检测充值金额
     */
    private BigDecimal realtimeRechargeMoney;
    
    /**
     * 国际检测充值条数
     */
    private Long internationalRechargeNum;

    /**
     * 国际检测充值金额
     */
    private BigDecimal internationalRechargeMoney;
    
    private Long directCommonRechargeNum;

    private BigDecimal directCommonRechargeMoney;

    private Long lineDirectRechargeNum;

    private BigDecimal lineDirectRechargeMoney;

    /**
     * 上次结算时间
     */
    private Long lastTime;
}
