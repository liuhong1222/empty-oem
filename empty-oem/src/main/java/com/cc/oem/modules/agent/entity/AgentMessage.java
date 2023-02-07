package com.cc.oem.modules.agent.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AgentMessage {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "所属代理商编号")
    @NotNull(message = "所属代理商编号不能为空")
    private Long agentId;

    @ApiModelProperty(value = "所属代理商")
    private String agentName;

    @ApiModelProperty(value = "新闻标题")
    @NotBlank(message = "新闻标题不能为空")
    private String title;

    @ApiModelProperty(value = "新闻内容")
    private String content;

    @ApiModelProperty(value = "消息类别，0：系统消息，1：更新通知，2：活动通知，3：故障通知")
    private Integer noticeType;

    @ApiModelProperty(value = "发送对象，0：全部客户，1：选定客户")
    private Integer sendTarget;

    @ApiModelProperty(value = "发送对象id集合")
    private String customerIds;

    @ApiModelProperty(value = "审批状态，0：初始化，1：创建待审核，2：修改待审核，3：已审核，4：已驳回，5：已删除")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}