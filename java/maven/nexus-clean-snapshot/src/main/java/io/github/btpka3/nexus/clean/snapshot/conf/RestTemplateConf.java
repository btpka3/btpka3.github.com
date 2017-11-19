package io.github.btpka3.nexus.clean.snapshot.conf;


import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.http.converter.*;
import org.springframework.util.*;
import org.springframework.util.concurrent.*;
import org.springframework.web.client.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

@Configuration
class RestTemplateConf {

    // ========================================================================== 内网

    @Bean
    HttpMessageConverters httpMessageConverters() {

        // String 类型 ： ISO-8859-1 -> UTF-8
        return new HttpMessageConverters(true, Arrays.asList(
                new StringHttpMessageConverter(StandardCharsets.UTF_8)
        )
        );
    }

//    @Bean
//    ClientHttpRequestFactory clientHttpRequestFactory() {
//        HttpClient httpClient = HttpClientBuilder.create()
//                .build();
//
//        // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
//        // 以方便调试 RestTemplate 请求头、响应头
//        ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient)
//        return fac
//    }

    /**
     * 方便打印日志
     */
    @Bean
    RestTemplateCustomizer myRestTemplateCustomizer(
            ClientHttpRequestFactory clientHttpRequestFactory
    ) {
        return new RestTemplateCustomizer() {

            @Override
            public void customize(RestTemplate restTemplate) {

//                HttpClient httpClient = HttpClientBuilder.create()
//                        .build();

                // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
                // 以方便调试 RestTemplate 请求头、响应头
//                ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient)
                restTemplate.setRequestFactory(clientHttpRequestFactory);

            }
        };
    }


    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate;
    }


    //@Bean
    AsyncRestTemplate asyncRestTemplate(
            AsyncClientHttpRequestFactory requestFactory,

            @Value("${nexus.userName}")
                    String userName,

            @Value("${nexus.password}")
                    String password
    ) {
        AsyncRestTemplate tpl = new AsyncRestTemplate(requestFactory);
        tpl.setInterceptors(Arrays.asList(
                new AsyncClientHttpRequestInterceptor() {

                    @Override
                    public ListenableFuture<ClientHttpResponse> intercept(HttpRequest request, byte[] body, AsyncClientHttpRequestExecution execution) throws IOException {

                        String token = Base64Utils.encodeToString((userName + ":" + password).getBytes(StandardCharsets.UTF_8));
                        request.getHeaders().add("Authorization", "Basic " + token);
                        return execution.executeAsync(request, body);
                    }
                }
        ));

        return tpl;
    }


}
