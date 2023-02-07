package com.cc.oem.modules.agent.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商级别关联
 */
@Data
public class AgentLevel {
    /**
     * 主键
     */
    private Long id;

    /**
     * 代理商等级
     */
    private String level;

    private Long warningsNumber;

    private Integer minRechargeNumber;

    private Integer minPaymentAmount;

    /**
     * 单价
     */
    private BigDecimal price;

    private Date createTime;

    private Date updateTime;

    private Integer levelType;

    private String remark;


}
