package com.cc.oem.modules.agent.model.param;

import lombok.Data;

import java.util.List;

/**
 * 财务管理查询条件
 */
@Data
public class FinanceRechargeParam extends TimesParam {

    /**
     * 代理商id
     */
    private Long agentId;

    /**
     * 代理商名称
     */
    private String companyName;

    /**
     * 交易类型：1支付宝  2银联  3创蓝充值  4管理员充值  5对公转账  6赠送
     */
    private Integer payType;

    /**
     * 客户名称
     */
    private String userName;

    /**
     * 客户手机号
     */
    private String custMobile;


    /**
     * 代理商手机号
     */
    private String agentMobile;
    
    /**
     * 产品类别，0：空号检测产品，1：实时检测产品 2-国际检测产品
     */
    private Integer category;

    private List<Long> custIdList;
}
