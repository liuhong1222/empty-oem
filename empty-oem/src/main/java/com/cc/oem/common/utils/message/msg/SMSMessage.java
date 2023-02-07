package com.cc.oem.common.utils.message.msg;


import com.cc.oem.common.utils.JacksonUtil;

/**
 * @author wangqiang
 * @since 2018/6/30
 */
//TODO
public class SMSMessage {

    /**
     * 用户账号，必填
     */
    private String account;
    /**
     * 用户密码，必填
     */
    private String password;
    /**
     * 短信内容。长度不能超过536个字符，必填
     */
    private String msg;
    /**
     * 机号码。多个手机号码使用英文逗号分隔，必填
     */
    private String phone;

    /**
     * 定时发送短信时间。格式为yyyyMMddHHmm，值小于或等于当前时间则立即发送，默认立即发送，选填
     */
    private String sendtime;
    /**
     * 是否需要状态报告（默认false），选填
     */
    private String report;
    /**
     * 下发短信号码扩展码，纯数字，建议1-3位，选填
     */
    private String extend;
    /**
     * 该条短信在您业务系统内的ID，如订单号或者短信发送记录流水号，选填
     */
    private String uid;


    public String getAccount() {
        return account;
    }


    public void setAccount(String account) {
        this.account = account;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getMsg() {
        return msg;
    }


    private void setMsg(String msg) {
        this.msg = msg;
    }


    public String getPhone() {
        return phone;
    }


    private void setPhone(String phone) {
        this.phone = phone;
    }


    public String getSendtime() {
        return sendtime;
    }


    private void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }


    public String getReport() {
        return report;
    }


    private void setReport(String report) {
        this.report = report;
    }


    public String getExtend() {
        return extend;
    }


    private void setExtend(String extend) {
        this.extend = extend;
    }


    public String getUid() {
        return uid;
    }


    private void setUid(String uid) {
        this.uid = uid;
    }


    public String toJson() {
        return JacksonUtil.toJson(this);
    }


    public static class SMSMessageBuilder {
        /**
         * Generated by POJO Builder Generate Plugin.
         */

        private final SMSMessage instance = new SMSMessage();


        public SMSMessageBuilder withAccount(String account) {
            instance.setAccount(account);
            return this;
        }


        public SMSMessageBuilder withPassword(String password) {
            instance.setPassword(password);
            return this;
        }


        public SMSMessageBuilder withMsg(String msg) {
            instance.setMsg(msg);
            return this;
        }


        public SMSMessageBuilder withPhone(String phone) {
            instance.setPhone(phone);
            return this;
        }


        public SMSMessageBuilder withSendtime(String sendtime) {
            instance.setSendtime(sendtime);
            return this;
        }


        public SMSMessageBuilder withReport(String report) {
            instance.setReport(report);
            return this;
        }


        public SMSMessageBuilder withExtend(String extend) {
            instance.setExtend(extend);
            return this;
        }


        public SMSMessageBuilder withUid(String uid) {
            instance.setUid(uid);
            return this;
        }


        public SMSMessage build() {
            SMSMessage copy = new SMSMessage();
            copy.setAccount(instance.getAccount());
            copy.setPassword(instance.getPassword());
            copy.setMsg(instance.getMsg());
            copy.setPhone(instance.getPhone());
            copy.setSendtime(instance.getSendtime());
            copy.setReport(instance.getReport());
            copy.setExtend(instance.getExtend());
            copy.setUid(instance.getUid());
            return copy;
        }

    }


    public static SMSMessageBuilder builder() {
        /**
         * Generated by POJO Builder Generate Plugin.
         */
        return new SMSMessageBuilder();
    }

}