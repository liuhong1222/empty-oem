package com.cc.oem.modules.agent.model.param;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 充值参数
 * author zhangx
 * date  2018/8/31 10:31
 */
@Data
public class CustRefundParam {
    /**
     * 用户id
     */
    private Long custId;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 总金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 0，空号，1.实时
     */
    private Integer category;
    /**
     * 0 对公，1 支付宝 2其他
     */
    private Integer refundType;
    /**
     * 0 对公，1 支付宝 2注册赠送 3 赠送
     */
    private Integer payType;

    /**
     * 剩余条数
     */
    private Long openingBalance;
}
