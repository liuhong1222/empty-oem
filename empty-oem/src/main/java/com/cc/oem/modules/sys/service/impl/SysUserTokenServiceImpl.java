package com.cc.oem.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cc.oem.common.utils.ShiroUtils;
import com.cc.oem.modules.agent.dao.AgentMapper;
import com.cc.oem.modules.agent.entity.Agent;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.sys.dao.SysUserTokenDao;
import com.cc.oem.modules.sys.entity.SysUserTokenEntity;
import com.cc.oem.modules.sys.oauth2.TokenGenerator;
import com.cc.oem.modules.sys.service.SysUserService;
import com.cc.oem.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    @Autowired
    AgentMapper agentMapper;
    @Autowired
    private SysUserService sysUserService;


    @Override
    public R createToken(long userId) {

        Agent agent = agentMapper.selectAgentBySysUserId(userId);
        if (agent != null && agent.getState() != null) {
            if (agent.getState().intValue() == 0) {
                return R.error("代理商账号已被禁用");
            }
//			if(agent.getState().intValue()==2){
//				return R.error("代理商账号已被锁定");
//			}
        }
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUserTokenEntity tokenEntity = this.selectById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            this.insert(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            this.updateById(tokenEntity);
        }
        //获取用户角色列表
        List<Long> roleIdList = new ArrayList<>();
        roleIdList.add(sysUserService.selectById(userId).getRoleId());
        R r = R.ok().put("token", token).put("expire", EXPIRE).put("roleIdList", roleIdList);

        return r;
    }

    @Override
    public void logout(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }
}
