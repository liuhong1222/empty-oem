package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.records.CustomerChangeAgent;
import com.cc.oem.modules.agent.model.data.records.CustomerChangeAgentQueryVo;
import com.cc.oem.modules.agent.model.param.records.CustomerChangeAgentQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
@Mapper
public interface CustomerChangeAgentMapper extends BaseOemMapper<CustomerChangeAgent, Long> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CustomerChangeAgentQueryVo getCustomerChangeAgentById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param customerChangeAgentQueryParam
     * @return
     */
    List<CustomerChangeAgentQueryVo> getCustomerChangeAgentPageList(CustomerChangeAgentQueryParam customerChangeAgentQueryParam);


    boolean save(CustomerChangeAgent customerChangeAgent);

    boolean updateById(CustomerChangeAgent customerChangeAgent);

}