package com.cc.oem.modules.agent.enums;

/**
 *  代理商账户充值操作的角色  1-充值  2-退款  3-给用户充值
 * @author chenzj
 * @since 2018/8/10
 */
public enum AgentOperationTypeEnum {
    AGENT_RECHARGE(1, "充值"),
    REFUND(2, "退款"),
    USER_RECHARGE(3, "给用户充值"),
;



    private Integer code;
    private String descri;

    AgentOperationTypeEnum(Integer code, String descri) {
        this.code = code;
        this.descri = descri;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescri() {
        return descri;
    }

    public static AgentOperationTypeEnum getEnumByCode(Integer code) {
        if (code==null) {
            return null;
        }
        for (AgentOperationTypeEnum value : AgentOperationTypeEnum.values()) {
            if (value.getCode().intValue()==code.intValue()) {
                return value;
            }
        }
        return null;
    }
}
