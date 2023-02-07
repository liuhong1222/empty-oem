package com.cc.oem.modules.agent.model.data.records;

import lombok.Data;

@Data
public class ConsumeBalanceForStastics {

    private Long agentId;

    private Long closingBalance;
    private Long dayInt;
}
