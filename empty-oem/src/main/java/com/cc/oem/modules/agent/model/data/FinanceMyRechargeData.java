package com.cc.oem.modules.agent.model.data;

import com.cc.oem.common.annotation.ExcelField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商充值记录
 *
 * @author chenzj
 * @since 2018/8/13
 */
public class FinanceMyRechargeData {
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 充值时间
     */
    @ExcelField(value = "充值时间", order = 1)
    private Date payTime;

    /**
     * 订单编号
     */
    @ExcelField(value = "订单编号", order = 2)
    private String orderNo;

    /**
     * 单价
     */
    @ExcelField(value = "单价(元/条)", order = 3)
    private BigDecimal price;

    /**
     * 条数
     */
    @ExcelField(value = "条数", order = 4)
    private Long number;

    /**
     * 金额
     */
    @ExcelField(value = "金额(元)", order = 5)
    private BigDecimal money;

    /**
     * 入账方式
     */
    private Integer payType;

    /**
     * 入账方式名称
     */
    @ExcelField(value = "方式", order = 6)
    private String payTypeName;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }
}
