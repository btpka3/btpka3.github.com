package com.github.btpka3.first.spring.data.jpa.conf;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
@Configuration
public class RestTemplateConf {
    @Bean
    public RestTemplateCustomizer myRestTemplateCustomizer() {
        return new RestTemplateCustomizer() {
            @Override
            public void customize(RestTemplate restTemplate) {
                HttpClient httpClient = HttpClientBuilder.create().build();

                // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
                // 以方便调试 RestTemplate 请求头、响应头
                ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient);
                restTemplate.setRequestFactory(fac);

            }

        };
    }

}
