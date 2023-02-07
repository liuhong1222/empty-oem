package com.cc.oem.modules.agent.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商
 */
@Data
public class Agent {

    /**
     * 主键
     */
    private Long id;

    /**
     * 联系人姓名
     */
    private String linkmanName;

    /**
     * 联系人手机号码
     */
    private String linkmanPhone;

    /**
     * 联系人邮箱
     */
    private String linkmanEmail;

    /**
     * 营业执照地址
     */
    private String businessLicensePath;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司简称
     */
    private String companyShortName;

    /**
     * 营业执照所在地
     */
    private String businessLicenseAddress;

    /**
     * 营业执照号
     */
    private String businessLicenseNumber;

    /**
     * 企业法人姓名
     */
    private String legalPerson;

    /**
     * 营业执照有效期开始时间
     */
    private String businessLicenseExpireStartTime;

    /**
     * 营业执照有效期结束时间
     */
    private String businessLicenseExpireEndTime;

    /**
     * 代理商等级
     */
    private String agentLevel;

    /**
     * 单价（元/条）
     */
    private BigDecimal price;

    /**
     * 最小支付金额
     */
    private Long minPaymentAmount;

    /**
     * 最小充值条数
     */
    private Long minRechargeNumber;

    /**
     * 预警条数
     */
    private Long warningsNumber;

    /**
     * 是否注册赠送，0：否，1：是
     */
    private Long registerGift;

    /**
     * 状态，0：禁用，1：启用
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 版本
     */
    private Long version;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 代理商实时检测等级
     */
    private String realLevel;

    /**
     * 实时检测单价（元/条）
     */
    private BigDecimal realPrice;

    /**
     * 实时检测最小支付金额
     */
    private Long realMinPaymentAmount;

    /**
     * 实时检测最小充值条数
     */
    private Long realMinRechargeNumber;

    /**
     * 实时检测预警条数
     */
    private Long realWarningsNumber;

    private Integer authenticationLimitLevel;


    private String mobileCubePath;
    
    /**
     * 代理商国际检测等级
     */
    private String internationalLevel;

    /**
     * 国际检测单价（元/条）
     */
    private BigDecimal internationalPrice;

    /**
     * 国际检测最小支付金额
     */
    private Long internationalMinPaymentAmount;

    /**
     * 国际检测最小充值条数
     */
    private Long internationalMinRechargeNumber;

    /**
     * 国际检测预警条数
     */
    private Long internationalWarningsNumber;
    
    /**
     * 代理商定向通用检测等级
     */
    private String directCommonLevel;

    /**
     * 定向通用检测单价（元/条）
     */
    private BigDecimal directCommonPrice;

    /**
     * 定向通用检测最小支付金额
     */
    private Long directCommonMinPaymentAmount;

    /**
     *定向通用检测最小充值条数
     */
    private Long directCommonMinRechargeNumber;

    /**
     *定向通用检测预警条数
     */
    private Long directCommonWarningsNumber;
    
    /**
     * 代理商line定向检测等级
     */
    private String lineDirectLevel;

    /**
     * line定向检测单价（元/条）
     */
    private BigDecimal lineDirectPrice;

    /**
     * line定向检测最小支付金额
     */
    private Long lineDirectMinPaymentAmount;

    /**
     *line定向检测最小充值条数
     */
    private Long lineDirectMinRechargeNumber;

    /**
     * line定向检测预警条数
     */
    private Long lineDirectWarningsNumber;

}
