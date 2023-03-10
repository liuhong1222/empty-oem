package com.cc.oem.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 * 
 * @author wade
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
