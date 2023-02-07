package com.cc.oem.modules.agent.controller.agent;

import com.cc.oem.common.annotation.RepeatCommitToken;
import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.AgentLevelSaveParam;
import com.cc.oem.modules.agent.model.param.AgentLevelUpdateParam;
import com.cc.oem.modules.agent.service.AgentLevelService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author wade
 */
@RestController
@RequestMapping("/open/agent/level")
@Validated
public class AgentLevelController {

    @Autowired
    AgentLevelService agentLevelService;

    @SysLog("查询代理商级别列表")
    @GetMapping("/list")
    @ApiOperation("查询代理商级别列表")
    @RequiresPermissions("user:level:info:list")
    public R list(String levelType) {
        return R.ok(agentLevelService.list(levelType));
    }

    @SysLog("新增代理商级别")
    @RepeatCommitToken
    @PostMapping("/save")
    @ApiOperation("新增代理商级别")
    @RequiresPermissions("user:level:save")
    public R saveBasicLevel(@Valid AgentLevelSaveParam param) {
        agentLevelService.saveBasicLevel(param);
        return R.ok();
    }

    @SysLog("修改代理商级别")
    @RepeatCommitToken
    @PostMapping("/update")
    @ApiOperation("修改代理商级别")
    @RequiresPermissions("user:level:update")
    public R updateBasicLevel(@Valid AgentLevelUpdateParam param) {
        agentLevelService.updateBasicLevel(param);
        return R.ok();
    }

    @SysLog("删除代理商级别")
    @RepeatCommitToken
    @PostMapping("/delete")
    @ApiOperation("删除代理商级别")
    @RequiresPermissions("user:level:delete")
    public R deleteBasicLevel(@Valid Long id) {
        return agentLevelService.deleteBasicLevel(id);
    }

    @SysLog("查看代理商级别")
    @GetMapping("/detail")
    @ApiOperation("查看代理商级别")
    @RequiresPermissions("user:level:info")
    public R detail(@Valid Long id) {
        return R.ok(agentLevelService.detail(id));
    }


}
