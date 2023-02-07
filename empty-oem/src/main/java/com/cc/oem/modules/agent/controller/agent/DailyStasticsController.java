package com.cc.oem.modules.agent.controller.agent;

import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.records.DailyQueryParam;
import com.cc.oem.modules.agent.service.records.AgentDailyStasticService;
import com.cc.oem.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/agent/dailyStastics")
@Validated
@Api("代理商日统计")
public class DailyStasticsController extends AbstractController {

    @Autowired
    private AgentDailyStasticService agentDailyStasticService;


    @SysLog("查找代理商统计列表")
    @ApiOperation("查找代理商统计列表")
    @RequiresPermissions("open:agent:daily:list")
    @RequestMapping(value = "/listAgentDaily")
    public R listAgentDaily(DailyQueryParam param) {
        if(!getIsAdmin()){
            param.setAgentId(super.getAgentId());
        }
        return agentDailyStasticService.queryAgentDailyList(param);
    }

    @SysLog("查找客户统计列表")
    @ApiOperation("查找客户统计列表")
    @RequiresPermissions("open:cust:daily:list")
    @RequestMapping(value = "/listCustDaily")
    public R listCustDaily(DailyQueryParam param) {
        if(!getIsAdmin()){
            param.setAgentId(super.getAgentId());
        }
        return agentDailyStasticService.queryCustDailyList(param);
    }


}
