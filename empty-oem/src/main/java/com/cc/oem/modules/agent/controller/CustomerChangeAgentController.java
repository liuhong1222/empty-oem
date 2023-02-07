package com.cc.oem.modules.agent.controller;


import com.cc.oem.common.utils.ExcelExportUtil;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.constants.AgentConstant;
import com.cc.oem.modules.agent.model.data.records.CustomerChangeAgentQueryVo;
import com.cc.oem.modules.agent.model.param.records.ChangeCustomerAgentParam;
import com.cc.oem.modules.agent.model.param.records.CustomerChangeAgentQueryParam;
import com.cc.oem.modules.agent.service.CustomerChangeAgentService;
import com.cc.oem.modules.agent.service.ExcelExportErrorService;
import com.cc.oem.modules.sys.controller.AbstractController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * copyright (C), 2018-2018, 创蓝253
 *
 * @author zhangx
 * @fileName ManagerController
 * @description tt
 */
@RestController
@RequestMapping("/open/agent/user")
public class CustomerChangeAgentController extends AbstractController {

    @Autowired
    private CustomerChangeAgentService customerChangeAgentService;

    @Autowired
    ExcelExportErrorService excelExportErrorService;

    /**
     * 获取客户转代理商记录
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "获取CustomerChangeAgent对象详情", notes = "查看客户转代理商记录")
    public R getCustomerChangeAgent(@PathVariable("id") Long id) throws Exception {
        return customerChangeAgentService.getCustomerChangeAgentById(id);
    }



    /**
     * 客户转代理商记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("customer:change:list")
    @ApiOperation(value = "获取CustomerChangeAgent分页列表", notes = "客户转代理商记录分页列表")
    public R getCustomerChangeAgentPageList(@Valid @RequestBody CustomerChangeAgentQueryParam customerChangeAgentQueryParam) throws Exception {
        return R.ok(customerChangeAgentService.getCustomerChangeAgentPageList(customerChangeAgentQueryParam));
    }

    /**
     * 客户转代理商记录分页列表导出
     */
    @GetMapping("/export")
    @RequiresPermissions("customer:change:list")
    @ApiOperation(value = "获取CustomerChangeAgent分页列表", notes = "客户转代理商记录分页列表")
    public void exportCustomerChangeAgentPageList( CustomerChangeAgentQueryParam param, HttpServletResponse response) throws Exception {
        try {
            param.appendTimeString();
            param.setCurrentPage(1);
            param.setPageSize(AgentConstant.MAX_PAGE_SIZE);
            PageInfo result = customerChangeAgentService.getCustomerChangeAgentPageList(param);

            String excelFileName = "客户转代理商记录";
            ExcelExportUtil.exportList(excelFileName, result.getList(), CustomerChangeAgentQueryVo.class, response);
        } catch (Exception e) {
            excelExportErrorService.error(response, e);
        }

    }

    /**
     * 更换客户的代理商
     *
     * @param param 入参
     * @return 响应结果
     */
    @PostMapping("/changeAgent")
    @RequiresPermissions("user:customer:list:change")
    @ApiOperation(value = "更换客户的代理商", notes = "更换客户的代理商")
    public R changeCustomerAgent(@Valid @RequestBody ChangeCustomerAgentParam param) {
        return customerChangeAgentService.changeCustomerAgent(param);
    }
}
