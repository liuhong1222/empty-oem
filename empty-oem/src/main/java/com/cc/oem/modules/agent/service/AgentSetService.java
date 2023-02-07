package com.cc.oem.modules.agent.service;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.*;
import com.cc.oem.modules.agent.entity.agentSettings.AgentSettings;
import com.cc.oem.modules.agent.model.param.AgentSetListParam;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName CreUserService
 * author   zhangx
 * date     2018/8/8 11:07
 * description
 */
public interface AgentSetService {

    R agentSetList(AgentSetListParam param);

    R findBasicInfo(Long agentId);

    R updateBasicInfo(AgentSettings param);

    /**
     * 保存
     *
     * @param agentSettings
     * @return
     * @throws Exception
     */
    R saveAgentSettings(AgentSettings agentSettings);

    R updateAgentSettings(AgentSettings agentSettings);

    R auditAgentSetting(Long agentId, Integer state,String remark,Integer officalWeb);
}
