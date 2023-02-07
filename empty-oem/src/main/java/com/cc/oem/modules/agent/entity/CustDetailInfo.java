package com.cc.oem.modules.agent.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustDetailInfo extends CustInfo {

    @ApiModelProperty(value = "客户编号")
    private Long customerId;

    @ApiModelProperty(value = "身份证照片正面地址")
    private String idCardFrontPath;

    @ApiModelProperty(value = "身份证照片反面地址")
    private String idCardBackPath;

    @ApiModelProperty(value = "身份证名称")
    private String idCardName;

    @ApiModelProperty(value = "身份证号")
    private String idCardNumber;

    @ApiModelProperty(value = "详细地址")
    private String idCardAddress;

    @ApiModelProperty(value = "身份证有效期开始时间")
    private String idCardExpireStartTime;

    @ApiModelProperty(value = "身份证有效期结束时间")
    private String idCardExpireEndTime;

    @ApiModelProperty(value = "联系邮箱")
    private String email;

    @ApiModelProperty(value = "营业执照地址")
    private String businessLicensePath;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "营业执照号")
    private String businessLicenseNumber;

    @ApiModelProperty(value = "企业法人")
    private String legalPerson;

    @ApiModelProperty(value = "营业执照有效期开始时间")
    private String businessLicenseExpireStartTime;

    @ApiModelProperty(value = "营业执照有效期结束时间")
    private String businessLicenseExpireEndTime;

    @ApiModelProperty(value = "经营范围")
    private String businessScope;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "版本")
    private Integer version;
}