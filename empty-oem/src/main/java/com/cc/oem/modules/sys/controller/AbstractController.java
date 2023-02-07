package com.cc.oem.modules.sys.controller;

import com.cc.oem.modules.agent.entity.Agent;
import com.cc.oem.modules.agent.service.AgentInfoService;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.service.SysUserService;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller公共组件
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SysUserService sysUserService;
    @Autowired
    AgentInfoService agentInfoService;

    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getId();
    }

    protected Long getAgentId() {
        Long agentId = sysUserService.selectAgentIdBySysUserId(sysUserService.getSysUserId());
        return agentId;
    }

    protected boolean checkIsAgent() {
        Long agentId = sysUserService.selectAgentIdBySysUserId(sysUserService.getSysUserId());
        if (agentId == null) {
            return false;
        }
        return true;
    }

    protected boolean getIsAdmin() {
        Long sysUserId = sysUserService.getSysUserId();
        if (!sysUserService.judgeIfAdmin(sysUserId)) {//不是管理员
            return false;
        }
        return true;
    }

    protected Agent getAgentInfoByAgentId(){
        Long agentId = sysUserService.selectAgentIdBySysUserId(sysUserService.getSysUserId());
        Agent detail = agentInfoService.detail(agentId);
        return detail;
    }

}
