package me.test.first.spring.boot.test;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dangqian.zll
 * @date 2024/4/23
 */
@SpringBootTest
@Import({
        RestTemplateTest.Conf.class
})
public class RestTemplateTest {

    /**
     * Determines the timeout in milliseconds until a connection is established.
     */
    private static final int CONNECT_TIMEOUT = 3000;

    /**
     * The timeout when requesting a connection from the connection manager.
     */
    private static final int REQUEST_TIMEOUT = 3000;

    /**
     * The timeout for waiting for data
     */
    private static final int SOCKET_TIMEOUT = 1000;

    @Configuration
    public static class Conf {
        public PoolingHttpClientConnectionManager poolingConnectionManager() {
            PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();

            // set a total amount of connections across all HTTP routes
            poolingConnectionManager.setMaxTotal(32);

            // set a maximum amount of connections for each HTTP route in pool
            poolingConnectionManager.setDefaultMaxPerRoute(4);

            return poolingConnectionManager;
        }

        public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
            final int DEFAULT_KEEP_ALIVE_TIME_MILLIS = 20 * 1000;
            return new ConnectionKeepAliveStrategy() {
                @Override
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    HeaderElementIterator it = new BasicHeaderElementIterator
                            (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                    while (it.hasNext()) {
                        HeaderElement he = it.nextElement();
                        String param = he.getName();
                        String value = he.getValue();

                        if (value != null && param.equalsIgnoreCase("timeout")) {
                            return Long.parseLong(value) * 1000;
                        }
                    }
                    return DEFAULT_KEEP_ALIVE_TIME_MILLIS;
                }
            };
        }

        @Bean
        public CloseableHttpClient httpClient() {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .build();
            return HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(poolingConnectionManager())
                    .setKeepAliveStrategy(connectionKeepAliveStrategy())
                    .build();
        }

        @Bean
        public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory(
                HttpClient httpClient
        ) {
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            clientHttpRequestFactory.setHttpClient(httpClient);
            return clientHttpRequestFactory;
        }

        @Bean
        public RestTemplate restTemplate(
                ClientHttpRequestFactory requestFactory
        ) {
            RestTemplate restTemplate = new RestTemplate(requestFactory);

            List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
            converters.stream()
                    .filter(converter -> converter instanceof StringHttpMessageConverter)
                    .collect(Collectors.toList())
                    .forEach(converters::remove);
            converters.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            return restTemplate;
        }
    }

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void test01() {

        Map<String,String> queryParams = new HashMap<>(8);
        queryParams.put("k3", "v31");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(16);
        params.setAll(queryParams);


        int id = 13;
        URI uri = UriComponentsBuilder.fromUriString("https://www.baidu.com/")
                .uriVariables(Map.of("id", id))
                .queryParam("k1", "v11", "v12")
                .queryParam("k2", Arrays.asList("v21", "v22"))
                .queryParams(params)
                .build()
                .toUri();

        String str = restTemplate.getForObject(uri, String.class);
        System.out.println("=======================");
        System.out.println(str);
    }


}
