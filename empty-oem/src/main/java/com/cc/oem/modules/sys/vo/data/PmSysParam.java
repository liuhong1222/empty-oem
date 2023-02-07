package com.cc.oem.modules.sys.vo.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 系统参数实体类
 * @author liuh
 * @date 2021年3月27日
 */
@Data
public class PmSysParam{

	private Long id;
	/**
	 * 参数名
	 */
	@NotBlank(message = "参数名不能为空")
	private String paramKey;
	
	/**
	 * 参数值
	 */
	@NotBlank(message = "参数值不能为空")
	private String paramValue;
	
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 获取属性值数组
	 * @date 2021/9/7
	 * @param
	 * @return java.lang.String[]
	 */
	public String[] fields() {
	    return new String[] {this.getParamKey(), this.getParamValue(), this.getRemark()};
	}
}
