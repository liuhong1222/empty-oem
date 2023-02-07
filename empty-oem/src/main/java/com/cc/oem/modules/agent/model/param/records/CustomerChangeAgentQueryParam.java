package com.cc.oem.modules.agent.model.param.records;

import com.cc.oem.modules.agent.model.param.TimesParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <pre>
 * 客户转代理商记录 查询参数对象
 * </pre>
 *
 * @author rivers
 * @since 2020-02-21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CustomerChangeAgentQueryParam对象", description = "客户转代理商记录查询参数")
public class CustomerChangeAgentQueryParam extends TimesParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("转出代理商名称")
    private String sourceAgentName;

    @ApiModelProperty("转入代理商名称")
    private String destAgentName;

    @ApiModelProperty("客户名称")
    private String name;

    @ApiModelProperty("客户手机号")
    private String phone;
}
