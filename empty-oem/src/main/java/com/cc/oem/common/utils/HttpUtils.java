package com.cc.oem.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.Map;

public class HttpUtils {

    // 设置请求超时10秒钟
    private static final int REQUEST_TIMEOUT = 5 * 1000;

    // 连接超时时间
    private static final int CONNECT_TIMEOUT = 5 * 1000;

    // 数据传输超时
    private static final int SO_TIMEOUT = 10 * 1000;

    private static final String ENCODING = "UTF-8";

    /**
     * 务必单例
     */
    private static CloseableHttpClient client;

    static {
        RequestConfig requestConfig = RequestConfig.custom()
                                                   .setConnectTimeout(CONNECT_TIMEOUT)
                                                   .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                                                   .setSocketTimeout(SO_TIMEOUT)
                                                   .build();
        client = HttpClients.custom().setDefaultRequestConfig(requestConfig).setMaxConnTotal(50).build();
    }

    private static final Logger log  = LoggerFactory.getLogger(HttpUtils.class);

    public static String get(String url, Map<String, String> paramsMap) throws Exception {
        return send(RequestBuilder.get(url), paramsMap);
    }


    public static String post(String url, Map<String, String> paramsMap) throws Exception {
        return send(RequestBuilder.post(url), paramsMap);
    }

    /**
     * @param url 请求地址
     * @param file post提交的文件
     * @param params 附带的文本参数
     */
    public static String postFile(String url, MultipartFile file, String fileParamName,Map<String, Object> params) throws Exception {
        String responseText = "";
        if (file != null) {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            String fileName = file.getOriginalFilename();
            builder.addBinaryBody(fileParamName, file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);
            //决中文乱码
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if(entry.getValue() == null){
                    continue;
                }
                // 类似浏览器表单提交，对应input的name和value
                builder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            CloseableHttpResponse response  = null;
            try {
                response = client.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity responseEntity = response.getEntity();
                    if (responseEntity != null) {
                        responseText = EntityUtils.toString(responseEntity, ENCODING);
                    }
                }
            } catch (Exception e) {
                log.error("httpPost报错", e);
                throw e;
            } finally {
                try {
                    if (response!=null){
                        response.close();
                    }
                } catch (Exception e) {
                    log.warn("", e);
                }
            }
        }
        return responseText;
    }

    public static String postJson(String url, String jsonBody) throws Exception {
        String responseText = "";
        if (StringUtils.isNotBlank(jsonBody)) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            StringEntity entity = new StringEntity(jsonBody, "utf-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse response  = null;
            try {
                response = client.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity responseEntity = response.getEntity();
                    if (responseEntity != null) {
                        responseText = EntityUtils.toString(responseEntity, ENCODING);
                    }
                }
            } catch (Exception e) {
                log.error("httpPost报错", e);
                throw e;
            } finally {
                try {
                    if (response!=null){
                        response.close();
                    }
                } catch (Exception e) {
                    log.warn("", e);
                }
            }
        }
        return responseText;
    }

    public static String send(RequestBuilder requestBuilder, Map<String, String> paramsMap) throws Exception {
        requestBuilder.setCharset(Charset.forName(ENCODING));
        String responseText = "";

        if (paramsMap != null) {
            for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                requestBuilder.addParameter(param.getKey(), param.getValue());
            }

            CloseableHttpResponse response = null;
            try {
                response = client.execute(requestBuilder.build());
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        responseText = EntityUtils.toString(entity, ENCODING);
                    }
                }
            } catch (Exception e) {
                log.error("httpPost报错", e);
                throw e;
            }
            finally {
                try {
                    if (response!=null){
                        response.close();
                    }
                } catch (Exception e) {
                    log.warn("", e);
                }
            }
        }
        return responseText;

    }
}
