package io.github.btpka3.nexus.clean.snapshot.conf;

import org.apache.http.client.*;
import org.apache.http.conn.*;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.*;
import org.apache.http.impl.nio.client.*;
import org.apache.http.impl.nio.conn.*;
import org.apache.http.impl.nio.reactor.*;
import org.apache.http.nio.client.*;
import org.apache.http.nio.conn.*;
import org.apache.http.nio.reactor.*;
import org.springframework.context.annotation.*;
import org.springframework.http.client.*;

@Configuration
public class HttpClientConf {


    @Bean(destroyMethod = "close")
    PoolingHttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setValidateAfterInactivity(100);
        connectionManager.setMaxTotal(100);

        return connectionManager;
    }

    @Bean
    HttpClient httpClient(
            HttpClientConnectionManager httpClientConnectionManager
    ) {
        return HttpClientBuilder.create()
                .setConnectionManager(httpClientConnectionManager)
                .build();
    }

    @Bean
    ClientHttpRequestFactory clientHttpRequestFactory(
            HttpClient httpClient
    ) {
        // HttpComponentsClientHttpRequestFactory 与日志级别 "logging.level.org.apache.http: DEBUG" 配合
        // 以方便调试 RestTemplate 请求头、响应头
        ClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient);
        return fac;
    }


    // --------------- async

    @Bean(destroyMethod = "shutdown")
    PoolingNHttpClientConnectionManager nHttpClientConnectionManager() throws IOReactorException {
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor());
        return connManager;
    }

    @Bean
    HttpAsyncClient httpAsyncClient(
            NHttpClientConnectionManager nHttpClientConnectionManager
    ) {
        return HttpAsyncClientBuilder.create()
                .setConnectionManager(nHttpClientConnectionManager)
                .build();
    }

    @Bean
    AsyncClientHttpRequestFactory asyncClientHttpRequestFactory(HttpAsyncClient httpAsyncClient) {
        AsyncClientHttpRequestFactory fac = new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
        return fac;
    }

}
