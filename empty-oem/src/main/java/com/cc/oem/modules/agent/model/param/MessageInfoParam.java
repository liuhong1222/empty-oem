package com.cc.oem.modules.agent.model.param;

import lombok.Data;

/**
 * 消息列表查询参数
 *
 */
@Data
public class MessageInfoParam extends PageParam {
    /**
     * 代理商id
     */
    private Long agentId;

    /**
     * 代理商名称
     */
    private String agentName;

    /**
     * 审核状态 1-发布待审核  3-已审核 2-修改待审核  4-已驳回  5删除
     */
    private Integer state;

    /**
     * 代理商手机号
     */
    private String agentMobile;

    /**
     * 消息类型 0-系统消息 2-活动通知 3-故障通知 1-更新通知
     */
    private String noticeType;

}