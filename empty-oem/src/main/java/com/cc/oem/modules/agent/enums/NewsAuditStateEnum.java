package com.cc.oem.modules.agent.enums;

/**
 * @author chenzj
 * @since 2018/10/7
 */
public enum NewsAuditStateEnum {

    TO_AUDIT(1, "发布待审核"),
    AUDITED(3, "已审核"),
    MODIFIED(2, "修改待审核"),
    REJECTED(4, "已驳回"),
    DELETED(5, "已删除"),
    ;

    private Integer code;
    private String descri;

    NewsAuditStateEnum(Integer code, String descri) {
        this.code = code;
        this.descri = descri;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public static NewsAuditStateEnum getEnumByCode(Integer code) {
        for (NewsAuditStateEnum newsAuditStateEnum : NewsAuditStateEnum.values()) {
            if (newsAuditStateEnum.getCode().equals(code)) {
                return newsAuditStateEnum;
            }
        }
        return null;
    }

    public static String getDescriByCode(Integer code) {
        NewsAuditStateEnum newsAuditStateEnum = getEnumByCode(code);
        return newsAuditStateEnum == null ? null : newsAuditStateEnum.getDescri();
    }

}
