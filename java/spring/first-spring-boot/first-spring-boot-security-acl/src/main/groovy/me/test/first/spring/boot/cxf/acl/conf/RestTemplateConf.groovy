package me.test.first.spring.boot.cxf.acl.conf

import ch.qos.logback.classic.Logger
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

/**
 *
 */
@Configuration
class RestTemplateConf {

    static final Logger log = LoggerFactory.getLogger(RestTemplateConf)

    RestTemplateConf(){
        log.debug("============ RestTemplateConf")
    }

    @Bean
    RestTemplateCustomizer myRestTemplateCustomizer() {
        log.debug("============ RestTemplateConf#myRestTemplateCustomizer()")

        return new RestTemplateCustomizer() {

            @Override
            void customize(RestTemplate restTemplate) {
                HttpClient httpClient = HttpClientBuilder.create()
                        .build();

                // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
                // 以方便调试 RestTemplate 请求头、响应头
                ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient)
                restTemplate.setRequestFactory(fac);

            }
        }
    }
}
