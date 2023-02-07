package com.cc.oem.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 * User: liutao
 * Date: 2018-05-24
 * Time: 13:56
 */
@Configurable
@ComponentScan
public class RestConfig {


    @Bean("baseTemplate")
    public RestTemplate customRestTemplate(){
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(3000);
        httpRequestFactory.setConnectTimeout(3000);
        httpRequestFactory.setReadTimeout(3000);
        return new RestTemplate(httpRequestFactory);
    }

    @Bean("erpTemplate")
    public RestTemplate customErpRestTemplate(){
        ConnectionPool pool = new ConnectionPool(5, 5L, TimeUnit.MINUTES);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3000,TimeUnit.MILLISECONDS)
                .readTimeout(3000,TimeUnit.MILLISECONDS)
                .writeTimeout(3000,TimeUnit.MILLISECONDS)
                .connectionPool(pool)
                .build();
        OkHttp3ClientHttpRequestFactory httpRequestFactory = new OkHttp3ClientHttpRequestFactory(client);
        return new RestTemplate(httpRequestFactory);
    }

    @Bean("wsTemplate")
    public RestTemplate customWsRestTemplate(){
        ConnectionPool pool = new ConnectionPool(5, 5L, TimeUnit.MINUTES);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3000,TimeUnit.MILLISECONDS)
                .readTimeout(3000,TimeUnit.MILLISECONDS)
                .writeTimeout(3000,TimeUnit.MILLISECONDS)
                .connectionPool(pool)
                .build();
        OkHttp3ClientHttpRequestFactory httpRequestFactory = new OkHttp3ClientHttpRequestFactory(client);
        return new RestTemplate(httpRequestFactory);
    }
}
