package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.redis.RedisClient;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.constant.RedisPrefixConstant;
import com.cc.oem.modules.agent.dao.AgentMapper;
import com.cc.oem.modules.agent.entity.Agent;
import com.cc.oem.modules.agent.model.param.UpdateSysUserMobileParam;
import com.cc.oem.modules.agent.service.UpdateMobileService;
import com.cc.oem.modules.sys.dao.SysUserDao;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.service.SysSendMsgService;
import com.cc.oem.modules.sys.service.SysUserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName SmsServiceImpl
 * description
 */
@Service
public class UpdateMobileServiceImpl implements UpdateMobileService {

    @Autowired
    SysUserService sysUserService;


    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    AgentMapper agentMapper;

    @Autowired
    private SysSendMsgService sysSendMsgService;
    @Autowired
    private RedisClient redisClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R sendVerifyCode(String mobile) {
        if (mobile == null || mobile.length() != 11) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "手机号不正确");
        }
        String regExp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        if (!m.find()) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "手机号不正确");
        }
        String verifyCode = String.valueOf(new Random().nextInt(89999)+100000);
        redisClient.set(RedisPrefixConstant.MODIFYUSERPHONE+mobile,verifyCode,120);
        String notifyMsg = String.format("您的oem系统联系手机号为%s,您正在修改你的联系号码，验证码为%s", mobile, verifyCode);
        sysSendMsgService.SendNotifyMsg(mobile, notifyMsg);

        return R.ok();
//        return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "验证码发送失败，请重试");
    }

    /**
     * 更新3张表，4个字段，sys_user(username,phone)  agent_contact  agent
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateMobile(UpdateSysUserMobileParam param) {

        String oldSms = redisClient.get(RedisPrefixConstant.MODIFYUSERPHONE+param.getOldMobile());
        String newSms = redisClient.get(RedisPrefixConstant.MODIFYUSERPHONE+param.getNewMobile());

        String oldCode = param.getOldCode();
        String newCode = param.getNewCode();
        Date now = new Date();
        if (oldSms == null ) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "验证码已过期，旧手机号请重发验证码");
        }
        if (newSms == null) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "验证码已过期，新手机号请重发验证码");
        }

        if (!oldCode.equals(oldSms)) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "验证码不对，请确认");
        }
        if (!newCode.equals(newSms)) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "验证码不对，请确认");
        }
        Long sysUserId = sysUserService.getSysUserId();
        Long agentId = sysUserService.selectAgentIdBySysUserId(sysUserId);

        SysUserEntity sysUserEntity = sysUserDao.selectById(sysUserId);
        if (!sysUserEntity.getPhone().equals(param.getOldMobile())) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "旧手机号不对，请刷新重试");
        }

        SysUserEntity checkUser = sysUserDao.queryByUserMobile(param.getNewMobile());
        if (checkUser != null) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "新手机号已存在，请检查手机号");
        }

        //更新账号
        int count1 = sysUserDao.updateUserNameAndMobileByUserId(sysUserId, param.getNewMobile(), param.getNewMobile());
        if (agentId != null) {
            Agent agentSysUser = agentMapper.selectByPrimaryKey(agentId);
            //判断是否主账号
            if (agentSysUser != null && agentSysUser.getLinkmanPhone().equals(param.getOldMobile())) {
                //如果是主账号，更新agent和联系人
                Agent agent = new Agent();
                agent.setId(agentId);
                agent.setLinkmanPhone(param.getNewMobile());
                int count2 = agentMapper.updateByPrimaryKeySelective(agent);
                int count3 = sysUserDao.updateUserNameAndMobileByUserId(sysUserId, param.getNewMobile(),param.getNewMobile());
                if ((count1 + count2 + count3) != 3) {
                    throw new RRException("更新失败，请重试");
                }
            }
        }
        if (count1 != 1) {
            throw new RRException("更新失败，请重试");
        }
        return R.ok("修改成功");
    }

    @Override
    public R checkMobile(String mobile) {
        List<SysUserEntity> list = sysUserDao.queryListByUserMobile(mobile);
        if (list != null && list.size() > 0) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "新手机号已存在，请检查手机号");
        }
        return R.ok();
    }


}
