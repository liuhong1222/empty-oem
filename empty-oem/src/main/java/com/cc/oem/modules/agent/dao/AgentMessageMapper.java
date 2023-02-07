package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.entity.AgentMessage;
import com.cc.oem.modules.agent.model.data.MessageInfoData;
import com.cc.oem.modules.agent.model.param.MessageInfoParam;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AgentMessageMapper extends BaseOemMapper<AgentMessage, Long> {

    /**
     * 查询代理商消息列表
     */
    List<MessageInfoData> queryAgentMessageList(MessageInfoParam param);

    /**
     * 审核代理商消息
     */
    int auditAgentMessage(@Param("id") Long id,
                          @Param("state") Integer state, @Param("remark") String remark);

    /**
     * 修改代理商消息
     */
    int modifyAgentMessage(@Param("title") String title, @Param("message") String message,
                           @Param("id") Long id, @Param("auditState") Integer auditState,
                           @Param("type") String type);

    /**
     * 删除代理商消息
     */
    int deleteAgentMessage(@Param("id") Long id, @Param("agentId") Long agentId);

    /**
     * 数秒内最近的代理商消息
     */
    long countByCreatorWithinSeconds(@Param("agentId") Long agentId, @Param("seconds") Long seconds);

}
