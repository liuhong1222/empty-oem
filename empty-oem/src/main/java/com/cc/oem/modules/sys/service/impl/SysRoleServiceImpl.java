package com.cc.oem.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.Constant;
import com.cc.oem.common.utils.PageUtils;
import com.cc.oem.common.utils.Query;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.dao.SysRoleDao;
import com.cc.oem.modules.sys.entity.SysRoleEntity;
import com.cc.oem.modules.sys.service.SysRoleMenuService;
import com.cc.oem.modules.sys.service.SysRoleService;
import com.cc.oem.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private Snowflake snowflake;


	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String roleName = (String)params.get("roleName");

		Page<SysRoleEntity> page = this.selectPage(
			new Query<SysRoleEntity>(params).getPage(),
			new EntityWrapper<SysRoleEntity>()
				.like(StringUtils.isNotBlank(roleName),"name", roleName));

		return new PageUtils(page);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleEntity role) {
        role.setCreateTime(new Date());
        role.setId(snowflake.nextId());
        this.insert(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {
        this.updateById(role);
        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.deleteBatchIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);
    }
}
