package com.cc.oem.modules.agent.controller;


import com.cc.oem.common.annotation.RepeatCommitToken;
import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.MessageAuditParam;
import com.cc.oem.modules.agent.model.param.MessageInfoParam;
import com.cc.oem.modules.agent.model.param.MessageSaveParam;
import com.cc.oem.modules.agent.service.MessageService;
import com.cc.oem.modules.sys.controller.AbstractController;
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
 * copyright (C), 2018-2018, 创蓝253
 *
 * 消息管理
 */
@RestController
@RequestMapping("/open/agent/message")
public class MessageController extends AbstractController {

    @Autowired
    MessageService messageService;

    @SysLog("查询代理商消息列表")
    @GetMapping("/all/list")
    @ApiOperation("查询代理商消息列表")
    @RequiresPermissions("message:all:list")
    public R messageAllList(MessageInfoParam param) {
        return R.ok(messageService.messageAllList(param));
    }

    @RequiresPermissions("open:agent:message:findUserIdMobileByMobile")
    @RequestMapping(value = "/findUserIdMobileByMobile")
    public R findUserIdMobileByMobile(String userPhone) {
        return messageService.findUserIdMobileByMobile(userPhone);
    }

    @SysLog("审核代理商消息")
    @PostMapping("/all/audit")
    @ApiOperation("审核代理商消息")
    @RepeatCommitToken
    @RequiresPermissions("message:all:audit")
    public R messageAllAudit(@Validated MessageAuditParam param) {
        messageService.messageAllAudit(param, getAgentId());
        return R.ok();
    }


    @SysLog("查看代理商消息详情")
    @GetMapping("/all/detail")
    @ApiOperation("查看代理商消息详情")
    @RequiresPermissions("message:all:detail")
    public R messageAllDetail(@Valid String agentMessageId) {
        return R.ok(messageService.messageAllDetail(agentMessageId));
    }

    @SysLog(" 删除代理商消息")
    @PostMapping("/all/delete")
    @ApiOperation("删除代理商消息")
    @RepeatCommitToken
    @RequiresPermissions("message:all:delete")
    public R messageAllDelete(@Validated Long agentMessageId) {
        messageService.messageAllDelete(agentMessageId, getUserId());
        return R.ok();
    }


    @SysLog("查询我的消息列表")
    @GetMapping("/my/list")
    @ApiOperation("查询我的消息列表")
    @RequiresPermissions("message:my:list")
    public R messageMyList(MessageInfoParam param) {
        return R.ok(messageService.messageMyList(param, getAgentId()));
    }


    @SysLog("发布我的消息")
    @PostMapping(value = {"/my/save", "/addMessage"})
    @ApiOperation("发布我的消息")
    @RepeatCommitToken
    @RequiresPermissions({"message:my:save", "open:agent:message:addMessage"})
    public R messageMySave(@Valid MessageSaveParam param) {
        messageService.messageMySave(param, getUserId(), getAgentId());
        return R.ok();
    }


    @SysLog("修改我的消息")
    @PostMapping("/my/update")
    @ApiOperation("修改我的消息")
    @RequiresPermissions("message:my:update")
    public R messageMyUpdate(@Valid MessageSaveParam param) {
        messageService.messageMyUpdate(param, getUserId(), getAgentId());
        return R.ok();
    }


    @SysLog("查看我的消息详情")
    @GetMapping("/my/detail")
    @ApiOperation("查看我的消息详情")
    @RequiresPermissions("message:my:detail")
    public R messageMyDetail(@Valid String agentMessageId) {
        return R.ok(messageService.messageMyDetail(agentMessageId, getAgentId()));
    }


    @SysLog("删除我的消息")
    @PostMapping("/my/delete")
    @ApiOperation("删除我的消息")
    @RepeatCommitToken
    @RequiresPermissions("message:my:delete")
    public R messageMyDelete(@Validated Long agentMessageId) {
        messageService.messageMyDelete(agentMessageId, getUserId(), getAgentId());
        return R.ok();
    }

}
