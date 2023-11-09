package me.test.first.spring.boot.test;

import io.netty.channel.ChannelOption;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 * @author dangqian.zll
 * @date 2020-01-16
 */
public class WebClientTest {

    @Test
    public void x() {

        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000));

        WebClient client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(builder ->
                        builder.codecs(codecs ->
                                codecs.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)
                        )
                )
                .build();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>(4);
        formData.set("a", "aa");
        formData.set("b", "bb");

        Mono<String> result = client
                .post()
                .uri("/persons/{id}", 1)
                .bodyValue(formData)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new RuntimeException("4xxError")))
                .bodyToMono(String.class);
    }
}
