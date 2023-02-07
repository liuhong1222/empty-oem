package com.cc.oem.modules.agent.controller.agent;

import com.cc.oem.common.annotation.RepeatCommitToken;
import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.ExcelExportUtil;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.constants.AgentConstant;
import com.cc.oem.modules.agent.model.data.AgentInfoData;
import com.cc.oem.modules.agent.model.param.AgentInfoParam;
import com.cc.oem.modules.agent.model.param.AgentInfoSaveParam;
import com.cc.oem.modules.agent.model.param.AgentInfoUpdateParam;
import com.cc.oem.modules.agent.model.param.AgentRechargeParam;
import com.cc.oem.modules.agent.model.param.records.AgentRefundParam;
import com.cc.oem.modules.agent.service.AgentInfoService;
import com.cc.oem.modules.agent.service.ExcelExportErrorService;
import com.cc.oem.modules.sys.controller.AbstractController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenzj
 * @since 2018/8/8
 */
@RestController
@RequestMapping("/open/agent/agentInfo")
@Validated
@Api("代理商信息操作")
public class AgentInfoController extends AbstractController {

    @Autowired
    AgentInfoService agentInfoService;

    @Autowired
    ExcelExportErrorService excelExportErrorService;


    @SysLog("查询代理商列表")
    @GetMapping("/list")
    @ApiOperation("查询代理商列表")
    @RequiresPermissions("user:agent:info:list")
    public R list(AgentInfoParam param) {
        //拼接开始时间、结束时间
        param.appendTimeString();
        return R.ok(agentInfoService.list(param));
    }

    @SysLog("管理员查询代理商列表")
    @GetMapping("/listAgent")
    @ApiOperation("管理员查询代理商列表")
    public R listAgent(String name,Integer officialWeb) {
        return agentInfoService.listAgent(name,officialWeb);
    }

    @GetMapping("/list/export")
    @ApiOperation("导出代理商列表")
    @RequiresPermissions("user:agent:info:list:export")
    public void listExport(AgentInfoParam param, HttpServletResponse response) throws IOException {
        try {
            param.appendTimeString();
            param.setCurrentPage(1);
            param.setPageSize(AgentConstant.MAX_PAGE_SIZE);
            PageInfo pageInfo = agentInfoService.list(param);
            List<AgentInfoData> list = pageInfo.getList();
            list.stream().forEach((k)->{
                k.setStateName(k.getState()==0?"禁用":"启用");
                k.setOfficialName(k.getOfficialWeb()==null?"":(k.getOfficialWeb()==1?"迅龙":"步正云"));
            });
            String excelFileName = "代理商列表";
            ExcelExportUtil.exportList(excelFileName, list, AgentInfoData.class, response);
        } catch (Exception e) {
            excelExportErrorService.error(response, e);
        }
    }

    @PostMapping("/license/upload")
    @ApiOperation("上传营业执照")
    @RequiresPermissions("user:agent:license:upload")
    public R uploadLicense(@Valid MultipartFile file) throws Exception {
        long fileSize = file.getSize();
        if (fileSize <= 0) {
            return R.error("上传失败:文件为空");
        } else if (fileSize > (50 * 1024 * 1024)) {
            return R.error("上传失败:文件大小不能超过50M");
        }
        return R.ok(agentInfoService.uploadLicense(getUserId(), file));
    }

    @SysLog("新增代理商")
    @RepeatCommitToken
    @PostMapping("/save")
    @ApiOperation("新增代理商")
    @RequiresPermissions("user:agent:save")
    public R saveAgent(@Valid AgentInfoSaveParam param) {
        //验证单价
        if (param.getPrice().compareTo(BigDecimal.ZERO) < 1) {
            throw new RRException("单价必须大于0");
        }
        agentInfoService.saveAgent(param, getUserId());
        return R.ok();
    }

    @SysLog("查询代理商详情")
    @GetMapping("/detail")
    @ApiOperation("查询代理商详情")
    @RequiresPermissions("user:agent:info")
    public R detail(@Valid Long agentId) {
        return R.ok(agentInfoService.detail(agentId));
    }

    @SysLog("修改代理商")
    @PostMapping("/update")
    @ApiOperation("修改代理商")
    @RepeatCommitToken
    @RequiresPermissions("user:agent:update")
    public R updateAgent(AgentInfoUpdateParam param) {
        agentInfoService.updateAgent(param);
        return R.ok();
    }

    @SysLog("禁用代理商")
    @RepeatCommitToken
    @PostMapping("/pause")
    @ApiOperation("禁用代理商")
    @RequiresPermissions("user:agent:pause")
    public R pauseAgent(@Valid Long agentId) {
        agentInfoService.pauseAgent(agentId);
        return R.ok();
    }

    @SysLog("启用代理商")
    @RepeatCommitToken
    @PostMapping("/resume")
    @ApiOperation("启用代理商")
    @RequiresPermissions("user:agent:resume")
    public R resumeAgent(@Valid Long agentId) {
        agentInfoService.resumeAgent(agentId);
        return R.ok();
    }

    @SysLog("代理商充值")
    @RepeatCommitToken
    @PostMapping("/recharge")
    @ApiOperation("代理商充值：系统充值，管理员充值接口")
    @RequiresPermissions("user:agent:recharge")
    public R recharge(AgentRechargeParam param) {
        return agentInfoService.recharge(param);
    }

    @ApiOperation("根据代理商名称查找代理商列表")
    @PostMapping("/findAgentListByName")
    public R findAgentListByName(String name){
        return agentInfoService.findAgentListByName(name);
    }

    @ApiOperation("获取代理商可退条数")
    @PostMapping("/getRefundableNumOfAgent")
    public R getRefundableNumOfAgent(Long agentId){
        return agentInfoService.getRefundableNumOfAgent(agentId);
    }

    @SysLog("上传号码魔方")
    @RepeatCommitToken
    @PostMapping("/uploadMobileCube")
    @ApiOperation("代理商上传号码魔方")
    @RequiresPermissions("user:agent:uploadMobileCube")
    public R uploadMobileCube(MultipartFile file,Long agentId,String fileType){
        return agentInfoService.uploadMobileCube(file,agentId,fileType);
    }

    @SysLog("代理商退款")
    @ApiOperation("代理商退款")
    @RequiresPermissions("open:agent:refundOfAgent")
    @RequestMapping(value = "/refundOfAgent", method = RequestMethod.POST)
    public R refundOfAgent(AgentRefundParam param) {
        return agentInfoService.refundOfAgent(param);
    }
}
