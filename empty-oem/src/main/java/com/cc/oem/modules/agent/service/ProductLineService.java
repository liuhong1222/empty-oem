package com.cc.oem.modules.agent.service;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.ProductGroup;
import com.cc.oem.modules.agent.model.param.ProductLineListParam;

/**
 * author zhangx
 * date  2019/3/25 13:43
 */
public interface ProductLineService {

    R list(ProductLineListParam param);

    /**
     * state 0上架，1下架 ,2删除  ,3通过，4驳回
     */
    R updateStatus(Long id, Integer status, String remark);

    R saveOrUpdate(ProductGroup productGroup);

    R findById(String id);

    R reorder(Long agentId);

    R updateOrder(Long id, Integer orderNum);

    R findNameListByName(String productLineName, Long agentId);

}
