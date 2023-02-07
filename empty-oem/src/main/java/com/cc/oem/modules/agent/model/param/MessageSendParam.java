package com.cc.oem.modules.agent.model.param;

import java.io.Serializable;

/**
 * author zhangx
 * date  2018/5/21 17:01
 */
public class MessageSendParam implements Serializable {

    private static final long serialVersionUID = -6398390749344165195L;

    /**
     * '消息类型 1-系统消息 2-活动通知 3-故障通知 4-更新通知'
     */
    private String type;

    private String userIdList;
    /**
     * 最多100
     */
    private String title;

    /**选择类型 1全部，2 部分用户*/
    private String selectType;

    /**
     * 大量不限
     */
    private String message;

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(String userIdList) {
        this.userIdList = userIdList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
