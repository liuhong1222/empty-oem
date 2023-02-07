package com.cc.oem.modules.agent.entity.finance;

import com.cc.oem.common.annotation.ExcelField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomerRecharge {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "代理商编号")
    private Long agentId;

    @ApiModelProperty(value = "客户编号")
    private Long customerId;

    @ApiModelProperty(value = "充值套餐名称")
    @ExcelField(value = "套餐", order = 7)
    public String goodsName;

    @ApiModelProperty(value = "客户名称")
    @ExcelField(value = "客户名称", order = 1)
    private String name;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    @ExcelField(value = "手机号码", order = 3)
    private String phone;

    @ApiModelProperty(value = "订单编号")
    @ExcelField(value = "订单编号", order = 5)
    private String orderNo;

    @ApiModelProperty(value = "单价（元/条）")
    @ExcelField(value = "单价（元/条）", order = 8)
    private String price;

    @ApiModelProperty(value = "充值条数")
    @ExcelField(value = "充值条数", order = 9)
    private Integer rechargeNumber;

    @ApiModelProperty(value = "充值金额")
    @ExcelField(value = "金额(元)", order = 10)
    private String paymentAmount;

    @ApiModelProperty(value = "充值类型，0：对公转账，1：支付宝扫码付，2：注册赠送，3：赠送, 4：对公支付宝转账，5：对私支付宝， 6：对私微信，7：对私转账")
    @ExcelField(value = "充值方式", order = 11)
    private Integer payType;

    @ApiModelProperty(value = "产品类别，0：空号检测产品，1：实时检测产品")
    @ExcelField(value = "产品类别", order = 4)
    private Integer category;

    @ApiModelProperty(value = "期初余条")
    @ExcelField(value = "期初余条", order = 13)
    private Long openingBalance;

    @ApiModelProperty(value = "期末余条")
    @ExcelField(value = "期末余条", order = 12)
    private Long closingBalance;

    @ApiModelProperty(value = "备注")
    @ExcelField(value = "备注", order = 14)
    private String remark;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @ExcelField(value = "充值时间", order = 6)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "操作者名称")
    private String creatorName;
}