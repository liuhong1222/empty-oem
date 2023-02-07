package com.cc.oem.modules.agent.model.param.records;

import com.cc.oem.modules.agent.model.param.TimesParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 实时检测记录 查询参数对象
 * </pre>
 *
 * @author rivers
 * @date 2021-01-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "RealtimeCheckQueryParam对象", description = "实时检测记录查询参数")
public class RealtimeCheckQueryParam extends TimesParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代理商ID")
    private Long agentId;

    @ApiModelProperty(value = "客户编号")
    private Long customerId;

    /**
     * 创建时间的开始时间
     */
    @ApiModelProperty(value = "创建起始时间")
    private String createTimeFrom;

    /**
     * 创建时间的终止时间
     */
    @ApiModelProperty(value = "创建终止时间")
    private String createTimeEnd;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "代理商名称")
    private String agentName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    private List<Long> custList;

    public void initTimeParam(){
        if (StringUtils.isNotBlank(this.createTimeFrom)){
            this.createTimeFrom = this.createTimeFrom+" 00:00:00";
        }
        if (StringUtils.isNotBlank(this.createTimeEnd)){
            this.createTimeEnd = this.createTimeEnd+" 23:59:59";
        }
    }
}
