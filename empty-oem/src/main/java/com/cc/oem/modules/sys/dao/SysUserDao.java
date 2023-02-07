package com.cc.oem.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.vo.data.AgentAccountData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
@Repository
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);


	/**
	 * 根据userId，更新用户名和手机号
	 */
	int updateUserNameAndMobileByUserId(@Param("userId")Long userId, @Param("username")String username, @Param("mobile")String mobile);

	/**
	 * 根据userId，更新代理商副账号信息
	 */
	int updateSlaverSysUserByUserId(@Param("userId")Long userId, @Param("username")String username, @Param("mobile")String mobile,
									@Param("realName")String realName,@Param("email")String email);

	/**
	 * 更新密码
	 */
	void updatePasswordByUserId(@Param("userId")Long userId, @Param("password")String password);

	/**
	 * 更新邮箱
	 */
	void updateEmailByUserId(@Param("userId")Long userId, @Param("email")String email);

	/**
	 * 更新用户姓名和邮箱
	 */
	void updateRealNameAndEmailByUserId(@Param("userId")Long userId, @Param("realName")String realName,@Param("email")String email);

	/**
	 * 更新手机号
	 */
	void updateMobileByUserId(@Param("userId")Long userId, @Param("mobile")String mobile);

	/**
	 * 根据手机号，查询系统用户
	 */
	SysUserEntity queryByUserMobile(@Param("mobile") String mobile);

	List<SysUserEntity> queryListByUserMobile(@Param("mobile") String mobile);


	/**
	 * 禁用账号
	 */
	void disableByUserId(@Param("id")Long userId);

    /**
     * 启用账号
     */
    void enableByUserId(@Param("userId")Long userId);

    List<SysUserEntity> queryByAgentId(Long agentId);

    boolean insertEntity(SysUserEntity user);

	List<AgentAccountData> queryAgentAccountList(@Param("roleId") String roleId, @Param("agentId") Long agentId,@Param("phone") String phone);
}
