package me.test

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.View
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

@SpringBootApplication(scanBasePackages = ["me.test"])
//@EnableAutoConfiguration // 该注解 @SpringBootApplication 已经启用了
class MyApp {

    // TODO XML
    // TODO JSON
    // TODO 404 错误
    // TODO filters
    // TODO interceptor

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyApp.class, args);
    }

    /** 自定义html格式的错误画面 */
    @Bean(name = ["error", "error/500.json"])
    View error(ObjectMapper objectMapper) {
        return new MappingJackson2JsonView(objectMapper)
    }

    @Bean
    WebMvcConfigurerAdapter myWebMvcConfigurerAdapter() {
        return new WebMvcConfigurerAdapter() {

            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

                // 不建议使用，当出错时，错误处理可能会打破该期待。
                // 因为 ERROR dispatch 时， 会到 URL "/error", 而没有后缀信息
                configurer.favorPathExtension(true)

                configurer.favorParameter(true)
            }
        }
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
