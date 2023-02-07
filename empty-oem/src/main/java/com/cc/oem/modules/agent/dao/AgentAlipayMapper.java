package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.entity.AgentAlipay;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AgentAlipayMapper extends BaseOemMapper<AgentAlipay, Long> {

    AgentAlipay selectByAgentId(@Param("agentId") Long agentId);

}
