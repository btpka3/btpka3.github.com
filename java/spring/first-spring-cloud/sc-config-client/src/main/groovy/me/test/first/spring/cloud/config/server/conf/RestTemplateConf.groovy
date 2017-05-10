package me.test.first.spring.cloud.config.server.conf

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.boot.autoconfigure.web.HttpMessageConverters
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate

import java.nio.charset.Charset

@Configuration
class RestTemplateConf {

    // ========================================================================== 内网

    @Bean
    @CompileStatic(TypeCheckingMode.SKIP)
    HttpMessageConverters httpMessageConverters() {

        // String 类型 ： ISO-8859-1 -> UTF-8
        return new HttpMessageConverters(true, [
                new StringHttpMessageConverter(Charset.forName("UTF-8"))
        ])
    }

    @Bean
    ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpClient httpClient = HttpClientBuilder.create()
                .build();

        // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
        // 以方便调试 RestTemplate 请求头、响应头
        ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient)
        return fac
    }

    /**
     * 方便打印日志
     */
    @Bean
    RestTemplateCustomizer myRestTemplateCustomizer() {
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

    /**
     * 访问内网的 RestTemplate
     * @param restTemplateBuilder
     * @return
     */
    @Bean
    @Scope("prototype")
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build()
        return restTemplate;
    }

}
