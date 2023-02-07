package com.cc.oem.modules.agent.enums;

/**
 *  0 营业执照,1, "代表签字图片,2, "公司红章图片,3 logo，4icon,5 客户身份证正面，6 客户身份证反面
 * @author chenzj
 * @since 2018/8/10
 */
public enum AgentPictureTypeEnum {
    AGENT_LICENSE(0, "营业执照图片"),
    AGENT_CONTRACT_SIGN(1, "代表签字图片"),
    AGENT_CONTRACT_SEAL(2, "公司红章图片"),
    AGENT_WEBSITE_LOGO(3, "LOGO图片"),
    AGENT_WEBSITE_ICON(4, "ICON图片"),
    Cust_Idcard_Front(5, "身份证正面"),
    Cust_Idcard_Back(6, "身份证反面");



    private Integer code;
    private String descri;

    AgentPictureTypeEnum(Integer code, String descri) {
        this.code = code;
        this.descri = descri;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescri() {
        return descri;
    }

    public static AgentPictureTypeEnum getEnumByCode(Integer code) {
        if (code==null) {
            return null;
        }
        for (AgentPictureTypeEnum value : AgentPictureTypeEnum.values()) {
            if (value.getCode().intValue()==code.intValue()) {
                return value;
            }
        }
        return null;
    }
}
