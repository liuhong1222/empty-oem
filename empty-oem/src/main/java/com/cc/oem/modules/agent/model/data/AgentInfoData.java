package com.cc.oem.modules.agent.model.data;

import com.cc.oem.common.annotation.ExcelField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商查询返回数据
 *
 */
@Data
public class AgentInfoData {
    /**
     * 代理商id
     */
    private Long agentId;
    /**
     * 代理商公司名称
     */
    @ExcelField(value = "官网类型", order = 0)
    private String officialName;

    /**
     * 代理商公司名称
     */
    @ExcelField(value = "代理商名称", order = 1)
    private String companyName;

    /**
     * 创建时间
     */
    @ExcelField(value = "创建时间", order = 4)
    private Date createTime;

    /**
     * 代理商离线检测等级
     */
    private String realLevelName;
    
    /**
     * 代理商国际检测等级
     */
    private String internationalLevelName;
    
    /**
     * 定向通用检测等级
     */
    private String directCommonLevelName;
    
    /**
     * line定向检测等级
     */
    private String lineDirectLevelName;

    /**
     * 充值总计(元)
     */
    @ExcelField(value = "空号充值总计(元)", order = 6)
    private Long emptyRechargeMoney;

    /**
     * 充值总条数
     */
    @ExcelField(value = "空号充值总条数", order = 7)
    private Long emptyRechargeNumber;

    /**
     * 剩余条数
     */
    @ExcelField(value = "空号剩余条数", order = 8)
    private Long emptyBalance;

    /**
     * 实时检测充值总条数
     */
    @ExcelField(value = "实时充值总条数", order = 9)
    private Long realTimeRechargeNumber;

    /**
     * 实时检测剩余条数
     */
    @ExcelField(value = "实时剩余条数", order = 10)
    private Long realTimeBalance;

    @ExcelField(value = "实时充值总计(元)", order =11)
    private Long realTimeRechargeMoney;
    
    /**
     * 国际检测充值总条数
     */
    @ExcelField(value = "国际充值总条数", order = 12)
    private Long internationalRechargeNumber;

    /**
     * 国际检测剩余条数
     */
    @ExcelField(value = "国际剩余条数", order = 13)
    private Long internationalBalance;

    @ExcelField(value = "国际充值总计(元)", order =14)
    private Long internationalRechargeMoney;
    
    /**
     * 国际定向通用检测充值总条数
     */
    @ExcelField(value = "国际定向通用充值总条数", order = 15)
    private Long directCommonRechargeNumber;

    /**
     * 国际定向通用检测剩余条数
     */
    @ExcelField(value = "国际定向通用剩余条数", order = 16)
    private Long directCommonBalance;

    @ExcelField(value = "国际定向通用充值总计(元)", order =17)
    private Long directCommonRechargeMoney;
    
    /**
     * 国际line定向检测充值总条数
     */
    @ExcelField(value = "国际line定向充值总条数", order = 18)
    private Long lineDirectRechargeNumber;

    /**
     * 国际line定向检测剩余条数
     */
    @ExcelField(value = "国际line定向剩余条数", order = 19)
    private Long lineDirectBalance;

    @ExcelField(value = "国际line定向充值总计(元)", order =20)
    private Long lineDirectRechargeMoney;

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
    @ExcelField(value = "联系电话", order = 21)
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
     * 公司简称
     */
    @ExcelField(value = "公司简称", order = 3)
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
    @ExcelField(value = "空号检测等级", order = 5)
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
    @ExcelField(value = "空号预警条数", order = 22)
    private Long warningsNumber;

    /**
     * 是否注册赠送，0：否，1：是
     */
    private Long registerGift;

    /**
     * 状态，0：禁用，1：启用
     */

    private Integer state;
    @ExcelField(value = "状态", order = 2)
    private String stateName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 版本
     */
    private Long version;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 代理商实时检测等级
     */
    @ExcelField(value = "实时检测等级", order =23)
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
    @ExcelField(value = "实时预警条数", order = 24)
    private Long realWarningsNumber;
    
    /**
     * 代理商国际检测等级
     */
    @ExcelField(value = "国际检测等级", order =25)
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
    @ExcelField(value = "国际预警条数", order = 26)
    private Long internationalWarningsNumber;
    
    /**
     * 代理商国际定向通用检测等级
     */
    @ExcelField(value = "国际定向通用检测等级", order =27)
    private String directCommonLevel;

    /**
     * 国际定向通用检测单价（元/条）
     */
    private BigDecimal directCommonPrice;

    /**
     * 国际定向通用检测最小支付金额
     */
    private Long directCommonMinPaymentAmount;

    /**
     * 国际定向通用检测最小充值条数
     */
    private Long directCommonMinRechargeNumber;

    /**
     * 国际定向通用检测预警条数
     */
    @ExcelField(value = "国际定向通用预警条数", order = 28)
    private Long directCommonWarningsNumber;
    
    /**
     * 代理商国际line定向检测等级
     */
    @ExcelField(value = "国际line定向检测等级", order =29)
    private String lineDirectLevel;

    /**
     * 国际line定向检测单价（元/条）
     */
    private BigDecimal lineDirectPrice;

    /**
     * 国际line定向检测最小支付金额
     */
    private Long lineDirectMinPaymentAmount;

    /**
     * 国际line定向检测最小充值条数
     */
    private Long lineDirectMinRechargeNumber;

    /**
     * 国际line定向检测预警条数
     */
    @ExcelField(value = "国际line定向预警条数", order = 30)
    private Long lineDirectWarningsNumber;

    private Integer authenticationLimitLevel;


    private String mobileCubePath;

    private Integer officialWeb;
}
