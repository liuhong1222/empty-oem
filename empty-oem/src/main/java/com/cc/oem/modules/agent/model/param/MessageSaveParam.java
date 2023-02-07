package com.cc.oem.modules.agent.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 消息列表查询参数
 *
 */
@Data
public class MessageSaveParam {
    //选择类型 1全部
    public final static String SELECT_TYPE_ALL = "0";

    /**
     * 消息id
     */
    private Long agentMessageId;

    /**
     * 消息标题
     */
    @NotBlank(message = "消息标题不能为空")
    private String title;

    /**
     * 消息标题
     */
    @NotBlank(message = "消息内容不能为空")
    private String message;

    /**
     * 消息类型 0-系统消息 2-活动通知 3-故障通知 1-更新通知
     */
    @NotBlank(message = "消息类型不能为空")
    private String type;

    /**
     * 选择类型 0全部，1 部分用户
     */
    private String selectType;

    /**
     * 用户列表
     */
    private String userIdList;

}
