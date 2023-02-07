package com.cc.oem.modules.sys.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 * 
 * @author wade
 */
@TableName("sys_role")
@Data
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色ID
	 */
	@TableId(value = "id", type = IdType.ID_WORKER)
	private Long id;

	/**
	 * 角色名称
	 */
	@NotBlank(message="角色名称不能为空")
	private String name;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建者ID
	 */
	private String code;

	private Integer type;

	private Integer version;

	private Date updateTime;

	@TableField(exist=false)
	private List<Long> menuIdList;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

}
