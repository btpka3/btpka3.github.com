package me.test.first.spring.boot.test;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * @see org.springframework.web.client.RestClient
 * @see RestTemplateTest
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