package com.cc.oem.common.utils.message.msg;

/**
 * @author wangqiang
 * @since 2018/6/30
 */
public class Messages {
    public static EmailMessage.EmailMessageBuilder createEmail() {
        return EmailMessage.builder();
    }

    public static DingdingMessage.DingdingMessageBuilder createDingdingMessage(){
        return DingdingMessage.builder();
    }

    public static SMSMessage.SMSMessageBuilder createSMSMessage(){
        return SMSMessage.builder();
    }
}
