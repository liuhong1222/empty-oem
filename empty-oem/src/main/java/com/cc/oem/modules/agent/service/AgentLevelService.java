package com.cc.oem.modules.agent.service;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.AgentLevel;
import com.cc.oem.modules.agent.model.param.AgentLevelSaveParam;
import com.cc.oem.modules.agent.model.param.AgentLevelUpdateParam;

import java.util.List;

/**
 * @author chenzj
 * @since 2018/8/11
 */
public interface AgentLevelService {

    List<AgentLevel> list(String levelType);

    void saveBasicLevel(AgentLevelSaveParam param);

    void updateBasicLevel(AgentLevelUpdateParam param);

    R deleteBasicLevel(Long id);

    AgentLevel detail(Long id);
}
