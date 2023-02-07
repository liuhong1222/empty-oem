package com.cc.oem.modules.agent.controller.agent;

import com.cc.oem.common.annotation.RepeatCommitToken;
import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.agentSettings.AgentSettings;
import com.cc.oem.modules.agent.model.param.AgentSetListParam;
import com.cc.oem.modules.agent.service.AgentSetService;
import com.cc.oem.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;


/**
 * Description:
 * User: zxa
 * Date: 2018-08-24
 * Time: 13:58
 */
@RestController
@RequestMapping("/open/agent/set")
@Validated
@Api("客户管理")
public class AgentSetController extends AbstractController {

    @Autowired
    AgentSetService agentSetService;

    @SysLog("查找基本信息")
    @ApiOperation("查找基本信息")
    @RequiresPermissions("open:agent:set:findBasicInfo")
    @RequestMapping(value = "/findBasicInfo")
    public R findBasicInfo(Long agentId) {
        return agentSetService.findBasicInfo(agentId);
    }

    @SysLog("查看代理商设置列表")
    @ApiOperation("查看代理商设置列表")
    @RequiresPermissions("open:agent:set:agentSetList")
    @RequestMapping(value = "/agentSetList")
    public R agentSetList(AgentSetListParam param) {
        return agentSetService.agentSetList(param);
    }


    @RepeatCommitToken
    @SysLog("更新基本信息")
    @RequiresPermissions("open:agent:set:updateBasicInfo")
    @RequestMapping(value = "/updateBasicInfo")
    public R updateBasicInfo(@Valid @RequestBody AgentSettings param) {
        return agentSetService.updateBasicInfo(param);
    }

    /**
     * 添加代理商设置
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加AgentSettings对象", notes = "添加代理商设置")
    public R addAgentSettings(@Valid @RequestBody AgentSettings source) throws Exception {
        source.setAgentId(super.getAgentId());
        if (StringUtils.isNotBlank(source.getDomain())) {
            source.setDomain(source.getDomain().trim().toLowerCase());
            if (source.getDomain().startsWith("http")) {
                return R.error("域名不用填写http、https等");
            }
            if (!source.getDomain().contains(".")) {
                return R.error("域名不正确");
            }
        }
        source.setCreateTime(new Date())
                .setUpdateTime(source.getCreateTime())
                .setState(AgentSettings.State.UNAUDITED.getState())
                .setId(null).setRemark(null);
        return agentSetService.saveAgentSettings(source);
    }

    @RequiresPermissions("open:agent:set:audit")
    @RequestMapping(value = "/audit")
    public R auditAgentSetting(Long agentId,Integer state,String remark,Integer officialWeb) {
        return agentSetService.auditAgentSetting(agentId,state,remark,officialWeb);
    }


}
