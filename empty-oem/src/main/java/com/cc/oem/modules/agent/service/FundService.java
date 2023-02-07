package com.cc.oem.modules.agent.service;

import com.alipay.api.AlipayApiException;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.FundRechargeParam;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName FundService
 * author   zhangx
 * date     2018/8/17 14:09
 * description
 */
public interface FundService {

    R recharge(FundRechargeParam param);

    String rechargeCallBack(Map<String, String> param) throws AlipayApiException, UnsupportedEncodingException;

    boolean dealRecharge(String orderNo, String tradNo, String tradeStatus, Map<String, String> param);

    R findOrderStatus(String orderNo);

}
