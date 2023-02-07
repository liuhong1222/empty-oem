package com.cc.oem.modules.agent.service;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.records.ChangeCustomerAgentParam;
import com.cc.oem.modules.agent.model.param.records.CustomerChangeAgentQueryParam;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;

/**
 * @author chenzj
 * @since 2018/8/11
 */
public interface CustomerChangeAgentService {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    R getCustomerChangeAgentById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param customerChangeAgentQueryParam
     * @return
     * @throws Exception
     */
    PageInfo getCustomerChangeAgentPageList(CustomerChangeAgentQueryParam customerChangeAgentQueryParam) throws Exception;

    /**
     * 更换客户的代理商
     *
     * @param param 入参
     * @return 响应结果
     */
    R changeCustomerAgent(ChangeCustomerAgentParam param);
}
