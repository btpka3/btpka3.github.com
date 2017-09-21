package me.test.first.spring.session.conf;

import com.fasterxml.jackson.databind.*;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.*;
import org.springframework.web.client.*;

import java.nio.charset.*;
import java.util.*;

@Configuration
public class RestTemplateConf {

    // ========================================================================== 内网

    @Bean
    public HttpMessageConverters httpMessageConverters(
            ObjectMapper objectMapper
    ) {

        // 启用 Jaxb 注解
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper);

        // 微信部分API返回的content-type 不正确，是 text/plain
        jsonConverter.setSupportedMediaTypes(new ArrayList<>(
                Arrays.asList(
                        MediaType.APPLICATION_JSON,
                        new MediaType("application", "*+json"),
                        MediaType.TEXT_PLAIN
                )
        ));


        return new HttpMessageConverters(true, Arrays.asList(

                // 启用 Jaxb 注解
                jsonConverter,

                // String 类型 ： ISO-8859-1 -> UTF-8
                new StringHttpMessageConverter(Charset.forName("UTF-8"))
        ));
    }


    /**
     * 方便打印日志
     */
    @Bean
    public RestTemplateCustomizer myRestTemplateCustomizer(
            ClientHttpRequestFactory clientHttpRequestFactory
    ) {
        return restTemplate -> {

            // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
            // 以方便调试 RestTemplate 请求头、响应头
            restTemplate.setRequestFactory(clientHttpRequestFactory);

        };
    }

    /**
     * 访问内网的 RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(
            RestTemplateBuilder restTemplateBuilder
    ) {
        return restTemplateBuilder.build();
    }

}
