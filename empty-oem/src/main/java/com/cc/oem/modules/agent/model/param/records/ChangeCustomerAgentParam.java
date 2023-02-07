package com.cc.oem.modules.agent.model.param.records;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@ApiModel(value = "更换客户的代理商入参", description = "更换客户的代理商入参")
public class ChangeCustomerAgentParam {

    @ApiModelProperty("客户Id")
    @NotNull(message = "客户Id不能为空")
    private Long customerId;

    @ApiModelProperty("代理商Id")
    @NotNull(message = "代理商Id不能为空")
    private Long destAgentId;

    @ApiModelProperty("备注")
    @Length(max = 200, message = "备注最大长度200")
    private String remark;
}
