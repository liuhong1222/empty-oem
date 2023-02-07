package com.cc.oem.modules.agent.service;

import com.cc.oem.modules.agent.model.param.FinanceRechargeParam;
import com.github.pagehelper.PageInfo;

/**
 * @author chenzj
 * @since 2018/8/13
 */
public interface FinanceService {

    PageInfo agentRechargeList(FinanceRechargeParam param);

    PageInfo custRechargeList(FinanceRechargeParam param);

    PageInfo custRefundList(FinanceRechargeParam param);

    PageInfo custConsumeList(FinanceRechargeParam param);



}
