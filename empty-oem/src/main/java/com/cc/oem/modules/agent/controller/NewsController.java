package com.cc.oem.modules.agent.controller;


import com.cc.oem.common.annotation.RepeatCommitToken;
import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.NewsAuditParam;
import com.cc.oem.modules.agent.model.param.NewsInfoParam;
import com.cc.oem.modules.agent.model.param.NewsSaveParam;
import com.cc.oem.modules.agent.service.NewsService;
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
 *
 * @author wade
 */
@RestController
@RequestMapping("/open/agent/news")
public class NewsController extends AbstractController {

    @Autowired
    NewsService newsService;


    @SysLog("查询代理商新闻列表")
    @GetMapping("/all/list")
    @ApiOperation("查询代理商新闻列表")
    @RequiresPermissions("news:all:list")
    public R newsAllList(NewsInfoParam param) {
        if(super.checkIsAgent()){
            param.setAgentId(super.getAgentId());
        }
        return R.ok(newsService.queryNewsList(param));
    }


    @SysLog("审核代理商新闻")
    @PostMapping("/all/audit")
    @ApiOperation("审核代理商新闻")
    @RepeatCommitToken
    @RequiresPermissions("news:all:audit")
    public R newsAllAudit(@Valid NewsAuditParam param) {
        newsService.newsAllAudit(param, getUserId());
        return R.ok();
    }


    @SysLog("查看代理商新闻详情")
    @GetMapping("/all/detail")
    @ApiOperation("查看代理商新闻详情")
    @RequiresPermissions("news:all:detail")
    public R newsAllDetail(@Valid String newsId) {
        return R.ok(newsService.newsAllDetail(newsId));
    }

    @SysLog(" 删除代理商新闻")
    @PostMapping("/all/delete")
    @ApiOperation("删除代理商新闻")
    @RepeatCommitToken
    @RequiresPermissions("news:all:delete")
    public R newsAllDelete(@Validated Long newsId) {
        newsService.newsAllDelete(newsId, getUserId());
        return R.ok();
    }

    @SysLog("发布我的新闻")
    @PostMapping("/my/save")
    @ApiOperation("发布我的新闻")
    @RepeatCommitToken
    @RequiresPermissions(value = {"news:my:save"})
    public R newsMySave(@Valid NewsSaveParam param) {
        newsService.publicMyNews(param,getAgentId());
        return R.ok();
    }

    @SysLog("修改我的新闻")
    @PostMapping("/my/update")
    @ApiOperation("修改我的新闻")
    @RequiresPermissions("news:my:update")
    public R newsMyUpdate(@Valid NewsSaveParam param) {
        newsService.newsMyUpdate(param,getAgentId());
        return R.ok();
    }


    @SysLog("查看我的新闻详情")
    @GetMapping("/my/detail")
    @ApiOperation("查看我的新闻详情")
    @RequiresPermissions("news:my:detail")
    public R newsMyDetail(@Valid String newsId) {
        return R.ok(newsService.newsMyDetail(newsId, getAgentId()));
    }

    @SysLog("删除我的新闻")
    @PostMapping("/my/delete")
    @ApiOperation("删除我的新闻")
    @RepeatCommitToken
    @RequiresPermissions("news:my:delete")
    public R newsMyDelete(@Validated Long newsId) {
        newsService.newsMyDelete(newsId,getAgentId());
        return R.ok();
    }


}
