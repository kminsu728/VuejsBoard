package com.mskim.demo.base.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

//@Configuration
public class InfluxConfiguration {

    @Value("${services.influxdb.url}")
    private String url;

    @Value("${services.influxdb.token}")
    private String token;

    @Value("${services.influxdb.bucket}")
    private String bucket;

    @Value("${services.influxdb.org}")
    private String org;

    @Value("${services.influxdb.max-connections:10}") // 기본값 10
    private int maxConnections;

    @Value("${services.influxdb.wait-idle-close-ttl:60000}") // 기본값 60초
    private long waitIdleCloseTtl;

    @Value("${services.influxdb.request-timeout:30000}") // 기본값 30초
    private long requestTimeout;

    @Value("${services.influxdb.session-timeout:60000}") // 기본값 60초
    private long sessionTimeout;

    @Bean
    public InfluxDBClient influxDBClient() {
        ConnectionPool connectionPool = new ConnectionPool(maxConnections, waitIdleCloseTtl, TimeUnit.SECONDS);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .readTimeout(requestTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(requestTimeout, TimeUnit.MILLISECONDS)
                .connectTimeout(sessionTimeout, TimeUnit.MILLISECONDS)
                .callTimeout(sessionTimeout, TimeUnit.MILLISECONDS)
                .connectionPool(connectionPool);



        InfluxDBClientOptions options = InfluxDBClientOptions.builder()
                .url(url)
                .authenticateToken(token.toCharArray())
                .org(org)
                .bucket(bucket)
                .okHttpClient(okHttpClient)
                .build();


        return InfluxDBClientFactory.create(options);
    }
}
