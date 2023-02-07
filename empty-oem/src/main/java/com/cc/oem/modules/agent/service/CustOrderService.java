package com.cc.oem.modules.agent.service;


import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.agentSettings.Goods;
import com.cc.oem.modules.agent.model.param.CustRechargeParam;
import com.cc.oem.modules.agent.model.param.CustRefundParam;

/**
 * copyright (C), 2018-2018, 创蓝253
 *
 * @author zhangx
 * @fileName ManagerService
 * @date 2018/5/18 19:27
 * @description
 */
public interface CustOrderService {
    //    R selectRechargeList(RechargeDetailParam param);
    R getPackageInfo(Long agentId,Integer category,Long custId);

    R recharge(CustRechargeParam param,Long agentId);

    R refunds(CustRefundParam param,Long agentId);

    R commonRechargeOperation(Goods goods, CustRechargeParam param, Long agentId);

    R commonPresentRechargeOperation(Goods goods, CustRechargeParam param, Long agentId);
}
