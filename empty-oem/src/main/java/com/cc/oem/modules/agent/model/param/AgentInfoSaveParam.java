package com.cc.oem.modules.agent.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 新建代理商参数
 */
@Data
public class AgentInfoSaveParam {

    @ApiModelProperty(value = "联系人姓名")
    @Length(max = 20, message = "联系人姓名最大长度20")
    private String linkmanName;

    @ApiModelProperty(value = "联系人手机号码")
    @Length(max = 20, message = "联系人手机号码最大长度20")
    private String linkmanPhone;

    @ApiModelProperty(value = "联系人邮箱")
    @Length(max = 64, message = "联系人邮箱最大长度64")
    private String linkmanEmail;

    @ApiModelProperty(value = "营业执照地址")
    @Length(max = 200, message = "营业执照地址最大长度200")
    private String businessLicensePath;

    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称最大长度100")
    private String companyName;

    @ApiModelProperty(value = "公司简称")
    @Length(max = 20, message = "公司简称最大长度20")
    private String companyShortName;

    @ApiModelProperty(value = "营业执照所在地")
    @Length(max = 200, message = "营业执照所在地最大长度200")
    private String businessLicenseAddress;

    @ApiModelProperty(value = "营业执照号")
    @Length(max = 64, message = "营业执照号最大长度64")
    @NotNull(message = "营业执照号不能为空")
    private String businessLicenseNumber;

    @ApiModelProperty(value = "企业法人姓名")
    @Length(max = 20, message = "企业法人姓名最大长度20")
    private String legalPerson;

    @ApiModelProperty(value = "营业执照有效期开始时间")
    private String businessLicenseExpireStartTime;

    @ApiModelProperty(value = "营业执照有效期结束时间")
    private String businessLicenseExpireEndTime;

    @ApiModelProperty(value = "代理商空号检测等级名称")
    @NotNull(message = "代理商空号检测等级名称不能为空")
    @Length(max = 20, message = "代理商空号检测等级名称最大长度20")
    private String agentLevel;

    @ApiModelProperty(value = "单价（元/条）")
    @NotNull(message = "单价（元/条）不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "最小支付金额")
    @NotNull(message = "最小支付金额不能为空")
    @Min(value = 1, message = "最小支付金额必须大于0")
    private Long minPaymentAmount;

    @ApiModelProperty(value = "最小充值条数")
    @NotNull(message = "最小充值条数不能为空")
    @Min(value = 0, message = "最小充值条数不能为负数")
    private Long minRechargeNumber;

    @ApiModelProperty(value = "预警条数")
    @NotNull(message = "预警条数不能为空")
    @Min(value = 0, message = "预警条数必须大于或等于0")
    private Long warningsNumber;

    @ApiModelProperty(value = "代理商实时检测等级名称")
    private String realLevel;

    @ApiModelProperty(value = "实时检测单价（元/条）")
    private BigDecimal realPrice;

    @ApiModelProperty(value = "实时检测最小支付金额")
    private Long realMinPaymentAmount;

    @ApiModelProperty(value = "实时检测最小充值条数")
    private Long realMinRechargeNumber;

    @ApiModelProperty(value = "实时检测预警条数")
    private Long realWarningsNumber;
    
    @ApiModelProperty(value = "代理商国际检测等级名称")
    private String internationalLevel;

    @ApiModelProperty(value = "国际检测单价（元/条）")
    private BigDecimal internationalPrice;

    @ApiModelProperty(value = "国际检测最小支付金额")
    private Long internationalMinPaymentAmount;

    @ApiModelProperty(value = "国际检测最小充值条数")
    private Long internationalMinRechargeNumber;

    @ApiModelProperty(value = "国际检测预警条数")
    private Long internationalWarningsNumber;
    
    @ApiModelProperty(value = "代理商国际定向通用检测等级名称")
    private String directCommonLevel;

    @ApiModelProperty(value = "国际定向通用检测单价（元/条）")
    private BigDecimal directCommonPrice;

    @ApiModelProperty(value = "国际定向通用检测最小支付金额")
    private Long directCommonMinPaymentAmount;

    @ApiModelProperty(value = "国际定向通用检测最小充值条数")
    private Long directCommonMinRechargeNumber;

    @ApiModelProperty(value = "国际定向通用检测预警条数")
    private Long directCommonWarningsNumber;
    
    @ApiModelProperty(value = "代理商国际line定向检测等级名称")
    private String lineDirectLevel;

    @ApiModelProperty(value = "国际line定向检测单价（元/条）")
    private BigDecimal lineDirectPrice;

    @ApiModelProperty(value = "国际line定向检测最小支付金额")
    private Long lineDirectMinPaymentAmount;

    @ApiModelProperty(value = "国际line定向检测最小充值条数")
    private Long lineDirectMinRechargeNumber;

    @ApiModelProperty(value = "国际line定向检测预警条数")
    private Long lineDirectWarningsNumber;

    @ApiModelProperty(value = "是否注册赠送，0：否，1：是")
    @Range(min = 0, max = 1, message = "是否注册赠送输入不合法")
    private Integer registerGift;

    @ApiModelProperty(value = "状态，0：禁用，1：启用")
    @Range(min = 0, max = 1, message = "状态输入不合法")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}
