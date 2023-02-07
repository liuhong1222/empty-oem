package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.entity.AgentAccount;
import com.cc.oem.modules.agent.entity.AgentRecharge;
import com.cc.oem.modules.agent.model.data.AgentRechargeTotalData;
import com.cc.oem.modules.agent.model.data.FinanceAgentRechargeData;
import com.cc.oem.modules.agent.model.data.records.AgentRechargeData;
import com.cc.oem.modules.agent.model.param.FinanceRechargeParam;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Mapper
public interface AgentRechargeMapper extends BaseOemMapper<AgentRecharge, Long> {

    /**
     * 汇总代理商总充值信息
     * @param agentIdList
     */
    List<AgentRechargeTotalData> queryAgentRechargeTotalDataList(@Param("list") List<Long> agentIdList);

    /**
     * 查询代理商充值记录
     */
    List<FinanceAgentRechargeData> queryFinanceAgentRechargeDataList(FinanceRechargeParam param);

    /**
     * 代理商充值总金额统计
     */
    BigDecimal sumMoney();

    /**
     * 代理商充值总条数统计
     */
    Long sumNumber();

    List<AgentRecharge> findOrderByOrderNoAndStatus(@Param("orderNo") String orderNo, @Param("status") Integer status);

    int updateByIdAndVersionSelective(AgentRecharge agentRecharge);

    AgentRecharge findOrderByOrderNo(@Param("orderNo") String orderNo);

    List<AgentAccount> calcuHistoryAgentInfo(@Param("startTime") String startTime,@Param("endTime") String endTime);

    AgentRechargeData getTotalRechargeInfo(FinanceRechargeParam param);

    AgentAccount findRechargeNumByAgentId(@Param("agentId") Long agentId);
}
