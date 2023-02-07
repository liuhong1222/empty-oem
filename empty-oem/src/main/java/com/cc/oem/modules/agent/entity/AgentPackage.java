package com.cc.oem.modules.agent.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商套餐
 */
@Data
public class AgentPackage {
    /**
     * 主键
     */
    private Long id;

    /**
     * 代理商id
     */
    private Long agentId;

    /**
     * 产品id   1空号检查
     */
    private Long productId;

    /**
     * 套餐id
     */
    private Long packageId;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 条数
     */
    private Integer number;

    /**
     * 实际价格
     */
    private BigDecimal money;

    /**
     * 流水号前缀
     */
    private String orderCode;

    /**
     * 打折=discout/100
     */
    private BigDecimal discout;

    /**
     * 原价
     */
    private BigDecimal originalCost;

    /**
     * 排序
     */
    private Integer orderNo;

    /**
     * 逻辑删除标志（1：已删 0：未删）
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    private Date updateTime;


}
