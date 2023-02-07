package com.cc.oem.modules.agent.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 充值套餐管理 查询参数对象
 * </pre>
 *
 * @author rivers
 * @since 2020-02-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GoodsQueryParam对象", description = "充值套餐管理查询参数")
public class GoodsQueryParam extends TimesParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代理商ID")
    private Long agentId;

    @ApiModelProperty("代理商名称")
    private String agentName;

    @ApiModelProperty(value = "产品类别，0：空号检测产品，1：实时检测产品")
    private Integer category;
}
