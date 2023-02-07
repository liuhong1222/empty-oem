package com.cc.oem.modules.agent.enums;

/**
 * @author chenzj
 * @since 2018/10/16
 */
public enum MessageTypeEnum {
    //消息类型 1-系统消息 2-活动通知 3-故障通知 4-更新通知
    SYSTEM("0", "系统消息"),
    ACTIVITY("2", "活动通知"),
    ERROR("3", "故障通知"),
    UPDATE("1", "更新通知"),
    ;

    private String code;
    private String descri;

    MessageTypeEnum(String code, String descri) {
        this.code = code;
        this.descri = descri;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public static MessageTypeEnum getEnumByCode(String code) {
        for (MessageTypeEnum newsAuditStateEnum : MessageTypeEnum.values()) {
            if (newsAuditStateEnum.getCode().equals(code)) {
                return newsAuditStateEnum;
            }
        }
        return null;
    }

    public static String getDescriByCode(String code) {
        MessageTypeEnum newsAuditStateEnum = getEnumByCode(code);
        return newsAuditStateEnum == null ? null : newsAuditStateEnum.getDescri();
    }

}
