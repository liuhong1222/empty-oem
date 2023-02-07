package com.cc.oem.modules.agent.model.param;

import java.io.Serializable;

/**
 * author zhangx
 * date  2018/8/2 16:10
 */
public class NewsSendParam implements Serializable {

    private static final long serialVersionUID = -6398390749344165195L;

    /**
     * 最多100
     */
    private String title;

    /**
     * 大量不限
     */
    private String message;

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
