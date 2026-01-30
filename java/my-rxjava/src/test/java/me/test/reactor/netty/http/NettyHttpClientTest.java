package me.test.reactor.netty.http;

import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

/**
 * @author dangqian.zll
 * @date 2022/12/27
 */
public class NettyHttpClientTest {

    public void x() {
        HttpClient client = HttpClient.create();

        HttpClientResponse resp = client.get()
                .uri("https://example.com/")
                .response()
                .block();


    }
}
