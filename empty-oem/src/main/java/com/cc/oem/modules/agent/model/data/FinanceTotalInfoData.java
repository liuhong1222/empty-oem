package com.cc.oem.modules.agent.model.data;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wade
 */
@Data
public class FinanceTotalInfoData {

    /**
     * 条数
     */
    private Long number;

    /**
     * 金额
     */
    private BigDecimal money;

}
