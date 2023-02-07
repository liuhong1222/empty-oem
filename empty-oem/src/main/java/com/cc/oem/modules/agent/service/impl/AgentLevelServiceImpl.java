package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.AgentLevelMapper;
import com.cc.oem.modules.agent.dao.AgentMapper;
import com.cc.oem.modules.agent.entity.AgentLevel;
import com.cc.oem.modules.agent.model.param.AgentLevelSaveParam;
import com.cc.oem.modules.agent.model.param.AgentLevelUpdateParam;
import com.cc.oem.modules.agent.service.AgentLevelService;
import com.cc.oem.modules.config.utils.Snowflake;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzj
 * @since 2018/8/11
 */
@Service
public class AgentLevelServiceImpl implements AgentLevelService {

    @Autowired
    AgentLevelMapper agentLevelMapper;
    @Autowired
    AgentMapper agentMapper;

    @Autowired
    private Snowflake snowflake;
    /**
     * 查询代理商级别列表
     */
    @Override
    public List<AgentLevel> list(String levelType) {
        List<AgentLevel> agentBasicLevelList = agentLevelMapper.selectListByNotDeleted(levelType);
        if(CollectionUtils.isEmpty(agentBasicLevelList)){
            return new ArrayList<>();
        }
        return agentBasicLevelList;
    }

    /**
     * 新增代理商级别
     */
    @Override
    public void saveBasicLevel(AgentLevelSaveParam param) {
        //验证参数
        verifyParams(param.getPrice(), param.getWarningsNumber(), param.getMinRechargeNumber(), param.getMinPaymentAmount());
        //验证级别是否已经存在
        AgentLevel agentBasicLevelVerifyLevel = agentLevelMapper.queryOneByLevel(param.getLevel());
        Assert.isNull(agentBasicLevelVerifyLevel, "此级别数已存在，不能重复添加");
        AgentLevel agentLevel = new AgentLevel();
        BeanUtils.copyProperties(param, agentLevel);
        agentLevel.setId(snowflake.nextId());
        agentLevelMapper.insertSelective(agentLevel);
    }

    private void verifyParams(BigDecimal price, Long emptyWarnNumber,
                              Integer minRechargeNumber, Integer minPayAmount) {
        //验证单价
        if (price.compareTo(BigDecimal.ZERO) < 1) {
            throw new RRException("单价必须大于0");
        }
        //验证预警条数
        if (emptyWarnNumber.compareTo(0L) < 0) {
            throw new RRException("预警条数必须大于0");
        }
        //验证最小充值条数
        if (minRechargeNumber.compareTo(0) < 1) {
            throw new RRException("最小充值条数必须大于0");
        }
        //验证最大充值金额
        if (minPayAmount.compareTo(0) < 1) {
            throw new RRException("最小充值金额必须大于0");
        }
    }

    /**
     * 修改代理商级别
     */
    @Override
    public void updateBasicLevel(AgentLevelUpdateParam param) {
        //验证参数
        verifyParams(param.getPrice(), param.getWarningsNumber(), param.getMinRechargeNumber(), param.getMinPaymentAmount());
        //验证id是否存在
        AgentLevel agentBasicLevelVerifyId = agentLevelMapper.selectByPrimaryKey(param.getId());
        Assert.notNull(agentBasicLevelVerifyId,"记录不存在");
        //验证级别是否已经存在
        AgentLevel agentBasicLevelVerifyLevel = agentLevelMapper.queryOneByLevel(param.getLevel());
        if (agentBasicLevelVerifyLevel != null && !agentBasicLevelVerifyLevel.getId().equals(param.getId())) {
            throw new RRException("此级别数已存在");
        }
        AgentLevel agentLevel = new AgentLevel();
        BeanUtils.copyProperties(param, agentLevel);
        agentLevelMapper.updateByPrimaryKey(agentLevel);
    }

    /**
     * 删除代理商级别
     */
    @Override
    public R deleteBasicLevel(Long id) {
        //验证id是否存在
        AgentLevel agentBasicLevelVerifyId = agentLevelMapper.selectByPrimaryKey(id);
        List<Long> list = agentMapper.findUsingthisLevel(agentBasicLevelVerifyId.getLevel());
        if(list!=null && list.size()>0){
            return R.error("该等级正在被使用中，无法删除");
        }
        Assert.notNull(agentBasicLevelVerifyId,"记录不存在");
        agentLevelMapper.deleteOneById(id);
        return R.ok();
    }

    /**
     * 查看代理商级别
     */
    @Override
    public AgentLevel detail(Long id) {
        AgentLevel agentBasicLevel = agentLevelMapper.selectByPrimaryKey(id);
        Assert.notNull(agentBasicLevel,"记录不存在");
        return agentBasicLevel;
    }


}
