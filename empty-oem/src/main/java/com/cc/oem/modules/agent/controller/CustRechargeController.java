package com.cc.oem.modules.agent.controller;


import com.cc.oem.common.annotation.RepeatCommitToken;
import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.CustRechargeParam;
import com.cc.oem.modules.agent.model.param.CustRefundParam;
import com.cc.oem.modules.agent.service.CustOrderService;
import com.cc.oem.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/open/agent/cust")
public class CustRechargeController extends AbstractController {

    @Autowired
    CustOrderService orderService;

    @RequiresPermissions("open:agent:cust:getPackageInfo")
    @RequestMapping(value = "/getPackageInfo")
    public R getPackageInfo(Integer category,Long custId) {
        return orderService.getPackageInfo(super.getAgentId(),category,custId);
    }

    @RepeatCommitToken
    @SysLog("代理商给客户充值")
    @RequiresPermissions("open:agent:cust:recharge")
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public R recharge(@RequestBody CustRechargeParam param) {
        return orderService.recharge(param,super.getAgentId());
    }

    @RepeatCommitToken
    @SysLog("代理商给客户退款")
    @RequiresPermissions("open:agent:cust:refunds")
    @RequestMapping(value = "/refunds", method = RequestMethod.POST)
    public R refunds(CustRefundParam param) {
        return orderService.refunds(param,super.getAgentId());
    }
}
