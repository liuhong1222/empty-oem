package com.cc.oem.modules.agent.dao;


import com.cc.oem.modules.agent.entity.ProductGroup;
import com.cc.oem.modules.agent.entity.ProductLineWeb;
import com.cc.oem.modules.agent.model.param.ProductLineListParam;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ProductLineMapper extends BaseOemMapper<ProductGroup, Long> {

    List<Map> selectListByParam(ProductLineListParam param);

    /**
     * agentId ,productLineName
     */
    List<Map> selectOneByMap(Map param);

    Map findById(Long id);

    List<Map> findNameListByAgentId(@Param("agentId") Long agentId, @Param("productLineName") String productLineName);

    List<Map> selectOrderList(@Param("agentId")Long agentId);

    int updateOrderBatch(List<Map> map);

    int updateWebOrderBatch(List<Map> map);
}