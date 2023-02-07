package com.cc.oem.modules.agent.dao.records;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.AgentRecharge;
import com.cc.oem.modules.agent.entity.records.AgentRefund;
import com.cc.oem.modules.agent.model.data.AgentRechargeTotalData;
import com.cc.oem.modules.agent.model.data.FinanceAgentRechargeData;
import com.cc.oem.modules.agent.model.param.FinanceRechargeParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Mapper
public interface AgentRefundMapper extends BaseOemMapper<AgentRecharge, Long> {


    int save(AgentRefund agentRefund);
}
