package com.cc.oem.modules.agent.controller.agent;

import com.alipay.api.AlipayApiException;
import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.FundRechargeParam;
import com.cc.oem.modules.agent.service.FundService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 代理商充值接口
 */
@RestController
@RequestMapping("/open/agent/fund")
@Api("工作台->代理商充值")
public class FundController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    FundService fundService;


    @SysLog("充值预创建")
    @ApiOperation("充值预创建")
    @RequiresPermissions("open:agent:fund:recharge")
    @PostMapping(value = "/recharge")
    public R recharge(@Valid @RequestBody FundRechargeParam param) {
        return fundService.recharge(param);
    }


    @ApiOperation("充值回调")
    @PostMapping(value = "/rechargeCallBack")
    public String rechargeCallBack(HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> param = getParamsMap(request);
        logger.info("支付宝回调参数:" + param.toString());
        String resultStr = null;
        try {
            resultStr = fundService.rechargeCallBack(param);
        } catch (AlipayApiException e) {
            logger.error("支付宝回调异常", e);
            return "false";
        } catch (UnsupportedEncodingException e) {
            logger.error("支付宝回调异常", e);
            return "false";
        }
        return resultStr;
    }

    private Map<String, String> getParamsMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        return params;
    }

    @SysLog("查询订单状态")
    @ApiOperation("查询订单状态")
    @RequiresPermissions("open:agent:fund:findOrderStatus")
    @RequestMapping(value = "/findOrderStatus", method = RequestMethod.POST)
    public R findOrderStatus(@NotEmpty @ApiParam("订单号") String orderNo) {
        return fundService.findOrderStatus(orderNo);
    }

}
