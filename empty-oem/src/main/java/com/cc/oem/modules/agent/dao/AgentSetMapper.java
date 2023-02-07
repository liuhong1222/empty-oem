package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.agentSettings.AgentSettings;
import com.cc.oem.modules.agent.model.data.records.AgentSettingsQueryData;
import com.cc.oem.modules.agent.model.param.AgentSetListParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AgentSetMapper extends BaseOemMapper<AgentSettings, Long> {

    List<AgentSettingsQueryData> agentSetList(AgentSetListParam param);

    AgentSettings findBasicInfo(@Param("agentId") Long agentId);

    AgentSettings selectByDomain(String domain);

    int saveAgentSetting(AgentSettings settings);

    int updateAgentSettings(AgentSettings settings);

    String selectDomainByAgentId(Long agentId);

    int auditAgentSetting(@Param("agentId") Long agentId, @Param("state") Integer state,@Param("remark") String remark,@Param("officialWeb")Integer officialWeb);

    List<Long> queryAgentSettingByDomain(String domain);

    List<Long> querAgentIdListByOfficialWeb(Integer officialWeb);
}
