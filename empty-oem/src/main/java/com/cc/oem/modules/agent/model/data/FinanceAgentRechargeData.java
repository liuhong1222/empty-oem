package com.cc.oem.modules.agent.model.data;

import com.cc.oem.common.annotation.ExcelField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商充值记录
 *
 * @author chenzj
 * @since 2018/8/13
 */
@Data
public class FinanceAgentRechargeData {
    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 代理商id
     */
    @ExcelField(value = "代理商序号", order = 1)
    private Long agentId;

    /**
     * 代理商编号
     */
    private String agentNo;

    /**
     * 代理商名称
     */
    @ExcelField(value = "代理商名称", order = 2)
    private String companyName;

    /**
     * 代理商手机号
     */
    @ExcelField(value = "手机号", order = 3)
    private String agentMobile;

    /**
     * 充值时间
     */
    @ExcelField(value = "充值时间", order = 5)
    private Date payTime;

    /**
     * 订单编号
     */
    @ExcelField(value = "订单编号", order = 6)
    private String orderNo;

    /**
     * 代理级别名称
     */
    @ExcelField(value = "代理等级", order = 7)
    private String levelName;

    /**
     * 单价
     */
    @ExcelField(value = "单价(元/条)", order = 8)
    private BigDecimal price;

    /**
     * 条数
     */
    @ExcelField(value = "条数", order = 9)
    private Long number;

    /**
     * 金额
     */
    @ExcelField(value = "金额(元)", order = 10)
    private Long money;

    /**
     * 入账方式
     */
    private Integer payType;

    /**
     * 入账方式名称
     */
    @ExcelField(value = "方式", order = 11)
    private String payTypeName;

    /**
     * 充值产品类别
     */
    private Integer category;
    @ExcelField(value = "充值产品", order = 4)
    private String categoryName;
}
