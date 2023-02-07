package com.cc.oem.modules.agent.dao.finance;


import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.AgentAccount;
import com.cc.oem.modules.agent.entity.finance.CustomerRefund;
import com.cc.oem.modules.agent.model.data.finance.QueryCustRefundData;
import com.cc.oem.modules.agent.model.param.FinanceRechargeParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerRefundMapper extends BaseOemMapper<CustomerRefund, Long> {

    /**
     * 客户退款记录
     */
    List<QueryCustRefundData> queryCustRefundList(FinanceRechargeParam param);

    Integer save(CustomerRefund refund);

    CustomerRefund queryTotalRechargeInfo(FinanceRechargeParam param);

    List<AgentAccount> countTotalRefundOfCust(String startTime, String endTime);
}
