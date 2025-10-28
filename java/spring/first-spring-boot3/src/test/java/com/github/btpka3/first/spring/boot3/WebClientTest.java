package com.github.btpka3.first.spring.boot3;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author dangqian.zll
 * @date 2024/4/23
 * @see RestTemplateTest
 * @see RestClientTest
 */
public class WebClientTest {
    public void x() {
        String s = WebClient.create("http://localhost:12201")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hsf/online")
                        .queryParam("k", "hsf")
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }
}
