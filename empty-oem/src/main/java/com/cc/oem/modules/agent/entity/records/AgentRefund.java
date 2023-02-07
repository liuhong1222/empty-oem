package com.cc.oem.modules.agent.entity.records;

import com.baomidou.mybatisplus.annotations.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商充值退款记录
 */
@Data
public class AgentRefund {

    /**
     * 主键
     */
    private Long id;

    private Long agentId;

    @ApiModelProperty(value = "代理商名称")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "单价（元/条）")
    private BigDecimal price;

    @ApiModelProperty(value = "退款条数")
    private Integer refundNumber;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "充值类型，0：对公转账，1：支付宝扫码付，2：注册赠送，3：赠送, 4：对公支付宝转账，5：对私支付宝， 6：对私微信，7：对私转账")
    private Integer refundType;

    @ApiModelProperty(value = "产品类别，0：空号检测产品，1：实时检测产品")
    private Integer category;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "版本")
    @Version
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    //0待处理，1充值成功，2充值失败
    @ApiModelProperty(value = "充值状态")
    private Integer status;
}
