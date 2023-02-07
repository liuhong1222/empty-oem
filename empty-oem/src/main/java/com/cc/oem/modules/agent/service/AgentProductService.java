package com.cc.oem.modules.agent.service;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.AgentProduct;
import com.cc.oem.modules.agent.model.param.ProductLineListParam;

/**
 * author zhangx
 * date  2019/3/25 13:43
 */
public interface AgentProductService {


    R list(ProductLineListParam param);

    /**
     * state 0上架，1下架 ,2删除  ,3通过，4驳回
     */
    R updateStatus(Long id, Integer status, String remark);

    R saveOrUpdate(AgentProduct agentProduct);

    R findById(Long id);

    R reorder(Long agentId);

    R updateOrder(Long id,Integer orderNum);

    R listProductsOfAgent(Long agentId,Integer category);
}
