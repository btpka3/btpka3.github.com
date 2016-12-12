package me.test

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@SpringBootApplication(scanBasePackages = ["me.test"])
//@EnableAutoConfiguration // 该注解 @SpringBootApplication 已经启用了
class MyMvcApp {

    // TODO filters
    // TODO interceptor

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyMvcApp.class, args);
    }

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

}
