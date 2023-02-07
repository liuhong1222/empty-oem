package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.entity.AgentAccount;
import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.AgentRecharge;
import com.cc.oem.modules.agent.entity.records.AgentRefund;
import com.cc.oem.modules.agent.model.data.records.StaticsAgentAccountData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AgentAccountMapper extends BaseOemMapper<AgentAccount, Long> {

    AgentAccount queryOneByAgentId(@Param("agentId")Long agentId);

    /**
     * 更新账户剩余条数
     */
    int addEmptyBalanceByAgentId(@Param("agentRecharge") AgentRecharge agentRecharge);

    int updateByAgentIdSelective(AgentAccount agentAccount);

    AgentAccount getAgentAccountOneByUserId(Integer creUserId);

    StaticsAgentAccountData stasticsData();
    
    StaticsAgentAccountData stasticsDataNew();

    StaticsAgentAccountData stasticsDataOfAgent(@Param("agentId")Long agentId);
    
    StaticsAgentAccountData stasticsDataOfAgentNew(@Param("agentId")Long agentId);

    int updateAgentAccountByAgentId(AgentAccount ac);

    int plusEmptyBalanceByAgentId(AgentRefund agentRefund);

    List<AgentAccount> queryList();

    int batchSave(@Param("list") List<Long> saveList);

    List<Long> findAllAgentAccount();

    int updateAgentRechargeInfo(AgentAccount newInfo);

    int updateCustRechargeInfo(AgentAccount newInfo);

    int addRefundOfAgent(AgentAccount account);
}
