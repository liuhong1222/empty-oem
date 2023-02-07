package com.cc.oem.modules.agent.model.data;

import com.cc.oem.common.annotation.ExcelField;

import java.util.Date;

/**
 * 客户充值记录
 *
 * @author chenzj
 * @since 2018/8/13
 */
public class FinanceUserConsumeData {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 消耗时间
     */
    @ExcelField(value = "消耗时间", order = 5)
    private Date consumeTime;
    /**
     * 客户id
     */
    @ExcelField(value = "客户编号", order = 1)
    private Long userId;
    /**
     * 客户名称
     */
    @ExcelField(value = "客户名称", order = 2)
    private String userName;
    /**
     * 客户手机号
     */
    @ExcelField(value = "手机号", order = 3)
    private String userMobile;
    /**
     * 代理商名称
     */
    @ExcelField(value = "代理商名称", order = 4)
    private String agentCompanyName;

    /**
     * 消耗条数
     */
    @ExcelField(value = "消耗条数(条)", order = 6)
    private Long number;
    /**
     * 代理商id
     */
    private Long agentId;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Date consumeTime) {
        this.consumeTime = consumeTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getAgentCompanyName() {
        return agentCompanyName;
    }

    public void setAgentCompanyName(String agentCompanyName) {
        this.agentCompanyName = agentCompanyName;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

}
