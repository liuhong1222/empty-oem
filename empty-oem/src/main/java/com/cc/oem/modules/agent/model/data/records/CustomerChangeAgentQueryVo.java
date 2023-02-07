package com.cc.oem.modules.agent.model.data.records;

import com.cc.oem.common.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 客户转代理商记录 查询结果对象
 * </pre>
 *
 * @author rivers
 * @since 2020-02-21
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CustomerChangeAgentQueryVo对象", description = "客户转代理商记录查询参数")
public class CustomerChangeAgentQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "转出代理商编号")
    private Long sourceAgentId;

    @ApiModelProperty(value = "转入代理商编号")
    private Long destAgentId;

    @ApiModelProperty(value = "转出公司名称")
    @ExcelField(value = "转出代理商", order = 4)
    private String sourceCompanyName;

    @ApiModelProperty(value = "转出公司简称")
    private String sourceCompanyShortName;

    @ApiModelProperty(value = "转入公司名称")
    @ExcelField(value = "转入代理商", order =5)
    private String destCompanyName;

    @ApiModelProperty(value = "转入公司简称")
    private String destCompanyShortName;

    @ApiModelProperty(value = "客户编号")
    private Long customerId;

    @ApiModelProperty(value = "客户名称")
    @ExcelField(value = "客户名称", order = 2)
    private String name;

    @ApiModelProperty(value = "手机号码")
    @ExcelField(value = "手机号码", order = 1)
    private String phone;

    @ApiModelProperty(value = "备注")
    @ExcelField(value = "备注", order = 7)
    private String remark;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "操作时间")
    @ExcelField(value = "操作时间", order = 6)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "注册时间")
    @ExcelField(value = "注册时间", order = 3)
    private Date registerTime;

    @ApiModelProperty(value = "身份证名称")
    private String idCardName;

    @ApiModelProperty(value = "已认证企业名称")
    private String certifiedCompanyName;

    public String getName() {
        if (StringUtils.isNotBlank(certifiedCompanyName)) {
            return certifiedCompanyName;
        } else if (StringUtils.isNotBlank(idCardName)) {
            return idCardName;
        } else {
            return "";
        }
    }

}