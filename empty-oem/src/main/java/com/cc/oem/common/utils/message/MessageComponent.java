package com.cc.oem.common.utils.message;

import com.alibaba.fastjson.JSONObject;
import com.cc.oem.common.utils.message.msg.SMSMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Description: User: liutao Date: 2018-06-08 Time: 13:34
 */
//TODO
@Component
public class MessageComponent extends BaseMessageSender {

    private static final Logger logger = LoggerFactory
            .getLogger(MessageComponent.class);

    @Value("${notifymessage.smsUrl}")
    private String smsUrl;
    @Value("${notifymessage.account}")
    private String account;
    @Value("${notifymessage.pswd}")
    private String pswd;
    @Value("${notifymessage.sign}")
    private String sign;
    @Value("${notifymessage.notifyMsg}")
    private  String notifyMsg;

    public boolean sendMsg(String mobile, String content) {
        content = String.format(notifyMsg, sign,content);
        logger.info("短信接口请求参数，手机号：{}，内容：{}", mobile, content);
        // 调用发送短信接口
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("account", account);
        params.add("pswd", pswd);
        params.add("mobile", mobile);
        params.add("msg", content);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(smsUrl, requestEntity, String.class);

        if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("短信接口调用成功，手机号：{}，内容：{}, 返回信息：{}", mobile, content, responseEntity.getBody());
            String[] result = responseEntity.getBody().split("\\n");
            if (result[0].endsWith(",0")) {
                return true;
            }
        }
        return false;
    }




//    /**
//     * 发送钉钉告警，过滤短期内的部分重复消息
//     *
//     * @param content 消息内容
//     */
//    public void alertByDingDing(String content) {
//        Byte cacheMsg = sendMsgCache.asMap().putIfAbsent(content, SENDED);
//        if (cacheMsg == null) {
//            sendDingDingMessageSync(content);
//        } else {
//            logger.warn("5分钟内已发送过相同的告警内容，暂不发送");
//        }
//    }


//    public void alertByEmail(final EmailMessage email) {
//        try {
//            timeLimiter.callWithTimeout(() -> {
//                sendEmail0(email);
//                return true;
//            }, 5000, TimeUnit.MILLISECONDS, false);
//        } catch (Exception e) {
//            logger.error("send email fail", e);
//        }
//    }


//    public void sendEmail(final EmailMessage email) throws MessageException {
//        try {
//            sendEmail0(email);
//        } catch (Exception e) {
//            throw new MessageException(e);
//        }
//    }


//    private void sendDingDingMessageSync(final String content) {
//        //异步发送，不影响主业务
//        getExecutor().execute(() -> {
//            String message = String.format(DING_MESSAGE_TEMPLATE, content);
//            //logger.info("钉钉消息发送：{}", message);
//            String result = sendSmsByPost(dingUrl, message);
//            logger.info("钉钉消息返回：{}", result);
//        });
//    }


//    public static void main(String[] a) {
//        MessageComponent messageComponent = new MessageComponent();
//        messageComponent.alertByDingDing("这是一条测试消息2，重复内容");
//        messageComponent.alertByDingDing("这是一条测试消息2，重复内容");
//    }

}
