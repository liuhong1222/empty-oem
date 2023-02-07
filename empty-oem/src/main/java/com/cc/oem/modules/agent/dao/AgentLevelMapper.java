package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.AgentLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AgentLevelMapper extends BaseOemMapper<AgentLevel, Long> {

    List<AgentLevel> selectListByNotDeleted(@Param("levelType") String levelType);

    AgentLevel queryOneByLevel(String level);

    AgentLevel queryOneByAgentId(@Param("agentId") Long agentId,@Param("category") Integer category);


    void deleteOneById(Long id);

}
