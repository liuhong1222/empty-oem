package com.cc.oem.modules.agent.enums;

/**
 *  代理商账户充值操作的角色  0-代理商  1-管理员  2-用户
 * @author chenzj
 * @since 2018/8/10
 */
public enum AgentAuditStateEnum {
    NOT_SET(-1, "待设置"),
    IN_REVIEW(0, "待审核"),
    APPROVED(1, "使用中"),
    REJECTED(2, "已驳回"),
;

    private Integer code;
    private String descri;

    AgentAuditStateEnum(Integer code, String descri) {
        this.code = code;
        this.descri = descri;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescri() {
        return descri;
    }

    public static AgentAuditStateEnum getEnumByCode(Integer code) {
        if (code==null) {
            return null;
        }
        for (AgentAuditStateEnum value : AgentAuditStateEnum.values()) {
            if (value.getCode().intValue()==code.intValue()) {
                return value;
            }
        }
        return null;
    }
}
