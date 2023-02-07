package com.cc.oem.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cc.oem.common.utils.R;
import com.cc.oem.common.utils.ShiroUtils;
import com.cc.oem.modules.agent.constant.AgentConstant;
import com.cc.oem.modules.agent.dao.AgentMapper;
import com.cc.oem.modules.agent.entity.Agent;
import com.cc.oem.modules.sys.dao.SysUserDao;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.service.SysUserService;
import com.cc.oem.modules.sys.vo.data.AgentAccountData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    AgentMapper agentMapper;


    @Override
    public R queryPage(String phone,String roleId,Integer currentPage,Integer pageSize,Long agentId) {

        PageHelper.startPage(currentPage, pageSize);
        Long finalAgentId = 1l;
        if("2".equals(roleId)){
            if(agentId!=null){
                finalAgentId = agentId;
            }else{
                finalAgentId = ShiroUtils.getUserEntity().getAgentId();
            }
            List<AgentAccountData> list =  sysUserDao.queryAgentAccountList(roleId,finalAgentId,phone);
            PageInfo<AgentAccountData> pageInfo = new PageInfo<>(list);
            return R.ok(pageInfo);
        }
        Wrapper<SysUserEntity> eq = new EntityWrapper<SysUserEntity>()
                .eq("role_id", roleId);
        if(StringUtils.isNotEmpty(phone)){
            eq.like("phone", "%" + phone + "%");
        }
        //获取用户列表
        List<SysUserEntity> list = this.selectList(eq);
        PageInfo<SysUserEntity> pageInfo = new PageInfo<>(list);

        return R.ok(pageInfo);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    @Transactional
    public void save(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()+salt));
        user.setSalt(salt);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        this.insert(user);
    }

    @Override
    @Transactional
    public R update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            SysUserEntity old = sysUserDao.selectById(user.getId());
            user.setPassword(DigestUtils.sha256Hex(user.getPassword()+old.getSalt()));
        }
        this.updateById(user);
        return R.ok();
    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.deleteBatchIds(Arrays.asList(userId));
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<SysUserEntity>().eq("id", userId).eq("password", password));
    }

    /**
     * 根据userId，更新用户名和手机号
     */
    @Override
    public void updateUserNameAndMobileByUserId(Long userId, String userName, String mobile) {
        sysUserDao.updateUserNameAndMobileByUserId(userId, userName, mobile);
    }


    /**
     * 根据userId，更新代理商副账号信息
     */
    @Override
    public void updateSlaverSysUserByUserId(Long userId, String userName, String mobile, String realName,String email) {
        sysUserDao.updateSlaverSysUserByUserId(userId, userName, mobile, realName,email);
    }

    /**
     * 更新密码
     */
    @Override
    public void updatePasswordByUserId(Long userId, String password) {
        sysUserDao.updatePasswordByUserId(userId, password);
    }

    @Override
    public void updateEmailByUserId(Long userId, String email) {
        sysUserDao.updateEmailByUserId(userId, email);
    }

    @Override
    public void updateRealNameAndEmailByUserId(Long userId, String realName, String email) {
        sysUserDao.updateRealNameAndEmailByUserId(userId, realName, email);
    }

    /**
     * 检查角色是否越权  TODO 判断是否越权
     */
    private void checkRole(SysUserEntity user) {
        if (user.getRoleId()==null) {
            return;
        }

//		//查询用户创建的角色列表
//		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());
//
//		//判断是否越权
//		if(!roleIdList.containsAll(user.getRoleIdList())){
//			throw new RRException("新增用户所选角色，不是本人创建");
//		}
    }

    @Override
    public Long getSysUserId() {
        SysUserEntity sysUserEntity = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        if (sysUserEntity == null) {
            throw new AuthorizationException();
        }
        return sysUserEntity.getId();
    }

    @Override
    public Long selectAgentIdBySysUserId(Long sysUserId) {
        if (sysUserId == null) {
            return null;
        }
        Agent agent = agentMapper.selectAgentBySysUserId(sysUserId);
        if (agent != null) {
            return agent.getId();
        }
        return null;
    }

    @Override
    public boolean judgeIfAdmin(Long sysUserId) {
        if (sysUserId == null) {
            return false;
        }
        if (AgentConstant.Admin_Role_Id.equals(ShiroUtils.getUserEntity().getRoleId())) {
            return true;
        }
        return false;
    }

    /**
     * 禁用账号
     */
    @Override
    public void disableByUserId(Long sysUserId) {
        sysUserDao.disableByUserId(sysUserId);
    }

    /**
     * 启用账号
     */
    @Override
    public void enableByUserId(Long sysUserId) {
        sysUserDao.enableByUserId(sysUserId);
    }

    @Override
    public List<SysUserEntity> queryByAgentId(Long agentId) {
        return baseMapper.queryByAgentId(agentId);
    }

}
