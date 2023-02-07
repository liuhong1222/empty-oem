package com.cc.oem.modules.agent.controller;


import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.ExcelExportUtil;
import com.cc.oem.common.utils.R;
import com.cc.oem.common.utils.ShiroUtils;
import com.cc.oem.modules.agent.constants.AgentConstant;
import com.cc.oem.modules.agent.model.data.CustExportData;
import com.cc.oem.modules.agent.model.param.CustInfoParam;
import com.cc.oem.modules.agent.model.param.userManage.CreateCustParam;
import com.cc.oem.modules.agent.service.CustInfoService;
import com.cc.oem.modules.agent.service.ExcelExportErrorService;
import com.cc.oem.modules.sys.controller.AbstractController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * author zhangx
 *
 * 对应表 customer
 */
@RestController
@RequestMapping("/open/agent/cust")
public class CustInfoController extends AbstractController {
    @Autowired
    CustInfoService custInfoService;

    @Autowired
    ExcelExportErrorService excelExportErrorService;

    @SysLog("查询客户列表")
    @ApiOperation("查询客户列表")
    @RequiresPermissions("user:user:info:list")
    @RequestMapping(value = "/custList")
    public R custList(@RequestBody CustInfoParam param) {
        if(!getIsAdmin()){
            param.setAgentId(super.getAgentId());
        }
        return custInfoService.custList(param, false);
    }

    @ApiOperation("导出客户列表")
    @RequiresPermissions("user:user:info:list:export")
    @RequestMapping(value = "/custListExport")
    public void custListExport( CustInfoParam param, HttpServletResponse response) throws IOException {
        if(!getIsAdmin()){
            param.setAgentId(super.getAgentId());
        }
        try {
            param.setCurrentPage(1);
            param.setPageSize(AgentConstant.MAX_PAGE_SIZE);
            R result = custInfoService.custList(param, true);
            PageInfo<CustExportData> pageInfo = (PageInfo<CustExportData>) result.get("data");
            List<CustExportData> mapList = pageInfo.getList();
            mapList.stream().forEach(k->k.setOfficialName(k.getOfficialWeb()==null?"":(k.getOfficialWeb()==1?"迅龙":"步正云")));
            ExcelExportUtil.exportList(ShiroUtils.getUserEntity().getRoleId().intValue(),"", mapList, CustExportData.class, response);
        } catch (Exception e) {
            logger.error("什么鬼");
            excelExportErrorService.error(response, e);
        }
    }

    @SysLog("查看客户详情")
    @ApiOperation("查看个人客户详情")
    @RequiresPermissions("user:indv:info")
    @RequestMapping(value = "/findPersonById")
    public R findPersonById(@RequestParam("customerId") Long customerId) {
        return custInfoService.findPersonCustById(customerId);
    }

    @SysLog("注册赠送")
    @ApiOperation("注册赠送")
    @RequiresPermissions("user:account:presentNum")
    @RequestMapping(value = "/presentNum", method = RequestMethod.POST)
    public R presentNum(Long custId) {
        return custInfoService.presentNum(custId,super.getAgentId());
    }

    @RequiresPermissions("open:agent:cust:getCustInfo")
    @RequestMapping(value = "/getCustInfo", method = RequestMethod.POST)
    public R getCustInfo(Long custId) {
        return custInfoService.getCustInfo(custId);
    }

    @RequiresPermissions("open:agent:cust:setApi")
    @RequestMapping(value = "/setApiState", method = RequestMethod.POST)
    public R setApiState(Long custId,Integer state) {
        return custInfoService.setApiState(custId,state);
    }

    @SysLog("代理商设置客户认证等级")
    @ApiOperation("代理商设置客户认证等级")
    @RequiresPermissions("open:agent:cust:setAuthenLevel")
    @RequestMapping(value = "/setAuthenLevel")
    public R setAuthenLevel(Long custId,Integer authenLevel) {
        return custInfoService.setAuthenLevel(getAgentId(),custId,authenLevel);
    }

    @SysLog("代理商添加客户")
    @ApiOperation("代理商添加客户")
    @RequiresPermissions("open:agent:cust:addCust")
    @RequestMapping(value = "/addCustOfAgent")
    public R addCustOfAgent(@Validated @RequestBody CreateCustParam param) {
        param.setAgentId(getAgentId());
        return custInfoService.addCustOfAgent(param);
    }
}
