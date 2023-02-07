package com.cc.oem.modules.agent.model.data.customer;

import com.cc.oem.modules.agent.entity.CustomerBalance;
import lombok.Data;

/**
 * 客户历史被赠与条数
 */
@Data
public class CustomerGivenRechargeNumInfo{

    /**
     * 客户手机号
     */
    private Long givenRechargeNum;
    /**
     * 0 空号，1 实时
     */
    private Integer category;


}
