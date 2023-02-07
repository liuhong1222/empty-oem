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
 * 我的消息管理
 * </pre>
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "MyNotice对象", description = "我的消息管理")
public class MyNotice{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "客户编号")
    @NotNull(message = "客户编号不能为空")
    private Long customerId;

    @ApiModelProperty(value = "通知编号")
    @NotNull(message = "通知编号不能为空")
    private Long noticeId;

    @ApiModelProperty(value = "消息标题")
    @NotBlank(message = "消息标题不能为空")
    private String title;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "消息类别，0：系统消息，1：更新通知，2：活动通知，3：故障通知")
    private Integer noticeType;

    @ApiModelProperty(value = "是否已读，0：未读，1：已读")
    private Integer haveRead;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "已读时间")
    private Date updateTime;

}
