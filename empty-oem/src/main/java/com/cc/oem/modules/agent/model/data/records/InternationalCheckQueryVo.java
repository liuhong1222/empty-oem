package com.cc.oem.modules.agent.model.data.records;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 国际检测记录查询参数
 * @author liuh
 * @date 2022年6月13日
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "InternationalCheckQueryVo对象", description = "国际检测记录查询参数")
public class InternationalCheckQueryVo implements Serializable {

	private static final long serialVersionUID = -2730178906582149129L;

	@ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "所属代理商编号")
    private Long agentId;

    @ApiModelProperty(value = "代理商名称")
    private String agentName;

    @ApiModelProperty(value = "客户编号")
    private Long customerId;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty(value = "手机号码")
    private String phone;
    
    @ApiModelProperty(value = "国家名称")
    private String countryName;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    private String fileSize;

    @ApiModelProperty(value = "已激活数")
    private Long activeCount;
    
    @ApiModelProperty(value = "未注册数")
    private Long noRegisterCount;
    
    @ApiModelProperty(value = "未知数")
    private Long unknownCount;

    @ApiModelProperty(value = "检测文件中无效号码（条）")
    private Long illegalNumber;

    @ApiModelProperty(value = "总条数（不含无效号码）；null表示未检测条数")
    private Long totalNumber;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}