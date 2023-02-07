package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.entity.AgentCreUser;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AgentCreUserMapper extends BaseOemMapper<AgentCreUser, Long> {

    List<AgentCreUser> selectByCreUserId(Integer creUserId);

    int deleteByCreUserId(Integer creUserId);
}
