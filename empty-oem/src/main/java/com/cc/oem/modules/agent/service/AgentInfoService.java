package com.cc.oem.modules.agent.service;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.Agent;
import com.cc.oem.modules.agent.model.param.AgentInfoParam;
import com.cc.oem.modules.agent.model.param.AgentInfoSaveParam;
import com.cc.oem.modules.agent.model.param.AgentInfoUpdateParam;
import com.cc.oem.modules.agent.model.param.AgentRechargeParam;
import com.cc.oem.modules.agent.model.param.records.AgentRefundParam;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author chenzj
 * @since 2018/8/8
 */
public interface AgentInfoService {

    PageInfo list(AgentInfoParam param);

    Map<String, String> uploadLicense(Long sysUserId , MultipartFile file) throws Exception;

    void saveAgent(AgentInfoSaveParam param,Long sysUserId);

    Agent detail(Long agentId);

    void updateAgent(AgentInfoUpdateParam param);

    void pauseAgent(Long agentId);

    void resumeAgent(Long agentId);

    R recharge(AgentRechargeParam param);

    R listAgent(String name,Integer officialWeb);

    R findAgentListByName(String name);

    R getRefundableNumOfAgent(Long agentId);

    R uploadMobileCube(MultipartFile file, Long agentId,String fileType);

    R refundOfAgent(AgentRefundParam param);

    public List<Long> findAgentIdList();
}
