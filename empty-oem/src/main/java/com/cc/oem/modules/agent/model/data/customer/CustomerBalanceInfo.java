package com.cc.oem.modules.agent.model.data.customer;

import com.cc.oem.modules.agent.entity.CustomerBalance;
import lombok.Data;

@Data
public class CustomerBalanceInfo extends CustomerBalance {

    /**
     * 客户手机号
     */
    private String custPhone;
    /**
     * 客户名
     */
    private String custName;
    /**
     * 可退空号条数
     */
    private Long refundableEmptyNum;
    /**
     * 可退实时条数
     */
    private Long refundableRealTimeNum;
    /**
     * 可退国际条数
     */
    private Long refundableInternationalNum;

    /**
     * 可退定向通用条数
     */
    private Long refundableDirectCommonNum;
    /**
     * 可退line定向条数
     */
    private Long refundableLineDirectNum;
}
