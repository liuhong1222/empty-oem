package com.cc.oem.modules.agent.model.param;

import lombok.Data;

/**
 *
 */
@Data
public class AgentSetListParam extends TimesParam {

    private String agentName;
    private Integer state;
    private String linkmanPhone;

}
