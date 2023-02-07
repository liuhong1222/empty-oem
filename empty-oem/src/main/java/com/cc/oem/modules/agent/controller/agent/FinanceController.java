package com.cc.oem.modules.agent.controller.agent;

import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.DateConverter;
import com.cc.oem.common.utils.DateUtils;
import com.cc.oem.common.utils.ExcelExportUtil;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.constants.AgentConstant;
import com.cc.oem.modules.agent.enums.AgentRechargePayTypeEnum;
import com.cc.oem.modules.agent.model.data.*;
import com.cc.oem.modules.agent.model.data.finance.QueryCustConsumeData;
import com.cc.oem.modules.agent.model.data.finance.QueryCustRefundData;
import com.cc.oem.modules.agent.model.param.FinanceRechargeParam;
import com.cc.oem.modules.agent.service.ExcelExportErrorService;
import com.cc.oem.modules.agent.service.FinanceService;
import com.cc.oem.modules.sys.controller.AbstractController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author wade
 */
@RestController
@RequestMapping("/open/agent/finance")
@Validated
@Api("财务管理操作")
public class FinanceController extends AbstractController {

    @Autowired
    FinanceService financeService;

    @Autowired
    ExcelExportErrorService excelExportErrorService;

    @SysLog("查询代理商充值记录列表")
    @GetMapping("/agent/recharge/list")
    @ApiOperation("查询代理商充值记录列表")
    @RequiresPermissions("finance:agent:recharge:list")
    public R agentRechargeList(@Valid FinanceRechargeParam param) {
        //拼接开始时间、结束时间
        param.appendTimeString();
        param.setAgentId(super.getAgentId());
        return R.ok(financeService.agentRechargeList(param));
    }

    @GetMapping("/agent/recharge/list/export")
    @ApiOperation("导出代理商充值记录列表")
    @RequiresPermissions("finance:agent:recharge:list:export")
    public void agentRechargeListExport(@Valid FinanceRechargeParam param, HttpServletResponse response) throws IOException {
        //拼接开始时间、结束时间
        try {
            param.appendTimeString();
            param.setCurrentPage(1);
            param.setPageSize(AgentConstant.MAX_PAGE_SIZE);
            PageInfo pageInfo = financeService.agentRechargeList(param);
            String excelFileName = "代理商充值记录";
            List<FinanceAgentRechargeData> list = pageInfo.getList();
            list.stream().forEach((k)->k.setCategoryName(k.getCategory()==0?"空号检测":(k.getCategory()==1?"实时检测":"国际检测")));
            ExcelExportUtil.exportList(excelFileName, pageInfo.getList(), FinanceAgentRechargeData.class, response);
        } catch (Exception e) {
            excelExportErrorService.error(response, e);
        }
    }

    @SysLog("查询客户充值记录列表")
    @GetMapping("/user/recharge/list")
    @ApiOperation("查询客户充值记录列表")
    @RequiresPermissions("finance:user:recharge:list")
    public R userRechargeList(@Valid FinanceRechargeParam param) {
        //拼接开始时间、结束时间
        param.appendTimeString();
        param.setAgentId(getAgentId());
        return R.ok(financeService.custRechargeList(param));
    }

    @GetMapping("/user/recharge/list/export")
    @ApiOperation("导出客户充值记录列表")
    @RequiresPermissions("finance:user:recharge:list:export")
    public void userRechargeListExport(@Valid FinanceRechargeParam param, HttpServletResponse response) throws IOException {
        //拼接开始时间、结束时间
        try {
            param.appendTimeString();
            param.setAgentId(getAgentId());
            param.setCurrentPage(1);
            param.setPageSize(AgentConstant.MAX_PAGE_SIZE);
            PageInfo pageInfo = financeService.custRechargeList(param);
            String excelFileName = "客户充值记录";
            List<FinanceUserRechargeData> list = pageInfo.getList();
            list.stream().forEach((k)->{
                k.setCategoryName(k.getCategory()==0?"空号检测":(k.getCategory()==1?"实时检测":"国际检测"));
                k.setPayTypeName(AgentRechargePayTypeEnum.getEnumByCode(k.getPayType()).getDescri());
            });
            ExcelExportUtil.exportList(excelFileName, list, FinanceUserRechargeData.class, response);
        } catch (Exception e) {
            excelExportErrorService.error(response, e);
        }
    }

    @SysLog("查询客户退款记录列表")
    @GetMapping("/user/refund/list")
    @ApiOperation("查询客户退款记录列表")
    @RequiresPermissions("finance:user:refund:list")
    public R userRefundList(@Valid FinanceRechargeParam param) {
        //拼接开始时间、结束时间
        param.appendTimeString();
        param.setAgentId(getAgentId());
        return R.ok(financeService.custRefundList(param));
    }

    @GetMapping("/user/refund/list/export")
    @ApiOperation("导出客户退款记录列表")
    @RequiresPermissions("finance:user:refund:list:export")
    public void userRefundListExport(@Valid FinanceRechargeParam param, HttpServletResponse response) throws IOException {
        //拼接开始时间、结束时间
        try {
            param.appendTimeString();
            param.setAgentId(getAgentId());
            param.setCurrentPage(1);
            param.setPageSize(AgentConstant.MAX_PAGE_SIZE);
            PageInfo pageInfo = financeService.custRefundList(param);
            List<QueryCustRefundData> list = pageInfo.getList();
            String excelFileName = "客户退款记录";
            list.stream().forEach((k)->{
                k.setCategoryName(k.getCategory()==0?"空号检测":(k.getCategory()==1?"实时检测":"国际检测"));
                k.setRefundTypeName(AgentRechargePayTypeEnum.getEnumByCode(k.getRefundType()).getDescri());
            });
            ExcelExportUtil.exportList(excelFileName, pageInfo.getList(), QueryCustRefundData.class, response);
        } catch (Exception e) {
            excelExportErrorService.error(response, e);
        }
    }

    @SysLog("查询客户消耗记录列表")
    @GetMapping("/user/consume/list")
    @ApiOperation("查询客户消耗记录列表")
    @RequiresPermissions("finance:user:consume:list")
    public R userConsumeList(@Valid FinanceRechargeParam param) {
        //拼接开始时间、结束时间
        param.appendTimeString();
        param.setAgentId(getAgentId());
        return R.ok(financeService.custConsumeList(param));
    }

    @GetMapping("/user/consume/list/export")
    @ApiOperation("导出客户消耗记录列表")
    @RequiresPermissions("finance:user:consume:list:export")
    public void userConsumeListExport(@Valid FinanceRechargeParam param, HttpServletResponse response) throws IOException {
        //拼接开始时间、结束时间
        try {
            param.appendTimeString();
            if (StringUtils.isBlank(param.getStartTime()) && StringUtils.isBlank(param.getEndTime())) {
                throw new RRException("请选择开始时间和结束时间");
            }
            DateConverter dateConverter = new DateConverter();
            if (DateUtils.differentDays(dateConverter.convert(param.getStartTime()), dateConverter.convert(param.getEndTime())) > 31) {
                throw new RRException("一次最多导1个月的数据");
            }
            param.setAgentId(getAgentId());
            param.setCurrentPage(1);
            param.setPageSize(AgentConstant.MAX_PAGE_SIZE);
            PageInfo pageInfo = financeService.custConsumeList(param);
            String excelFileName = "客户消耗记录";
            ExcelExportUtil.exportList(excelFileName, pageInfo.getList(), QueryCustConsumeData.class, response);
        } catch (Exception e) {
            excelExportErrorService.error(response, e);
        }
    }


}
