package com.cc.oem.modules.agent.model.param;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 充值参数
 * author zhangx
 * date  2018/8/31 10:31
 */
@Data
public class CustRechargeParam {
    /**
     * 客户id
     */
    private Long custId;

    private Long goodsId;
    /**
     * 对应数据库中的money,单价或套餐价
     */
    private BigDecimal price;
    private Integer number;

    /**
     * 2piModelProperty(value = "充值类型，0：对公转账，1：支付宝扫码付，2：注册赠送，3：赠送, 4：对公支付宝转账，5：对私支付宝， 6：对私微信，7：对私转账")
     */
    private Integer payType;
    /**
     * 总额
     */
    private BigDecimal amount;
    private String remark;

    private Integer category;
}
