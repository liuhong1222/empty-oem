package com.cc.oem.modules.agent.service.impl;

import cn.hutool.core.io.FileUtil;
import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.constants.AgentConstant;
import com.cc.oem.modules.agent.dao.*;
import com.cc.oem.modules.agent.entity.*;
import com.cc.oem.modules.agent.entity.agentSettings.AgentSettings;
import com.cc.oem.modules.agent.model.data.records.AgentSettingsQueryData;
import com.cc.oem.modules.agent.model.param.AgentSetListParam;
import com.cc.oem.modules.agent.service.AgentSetService;
import com.cc.oem.modules.agent.util.SpringBeanUtil;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.*;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName CreUserServiceImpl
 * author   zhangx
 * date     2018/8/8 11:07
 * description
 */
@Service
public class AgentSetServiceImpl implements AgentSetService {

    @Value("${fileUploadPath.agreement}")
    String uploadPath;
    @Autowired
    AgentSetMapper agentSetMapper;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    AgentMapper agentMapper;
    @Autowired
    private Snowflake snowflake;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveAgentSettings(AgentSettings agentSettings){
        if(agentSettings.getId()!=null){
            return R.error("已存在代理商设置，如需调整请选择修改代理商设置");
        }
        Agent agent = agentMapper.selectByPrimaryKey(agentSettings.getAgentId());
        if (agent == null || AgentConstant.COMMON_DISABLED_STATUS_VALUE.equals(agent.getState())) {
            return R.error("操作失败；代理商已禁用，请联系管理员");
        }

        if (StringUtils.isNotBlank(agentSettings.getDomain())) {
            String domain = agentSettings.getDomain();
            AgentSettings settings = agentSetMapper.selectByDomain(domain);
            if (settings != null) {
                return R.error("操作失败；域名已被使用");
            }
        }
        if (StringUtils.isNotBlank(agentSettings.getDomain())) {
            List<Long> setList = agentSetMapper.queryAgentSettingByDomain(agentSettings.getDomain());
            if(!CollectionUtils.isEmpty(setList)){
                return R.error("该域名已被其他代理商占用，请使用其他域名");
            }
        }
        if (StringUtils.isNotBlank(agentSettings.getAgreement())) {
            String path = uploadPath + "agreement";
            File directory = new File(path);
            if (!directory.exists()) {
                boolean flag = directory.mkdirs();
                if (!flag) {
                    throw new RuntimeException("创建" + directory + "目录失败！");
                }
            }
            File file = new File(path + "/" + agentSettings.getAgentId() + ".txt");
            FileUtil.writeUtf8String(agentSettings.getAgreement(), file);
        }
        agentSettings.setAgreement(null);
        agentSettings.setId(snowflake.nextId());
        int result = agentSetMapper.saveAgentSetting(agentSettings);
        return R.ok(agentSettings.getId());

    }

    @Override
    public R agentSetList(AgentSetListParam param) {
        param.appendTimeString();
        PageHelper.startPage(param.getCurrentPage(),param.getPageSize());
        List<AgentSettingsQueryData> list = agentSetMapper.agentSetList(param);
        PageInfo<AgentSettingsQueryData> pageInfo = new PageInfo(list);
        return R.ok(pageInfo);
    }

    @Override
    public R findBasicInfo(Long agentId) {
        Map map = new HashMap();
        AgentSettings basicInfo = agentSetMapper.findBasicInfo(agentId);
        map.put("agentSetInfo",basicInfo);
        if(basicInfo==null){
            map.put("agentSetId",null);
        }else{
            map.put("agentSetId",basicInfo.getId());
        }
        return R.ok(map);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public R updateBasicInfo(AgentSettings param) {
        if (param.getAgentId() == null) {
            throw new RRException("缺少必要参数");
        }
        AgentSettings oldParam = agentSetMapper.findBasicInfo(param.getAgentId());
        Long id = oldParam.getId();
        Integer integer = SpringBeanUtil.helpUpdateColumn(AgentSettings.class, oldParam, param);
        if(integer<1){
            return R.ok();
        }
        if (!sysUserService.judgeIfAdmin(sysUserService.getSysUserId())) {
            if (AgentSettings.State.REJECTED.getState().equals(oldParam.getState())) {
                //假如说代理商修改的是被驳回的，则修改之后应为待审核
                param.setState(AgentSettings.State.UNAUDITED.getState());
                param.setRemark("");
            }
        }
        if (StringUtils.isNotBlank(param.getDomain())) {
            List<Long> setList = agentSetMapper.queryAgentSettingByDomain(param.getDomain());
            if(!CollectionUtils.isEmpty(setList)){
                return R.error("该域名已被其他代理商占用，请使用其他域名");
            }
        }
        param.setId(id);
        int i = agentSetMapper.updateAgentSettings(param);
        if(i!=1){
            return R.error();
        }
        return R.ok();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateAgentSettings(AgentSettings agentSettings){
        AgentSettings oldSettings = agentSetMapper.selectByPrimaryKey(agentSettings.getId());
        if (oldSettings == null) {
            return R.error("数据不存在，请刷新");
        }
        agentSettings.setAgentId(oldSettings.getAgentId());


        if (StringUtils.isNotBlank(agentSettings.getDomain())) {
            String domain = agentSettings.getDomain();
            AgentSettings settings = new AgentSettings();
            settings.setDomain(domain);
            AgentSettings oldAgentSettings = agentSetMapper.selectByDomain(domain);
            if (oldAgentSettings!=null) {
                return R.error("操作失败；域名已被使用");
            }
        }

        if (StringUtils.isNotBlank(agentSettings.getAgreement())) {
            String path = uploadPath + "agreement";
            File directory = new File(path);
            if (!directory.exists()) {
                boolean flag = directory.mkdirs();
                if (!flag) {
                    throw new RuntimeException("创建" + directory + "目录失败！");
                }
            }

            File file = new File(path + "/" + oldSettings.getAgentId() + ".txt");
            FileUtil.writeUtf8String(agentSettings.getAgreement(), file);
        }

        int result = agentSetMapper.updateByPrimaryKeySelective(agentSettings.setAgreement(null));
        return R.ok(result);
    }

    @Override
    public R auditAgentSetting(Long agentId, Integer state,String remark,Integer officialWeb) {
        if(state==AgentSettings.State.REJECTED.getState()){
            //假如说为驳回的情况下
            officialWeb = null;
            //不修改state
        }
        int i = agentSetMapper.auditAgentSetting(agentId,state,remark,officialWeb);
        if(i!=1){
            return R.error("审核代理商设置失败");
        }
        return R.ok();
    }
}
