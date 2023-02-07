package com.cc.oem.modules.agent.controller.records;


import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.userManage.CustomerVerifyQueryParam;
import com.cc.oem.modules.agent.service.records.CustExtService;
import com.cc.oem.modules.sys.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * author wade
 *
 * 客户认证信息处理
 *
 * 对应表 customer_ext
 */
@RestController
@RequestMapping("/open/agent/cust")
public class CustExtController extends AbstractController {
    @Autowired
    CustExtService custExtService;

    /**
     * 客户认证信息分页列表
     */
    @PostMapping("/getVerifyPageList")
    @RequiresPermissions("user:verify:list:page")
    @ApiOperation(value = "获取CustomerExt分页列表", notes = "客户认证信息分页列表")
    public R getCustomerExtPageList(@Valid @RequestBody CustomerVerifyQueryParam customerExtQueryParam) throws Exception {
        customerExtQueryParam.setAgentId(super.getAgentId());
        return custExtService.getCustomerExtPageList(customerExtQueryParam);
    }

    /**
     * 客户认证信息待审核列表
     */
    @PostMapping("/queryAuditingCustExtList")
    @RequiresPermissions("user:verify:list:page")
    @ApiOperation(value = "获取CustomerExt分页列表", notes = "客户认证信息分页列表")
    public R queryAuditingCustExtList(@Valid @RequestBody CustomerVerifyQueryParam customerExtQueryParam) throws Exception {
        customerExtQueryParam.setAgentId(super.getAgentId());
        return custExtService.queryAuditingCustExtList(customerExtQueryParam);
    }


}
