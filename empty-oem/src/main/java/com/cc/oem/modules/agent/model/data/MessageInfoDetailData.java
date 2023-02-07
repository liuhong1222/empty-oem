package com.cc.oem.modules.agent.model.data;

import com.cc.oem.modules.agent.entity.AgentMessage;
import lombok.Data;

import java.util.List;

/**
 * @author chenzj
 */
@Data
public class MessageInfoDetailData extends AgentMessage {

    private List<String> customerMobileList;

    private String noticeTypeName;
}
