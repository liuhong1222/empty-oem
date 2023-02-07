package com.cc.oem.common.utils.message;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author wangqiang
 * @since 2018/6/30
 */
public class BaseMessageSender {
    private static final   Logger log    = LoggerFactory.getLogger(BaseMessageSender.class);
    protected static final Byte   SENDED = (byte) 1;

    protected Cache<String, Byte> sendMsgCache = CacheBuilder.newBuilder()
                                                       .expireAfterWrite(5, TimeUnit.MINUTES)
                                                       .maximumSize(300).build();

    protected TimeLimiter timeLimiter  = new SimpleTimeLimiter();

//    private ExecutorService       executor     = new InstrumentedExecutorService(
//                                                       Executors.newFixedThreadPool(4),
//                                                       "send_message");
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//
//    protected ExecutorService getExecutor() {
//        return executor;
//    }
//
//
//    protected void sendEmail0(final EmailMessage email) throws MessagingException,
//            UnsupportedEncodingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, email.isMultipart());
//        if (StringUtils.isEmpty(email.getFromName())) {
//            helper.setFrom(email.getFrom());
//        } else {
//            helper.setFrom(email.getFrom(), email.getFromName());
//        }
//        helper.setTo(email.getTo());
//        helper.setSubject(email.getSubject());
//        helper.setText(email.getText(), email.isHtml());
//        mailSender.send(message);
//    }


    protected String sendSmsByPost(String path, String postContent) {
        return HttpUtils.postJson(path,postContent);

//        URL url = null;
//        try {
//            url = new URL(path);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setConnectTimeout(10000);
//            httpURLConnection.setReadTimeout(10000);
//
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setDoInput(true);
//            httpURLConnection.setRequestProperty("Charset", "UTF-8");
//            httpURLConnection.setRequestProperty("Content-Type", "application/json");
//
//            httpURLConnection.connect();
//            OutputStream os = httpURLConnection.getOutputStream();
//            os.write(postContent.getBytes("UTF-8"));
//            os.flush();
//
//            StringBuilder sb = new StringBuilder();
//            int httpRspCode = httpURLConnection.getResponseCode();
//            if (httpRspCode == HttpURLConnection.HTTP_OK) {
//
//                BufferedReader br = new BufferedReader(new InputStreamReader(
//                        httpURLConnection.getInputStream(), "utf-8"));
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    sb.append(line);
//                }
//                br.close();
//                return sb.toString();
//
//            }
//
//        } catch (Exception e) {
//            log.error("", e);
//        }
//        return null;
    }
}
