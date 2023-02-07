package com.cc.oem.modules.agent.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cc.oem.modules.agent.entity.records.CustomerVerifyQueryVo;
import com.cc.oem.modules.agent.model.data.records.CustomerExtQueryVo;
import com.cc.oem.modules.agent.model.param.AuthInfoParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 业务原因新建
 */
@Repository
@Mapper
public interface AuthInfoMapper extends BaseMapper {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CustomerExtQueryVo getCustomerExtById(Serializable id);

    List<CustomerVerifyQueryVo> getCustomerVerifyPageList(AuthInfoParam param);

    int auditCustomerExtState(@Param("id") Long id, @Param("state") Integer state);
    
    int auditCustomerBackState(@Param("id") Long id);
    
    int deleteOne(@Param("id") Long id);
}