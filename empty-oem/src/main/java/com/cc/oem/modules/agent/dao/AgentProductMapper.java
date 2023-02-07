package com.cc.oem.modules.agent.dao;


import com.cc.oem.modules.agent.entity.AgentProduct;
import com.cc.oem.modules.agent.entity.AgentProductWeb;
import com.cc.oem.modules.agent.model.param.ProductLineListParam;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AgentProductMapper extends BaseOemMapper<AgentProduct, Long> {

    List<Map> selectListByParam(ProductLineListParam param);

    /**
     * agentId ,productName
     */
    List<Map> selectOneByMap(Map param);

    Map findById(Long id);

    List<Map> selectOrderList(Long productLineId);

    int updateOrderBatch(List<Map> map);

    Long insertByDefault(AgentProduct agentProduct);

    List<Map> listProductsOfAgent(@Param("agentId") Long agentId, @Param("category") Integer category);
}