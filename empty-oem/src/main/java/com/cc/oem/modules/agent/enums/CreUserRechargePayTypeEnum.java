package com.cc.oem.modules.agent.enums;

/**
 * @author chenzj
 * @since 2018/8/13
 */
public enum CreUserRechargePayTypeEnum {

    ALIPAY(1,"支付宝"),
    UNIONPAY(2,"银联"),
    //创蓝充值
    CHUANGLAN(3,"对公转账"),
    ADMIN(4,"管理员充值"),
    //代理商在oem系统给用户以支付宝方式充值(支付宝)
    OEM_ALIPAY(5,"支付宝"),
    //代理商在oem系统给用户以对公转账方式充值(对公转账)
    OEM_CORPORATE_ACCOUNT(6,"对公转账"),
    //代理商在oem系统给用户以赠送方式充值(赠送)
    USER_PRESENT(7,"赠送"),
    //代理商给用户退款(退款)
    USER_REFUND(8,"退款"),
    ;
    private Integer code;
    private String descri;

    CreUserRechargePayTypeEnum(Integer code, String descri) {
        this.code = code;
        this.descri = descri;
    }

    public static CreUserRechargePayTypeEnum getEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (CreUserRechargePayTypeEnum value : CreUserRechargePayTypeEnum.values()) {
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
        if (code != null && CreUserRechargePayTypeEnum.getEnumByCode(code) != null) {
            return getEnumByCode(code).getDescri();
        } else {
            return "其他";
        }
    }

}
