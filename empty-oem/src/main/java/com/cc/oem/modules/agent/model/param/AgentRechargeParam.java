package com.cc.oem.modules.agent.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 代理商充值
 *
 */
@Data
public class AgentRechargeParam {

    @ApiModelProperty(value = "代理商编号")
    @NotNull(message = "代理商编号不能为空")
    private Long agentId;

    @ApiModelProperty(value = "代理商等级")
    private String agentLevel;

    @ApiModelProperty(value = "代理商名称")
    private String name;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "单价（元/条）")
    private BigDecimal price;

    @ApiModelProperty(value = "充值条数")
    private Integer rechargeNumber;

    @ApiModelProperty(value = "充值金额")
    @NotNull(message = "充值金额不能为空")
    @Min(value = 1, message = "充值金额不能小于1元")
    @Max(value = Integer.MAX_VALUE - 1, message = "充值金额超过最大值")
    private Integer paymentAmount;

    @ApiModelProperty(value = "充值类型，0：对公转账，1：支付宝扫码付，2：注册赠送，3：赠送, 4：对公支付宝转账，5：对私支付宝， 6：对私微信，7：对私转账")
    @NotNull(message = "充值类型不能为空")
    @Range(min = 0, max = 7, message = "充值类型无效")
    private Integer payType;

    @ApiModelProperty(value = "产品类别，0：空号检测产品，1：实时检测产品")
    @NotNull(message = "产品类别不能为空")
    @Range(min = 0, max = 5, message = "产品类别输入有误")
    private Integer category;

    @ApiModelProperty(value = "备注")
    @Length(max = 200, message = "备注最大长度200")
    private String remark;

}
