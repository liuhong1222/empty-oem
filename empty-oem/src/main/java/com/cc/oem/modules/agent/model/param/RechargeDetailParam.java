package com.cc.oem.modules.agent.model.param;

import java.math.BigDecimal;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName RechargeDetailParam
 * author   zhangx
 * date     2018/5/29 18:58
 * description
 */
public class RechargeDetailParam extends PageParam {
    /**
     * 这个是t_product中的id
     */
    private Integer productId;
    /**
     * 1支付宝，2银联 3创蓝充值',
     */
    private String payType;
    /*1单价，2充值条数，3充值金额**/
    private Integer projectType;
    private BigDecimal minCount;
    private BigDecimal maxCount;
    /**
     * '0000-00-00 00:00:00'
     */
    private String startDateStr;
    private String endDateStr;

    /**
     * '1 充值 2 退款'
     */
    private String type;
    private Integer userId;

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public BigDecimal getMinCount() {
        return minCount;
    }

    public void setMinCount(BigDecimal minCount) {
        this.minCount = minCount;
    }

    public BigDecimal getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(BigDecimal maxCount) {
        this.maxCount = maxCount;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }
}
