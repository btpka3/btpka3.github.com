package com.github.btpka3.first.spring.boot3;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;

/**
 * @author dangqian.zll
 * @date 2024/4/23
 * @see RestTemplateTest
 * @see RestClientTest
 */
public class WebClientTest {
    WebClient webClient;

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

    void get01() {
        URI uri = UriComponentsBuilder.fromPath("/pandora/ls")
                .scheme("http")
                .host("127.0.0.1")
                .port(12201)
                .build()
                .toUri();

        String body = webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(1000))
                .block();

    }

    void post02() {
        URI uri = null;
        String body = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(1000))
                .block();

    }
}
