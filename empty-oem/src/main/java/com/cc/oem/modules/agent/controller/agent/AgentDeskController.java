package com.cc.oem.modules.agent.controller.agent;

import com.cc.oem.common.annotation.RepeatCommitToken;
import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.service.AgentDeskService;
import com.cc.oem.modules.sys.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


/**
 * Description:
 * User: zxa
 * Date: 2018-08-24
 * Time: 13:58
 */
@RestController
@RequestMapping("/open/agent/desk")
public class AgentDeskController extends AbstractController {

    @Autowired
    AgentDeskService agentDeskService;

    @SysLog("查看代理商工作台信息")
    @ApiOperation("查看代理商工作台信息")
    @RequiresPermissions("open:agent:desk:getAgentDeskInfo")
    @PostMapping(value = "/getAgentDeskInfo")
    public R getAgentDeskInfo(Integer category) {
        return agentDeskService.getAgentDeskInfo(getAgentId(), getUserId(),category);
    }

    @SysLog("查看管理员工作台信息")
    @ApiOperation("查看管理员工作台信息")
    @RequiresPermissions("open:agent:desk:getAdminDeskInfo")
    @PostMapping(value = "/getAdminDeskInfo")
    public R getAdminDeskInfo(Integer category) {
        return agentDeskService.getAdminDeskInfo(category);
    }


    @RepeatCommitToken
    @ApiOperation("修改代理商预警值")
    @RequiresPermissions("open:agent:desk:updateWarnNumber")
    @PostMapping(value = "/updateWarnNumber")
    public R updateWarnNumber(String warnNumber,Integer category) {
        if (StringUtils.isBlank(warnNumber)) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "预警条数不能为空");
        }
        
        if(null == category) {
        	return R.error(HttpStatus.SC_BAD_REQUEST, "category不能为空");
        }
        
        long warnNumber2 = new BigDecimal(warnNumber).multiply(BigDecimal.valueOf(10000)).longValue();
        return agentDeskService.updateWarnNumber(getAgentId(), warnNumber2,category);
    }

    @RequestMapping(value = "/updateMobile")
    public R updateMobile(String phone) {
        return agentDeskService.updateMobile(getUserId(),phone);
    }

    @SysLog("查询手机号")
    @ApiOperation("查询手机号")
    @RequiresPermissions("open:agent:desk:findMobile")
    @RequestMapping(value = "/findMobile")
    public R findMobile() {
        return agentDeskService.findMobile(getUserId());
    }

    @RepeatCommitToken
    @SysLog("更新email")
    @ApiOperation("更新email")
    @RequiresPermissions("open:agent:desk:updateMail")
    @PostMapping(value = "/updateMail")
    public R updateMail(String mail) {
        return agentDeskService.updateMail(getUserId(), mail);
    }

    @RepeatCommitToken
    @SysLog("赠送条数设置")
    @ApiOperation("赠送条数设置")
    @RequiresPermissions("open:agent:desk:updateAutoPresentCfg")
    @PostMapping(value = "/updateAutoPresentCfg")
    public R updateAutoPresentCfg(Integer autoPresentCfg) {
        return agentDeskService.updateAutoPresentCfg(getAgentId(), autoPresentCfg);
    }

    @SysLog("设置认证等级")
    @ApiOperation("设置认证等级")
    @RequiresPermissions("open:agent:desk:setAuthenLevel")
    @RequestMapping(value = "/setAuthenLevel")
    public R setAuthenLevel(Integer authenLevel,Long agentId) {
        return agentDeskService.setAuthenLevel(agentId,authenLevel);
    }


}
