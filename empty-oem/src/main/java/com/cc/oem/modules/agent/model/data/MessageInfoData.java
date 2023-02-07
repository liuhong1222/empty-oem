package com.cc.oem.modules.agent.model.data;

import com.cc.oem.modules.agent.entity.AgentMessage;
import lombok.Data;

import java.util.Date;

/**
 * @author chenzj
 */
@Data
public class MessageInfoData extends AgentMessage {


    /**
     * 状态
     */
    private String stateName;
    /**
     * 状态
     */
    private String typeName;
}
