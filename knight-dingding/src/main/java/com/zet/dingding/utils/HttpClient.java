package com.zet.dingding.utils;


import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;


import org.postgresql.ssl.NonValidatingFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import javax.annotation.Resource;
import javax.net.ssl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class HttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private static volatile HttpClient httpClient;

    @Resource
    private  RestTemplateLog restTemplateLog;

    private static OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestProxyFactory;

    private static BufferingClientHttpRequestFactory bufferingClientHttpRequestProxyFactory;

    private static final int CONNECT_TIMEOUT = 60000;

    private static final int READ_TIMEOUT = 60000;

    private static final int WRITE_TIMEOUT = 60000;

    private static final int MAX_IDLE_CONNECTIONS = 200;

    private static final long KEEP_ALIVE_DURATION = 300L;

    private static final int MESHER_PROXY_PORT = 30101;

    public static HttpClient getInstance() {
        if (httpClient == null)
            synchronized (HttpClient.class) {
                if (httpClient == null)
                    httpClient = new HttpClient();
                httpClient.restTemplateLog=new RestTemplateLog();
            }
        return httpClient;
    }

    public RestTemplate createRestTemplate() throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(restTemplateLog);
        restTemplate.setInterceptors(interceptors);
        if (okHttp3ClientHttpRequestProxyFactory == null )
            createOkHttpClient();
        restTemplate.setRequestFactory((ClientHttpRequestFactory)bufferingClientHttpRequestProxyFactory);
        return restTemplate;
    }

    private void createOkHttpClient() throws Exception {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.hostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        builder.connectTimeout(60000L, TimeUnit.SECONDS);
        builder.readTimeout(60000L, TimeUnit.SECONDS);
        builder.writeTimeout(60000L, TimeUnit.SECONDS);
        builder.connectionPool(pool());

        builder.sslSocketFactory(new NonValidatingFactory(""), new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        );

        okHttp3ClientHttpRequestProxyFactory = new OkHttp3ClientHttpRequestFactory(builder.build());
        bufferingClientHttpRequestProxyFactory = new BufferingClientHttpRequestFactory(okHttp3ClientHttpRequestProxyFactory);
    }

    public ConnectionPool pool() {
        return new ConnectionPool(200, 300L, TimeUnit.SECONDS);
    }
}