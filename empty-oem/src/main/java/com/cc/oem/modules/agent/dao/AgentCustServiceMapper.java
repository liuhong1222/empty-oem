package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.entity.AgentCustService;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AgentCustServiceMapper extends BaseOemMapper<AgentCustService, Long> {
    AgentCustService selectByAgentId(@Param("agentId") Long agentId);

    List<AgentCustService> selectByMeiqiaEntid(@Param("meiqiaEntid") String meiqiaEntid);

    int delWeixinImageByAgentId(@Param("agentId") Long agentId);


}
