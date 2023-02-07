package com.cc.oem.modules.agent.enums;

/**
 * @author chenzj
 * @since 2018/8/13
 */
public enum AgentRechargePayTypeEnum {
    ALIPAY(1,"支付宝"),
    UNIONPAY(0,"对公转账"),
    CHUANGLAN(2,"注册赠送"),
    ADMIN(3,"赠送"),
    CORPORATE_ACCOUNT(4,"对公支付宝转账"),
    PRESENT(5,"对私支付宝"),
    USER_RECHAGE_ONLINE(6,"对私微信"),
    USER_REFUND(7,"对私转账"),
    WEIXIN(8,"微信扫码付"),
    AUTH_PRESENT(10,"认证赠送")

    ;
    private Integer code;
    private String descri;

    AgentRechargePayTypeEnum(Integer code, String descri) {
        this.code = code;
        this.descri = descri;
    }

    public static AgentRechargePayTypeEnum getEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (AgentRechargePayTypeEnum value : AgentRechargePayTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
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

    public static String getDescri(Integer code){
        if (code != null && AgentRechargePayTypeEnum.getEnumByCode(code) != null) {
            return getEnumByCode(code).getDescri();
        } else {
            return "其他";
        }
    }

}
