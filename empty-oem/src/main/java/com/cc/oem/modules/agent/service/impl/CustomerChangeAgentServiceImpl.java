package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.constants.AgentConstant;
import com.cc.oem.modules.agent.dao.*;
import com.cc.oem.modules.agent.entity.*;
import com.cc.oem.modules.agent.entity.records.CustomerChangeAgent;
import com.cc.oem.modules.agent.model.data.records.CustomerChangeAgentQueryVo;
import com.cc.oem.modules.agent.model.param.records.ChangeCustomerAgentParam;
import com.cc.oem.modules.agent.model.param.records.CustomerChangeAgentQueryParam;
import com.cc.oem.modules.agent.service.CustomerChangeAgentService;
import com.cc.oem.modules.agent.service.RefreshCacheService;
import com.cc.oem.modules.config.utils.Snowflake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chenzj
 * @since 2018/8/11
 */
@Service
public class CustomerChangeAgentServiceImpl implements CustomerChangeAgentService {

    @Autowired
    CustomerChangeAgentMapper customerChangeAgentMapper;

    @Autowired
    private CustInfoMapper customerMapper;

    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private Snowflake snowflake;
    @Autowired
    private RefreshCacheService refreshCacheService;

    @Override
    public R getCustomerChangeAgentById(Serializable id) throws Exception {
        return R.ok(customerChangeAgentMapper.getCustomerChangeAgentById(id));
    }

    @Override
    public PageInfo getCustomerChangeAgentPageList(CustomerChangeAgentQueryParam param) throws Exception {
        param.appendTimeString();
        PageHelper.startPage(param.getCurrentPage(),param.getPageSize());
        List<CustomerChangeAgentQueryVo> list = customerChangeAgentMapper.getCustomerChangeAgentPageList(param);
        PageInfo info = new PageInfo(list);
        return info;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R changeCustomerAgent(ChangeCustomerAgentParam param) {
        CustInfo custInfo = customerMapper.selectById(param.getCustomerId());
        String phone = custInfo.getPhone();
        if (custInfo == null) {
            return R.error("客户信息未找到");
        }
        CustInfo targetCust = customerMapper.queryByAgentIdAndName(param.getDestAgentId(), custInfo.getName());
        if(targetCust!=null){
            return R.error("您已在目标代理商下使用该手机号进行过注册，您不能进行转代理商操作");
        }
        Agent destAgent = agentMapper.selectByPrimaryKey(param.getDestAgentId());
        if (destAgent == null || AgentConstant.COMMON_DISABLED_STATUS_VALUE.equals(destAgent.getState())) {
            return R.error("代理商信息未找到，或代理商未启用");
        }
        if (custInfo.getAgentId().equals(destAgent.getId())) {
            return R.error("转入代理商为当前代理商，请选择其它代理商");
        }
        CustomerChangeAgent changeAgent = new CustomerChangeAgent();
        changeAgent.setCreateTime(new Date())
                .setCustomerId(custInfo.getId())
                .setDestAgentId(destAgent.getId())
                .setName(custInfo.getName())
                .setPhone(custInfo.getPhone())
                .setRemark(param.getRemark())
                .setUpdateTime(changeAgent.getCreateTime());

        Agent sourceAgent = agentMapper.selectByPrimaryKey(custInfo.getAgentId());
        if (sourceAgent != null) {
            changeAgent.setSourceAgentId(sourceAgent.getId());
        }
        changeAgent.setId(snowflake.nextId());
        customerChangeAgentMapper.save(changeAgent);

        // 更换代理商
        custInfo = new CustInfo();
        custInfo.setId(param.getCustomerId());
        custInfo.setAgentId(param.getDestAgentId());
        custInfo.setRemark(param.getRemark());
        boolean result = customerMapper.updateByPrimaryKeySelective(custInfo) > 0;
        refreshCacheService.reflushCustExt(param.getCustomerId(),sourceAgent.getId(),destAgent.getId(),phone);
        refreshCacheService.customerInfoRefresh(param.getCustomerId());
        return R.ok(result);
    }
}
