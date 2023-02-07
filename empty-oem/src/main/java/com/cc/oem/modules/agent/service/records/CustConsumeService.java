package com.cc.oem.modules.agent.service.records;


import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.data.records.ConsumeData;
import com.cc.oem.modules.agent.model.param.CustRechargeParam;
import com.cc.oem.modules.agent.model.param.CustRefundParam;

import java.util.List;

/**
 *  客户消耗操作类
 * @author wade
 *
 *
 */
public interface CustConsumeService {

    List<ConsumeData> queryTodayConsumeData();

    List<ConsumeData> queryTodayConsumeDataOfAgent(Long agentId);

}
