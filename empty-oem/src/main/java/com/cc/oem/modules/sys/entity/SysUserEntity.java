package com.cc.oem.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.IdType;
import com.cc.oem.common.annotation.ExcelField;
import com.cc.oem.common.validator.group.AddGroup;
import com.cc.oem.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 * 
 * @author wade
 *
 * update latest 2021/10/29
 */
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ID_WORKER)
	private Long id;

	@ApiModelProperty(value = "用户名")
	@javax.validation.constraints.NotBlank(message = "用户名不能为空")
	@Length(max = 20, message = "用户名长度超过最大值")
	private String username;

	@ApiModelProperty(value = "昵称")
	@Length(max = 20, message = "昵称长度超过最大值")
	private String nickname;

	@ApiModelProperty(value = "密码")
	@Length(max = 64, message = "密码长度超过最大值")
	private String password;

	@ApiModelProperty(value = "盐值")
	private String salt;

	@ApiModelProperty(value = "手机号码")
	@NotBlank(message = "手机号码不能为空")
	@Length(max = 20, message = "密码长度超过最大值")
	private String phone;

	@ApiModelProperty(value = "性别，0：女，1：男，默认1")
	@Min(value = 0, message = "性别输入有误")
	@Max(value = 1, message = "性别输入有误")
	private Integer gender;

	@ApiModelProperty(value = "头像")
	@Length(max = 200, message = "头像长度超过最大值")
	private String head;

	@ApiModelProperty(value = "联系邮箱")
	@Length(max = 64, message = "联系邮箱长度超过最大值")
	private String email;

	@ApiModelProperty(value = "备注")
	@Length(max = 200, message = "备注长度超过最大值")
	private String remark;

	@ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
	@Max(value = 2, message = "状态值不正确")
	@Min(value = 0, message = "状态值不正确")
	private Integer state;

	@ApiModelProperty(value = "代理商id")
	private Long agentId;

	@ApiModelProperty(value = "角色id")
	@NotNull(message = "角色id不能为空")
	private Long roleId;

	@ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
	@Null(message = "逻辑删除不用传")
	private Integer deleted;

	@ApiModelProperty(value = "版本")
	@Null(message = "版本不用传")
	@Version
	private Integer version;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "修改时间")
	private Date updateTime;

}
