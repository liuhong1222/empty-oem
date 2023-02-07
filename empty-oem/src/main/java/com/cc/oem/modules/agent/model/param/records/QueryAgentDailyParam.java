package com.cc.oem.modules.agent.model.param.records;

import com.cc.oem.modules.agent.model.param.TimesParam;
import lombok.Data;

@Data
public class QueryAgentDailyParam extends TimesParam {

    private Long agentId;
}
