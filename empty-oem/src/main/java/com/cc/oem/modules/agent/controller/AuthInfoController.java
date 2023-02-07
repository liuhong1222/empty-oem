package com.cc.oem.modules.agent.controller;


import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.BusinessLicenceInfo;
import com.cc.oem.modules.agent.model.param.AuthInfoParam;
import com.cc.oem.modules.agent.service.CustInfoService;
import com.cc.oem.modules.agent.service.records.impl.AuthServiceImpl;
import com.cc.oem.modules.sys.controller.AbstractController;
import com.cc.oem.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author cw
 */
@RestController
@RequestMapping("/open/agent/auth")
@Validated
@Api("用户认证管理")
public class AuthInfoController extends AbstractController {


    @Autowired
    AuthServiceImpl authService;

    @Autowired
    SysUserService sysUserService;

    @SysLog("查看认证列表")
    @ApiOperation("查看认证列表")
    @RequiresPermissions("user:auth:info:list")
    @RequestMapping(value = "/companyAuthList", method = RequestMethod.POST)
    public R companyAuthList(AuthInfoParam param) {
        param.setAgentId(getAgentId());
        param.appendTimeString();
        return authService.companyAuthList(param);
    }

    @SysLog("查看认证详情")
    @ApiOperation("查看认证详情")
    @RequiresPermissions("user:auth:info:detail")
    @RequestMapping(value = "/getCustomerExtById", method = RequestMethod.POST)
    public R getCustomerExtById(Long id) {
        return authService.getCustomerExtById(id);
    }

    @SysLog("审核认证")
    @ApiOperation("审核认证")
    @RequiresPermissions("user:auth:info:audit")
    @RequestMapping(value = "/auditCustExt", method = RequestMethod.POST)
    public R auditCustExt(Long id,Integer state,Long customerId) {
        return authService.auditCustExt(id,state,customerId,getAgentId());
    }





}
