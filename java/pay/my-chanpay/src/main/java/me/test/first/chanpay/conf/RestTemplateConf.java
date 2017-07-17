package me.test.first.chanpay.conf;

import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;
import org.springframework.http.client.*;
import org.springframework.web.client.*;

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
