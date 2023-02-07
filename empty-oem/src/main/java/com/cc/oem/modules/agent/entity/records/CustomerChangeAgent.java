package com.cc.oem.modules.agent.entity.records;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <pre>
 * 客户转代理商记录
 * </pre>
 *
 * @author rivers
 * @since 2020-02-21
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CustomerChangeAgent对象", description = "客户转代理商记录")
public class CustomerChangeAgent{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "转出代理商编号")
    @NotNull(message = "转出代理商编号不能为空")
    private Long sourceAgentId;

    @ApiModelProperty(value = "转入代理商编号")
    @NotNull(message = "转入代理商编号不能为空")
    private Long destAgentId;

    @ApiModelProperty(value = "客户编号")
    @NotNull(message = "客户编号不能为空")
    private Long customerId;

    @ApiModelProperty(value = "客户名称")
    @NotBlank(message = "客户名称不能为空")
    private String name;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}
