package com.cc.oem.modules.agent.enums;

/**
 *  代理商账户充值操作的角色  0-代理商  1-管理员  2-用户
 * @author chenzj
 * @since 2018/8/10
 */
public enum AgentOrderRoleTypeEnum {
    AGENT(0, "代理商"),
    MANAGER(1, "管理员"),
    USER(2, "用户"),
;



    private Integer code;
    private String descri;

    AgentOrderRoleTypeEnum(Integer code, String descri) {
        this.code = code;
        this.descri = descri;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescri() {
        return descri;
    }

    public static AgentOrderRoleTypeEnum getEnumByCode(Integer code) {
        if (code==null) {
            return null;
        }
        for (AgentOrderRoleTypeEnum value : AgentOrderRoleTypeEnum.values()) {
            if (value.getCode().intValue()==code.intValue()) {
                return value;
            }
        }
        return null;
    }
}
