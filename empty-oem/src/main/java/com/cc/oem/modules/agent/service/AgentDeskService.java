package com.cc.oem.modules.agent.service;

import com.cc.oem.common.utils.R;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName CreUserService
 * author   zhangx
 * date     2018/8/8 11:07
 * description
 */
public interface AgentDeskService {

    R getAgentDeskInfo(Long agentId, Long sysUserId,Integer category);

    R updateWarnNumber(Long agentId, Long warnNumber,Integer category);

    R updateMobile(Long sysUserId, String mobile);

    R findMobile(Long sysUserId);

    R updateMail(Long sysUserId, String mail);

    R getAdminDeskInfo(Integer category);

    R updateAutoPresentCfg(Long agentId, Integer autoPresentCfg);

    R setAuthenLevel(Long agentId ,Integer authenLevel);

}
