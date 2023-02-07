package com.cc.oem.modules.agent.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author chenzj
 * @since 2018/8/11
 */
@Data
public class AgentLevelSaveParam {

    @ApiModelProperty(value = "代理商等级")
    @NotBlank(message = "代理商等级不能为空")
    @Length(max = 20, message = "代理商等级最大长度20")
    private String level;

    @ApiModelProperty(value = "单价（元/条）")
    @NotNull(message = "单价（元/条）不能为空")
    private BigDecimal price;

    @ApiModelProperty("预警条数")
    @NotNull(message = "预警条数不能为空")
    private Long warningsNumber;


    @ApiModelProperty("最小充值条数")
    @NotNull(message = "最小充值条数不能为空")
    private Integer minRechargeNumber;

    @ApiModelProperty("最小支付充值金额")
    @NotNull(message = "最小支付充值金额不能为空")
    private Integer minPaymentAmount;

    /**
     * 0 代表空号等级，1代表实时等级 2-代表国际等级
     */
    @ApiModelProperty("等级类型")
    @NotNull(message = "等级类型不能为空")
    private Integer levelType;

    private String remark;
}
