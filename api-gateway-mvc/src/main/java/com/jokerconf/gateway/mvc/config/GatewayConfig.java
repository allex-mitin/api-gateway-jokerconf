package com.jokerconf.gateway.mvc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.util.concurrent.TimeUnit;

/**
 * Configuration of gateway beans
 */
@Configuration
@Slf4j
public class GatewayConfig {

    @Bean
    @ConditionalOnProperty(value = "client-type", havingValue = "APACHE")
    public ClientHttpRequestFactory apacheClientHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    @ConditionalOnProperty(value = "client-type", havingValue = "SIMPLE")
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(600000);
        requestFactory.setReadTimeout(60000);
        return requestFactory;
    }

    private HttpClient httpClient() {
        ConnectionConfig connConfig = ConnectionConfig.custom()
                .setConnectTimeout(600000, TimeUnit.MILLISECONDS)
                .setSocketTimeout(600000, TimeUnit.MILLISECONDS)
                .setValidateAfterInactivity(600000, TimeUnit.MILLISECONDS)
                .build();
        HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connConfig)
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(200)
                .build();
        return HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }


}
