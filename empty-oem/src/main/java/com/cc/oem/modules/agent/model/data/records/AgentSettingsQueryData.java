package com.cc.oem.modules.agent.model.data.records;

import com.cc.oem.modules.agent.entity.agentSettings.AgentSettings;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AgentSettingsQueryData extends AgentSettings {


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
}
