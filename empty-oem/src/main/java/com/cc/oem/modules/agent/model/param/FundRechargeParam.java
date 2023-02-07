package com.cc.oem.modules.agent.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 *
 * description 代理商通过支付宝充值
 */
@Data
public class FundRechargeParam {

    @ApiModelProperty("单价")
    @NotNull(message = "单价不能为空")
    private BigDecimal price;

    @ApiModelProperty("万条数")
    @NotNull(message = "条数不能为空")
    private BigDecimal number;

    @ApiModelProperty("万元金额")
    @NotNull(message = "金额不能为空")
    private BigDecimal money;

    private String remark;

    private Integer category;
}

