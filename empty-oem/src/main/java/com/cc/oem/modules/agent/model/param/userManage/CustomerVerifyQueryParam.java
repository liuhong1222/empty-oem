package com.cc.oem.modules.agent.model.param.userManage;

import com.cc.oem.modules.agent.model.param.TimesParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <pre>
 * 客户退款记录 查询参数对象
 * </pre>
 *
 * @author rivers
 * @since 2020-02-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CustomerVerifyQueryParam对象", description = "客户认证记录查询参数")
public class CustomerVerifyQueryParam extends TimesParam {
    @ApiModelProperty("提交开始时间")
    private Date createTimeFrom;

    @ApiModelProperty("提交结束时间")
    private Date createTimeEnd;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "审批状态，0：待审核，9：已认证，1：已驳回")
    private Integer state;

    private Long agentId;
}
