package com.github.btpka3.first.spring.boot3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/4/23
 */
public class RestClientTest {
    public void x() {
        RestClient defaultClient = RestClient.create();

        RestClient customClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .messageConverters(converters -> converters.add(new MyCustomMessageConverter()))
                .baseUrl("https://example.com")
                .defaultUriVariables(Map.of("variable", "foo"))
                .defaultHeader("My-Header", "Foo")
                .requestInterceptor(new MyClientHttpRequestInterceptor())
                .requestInitializer(new MyClientHttpRequestInitializer())
                .build();

        int id = 42;

        UriComponentsBuilder.fromUriString("https://example.com/orders/{id}")
                .uriVariables(Map.of("id", id))
                .queryParam("k1", "v11","v12")
                .queryParam("k2", Arrays.asList("v21", "v22"))
                .build()
                .toUri()
        ;
        MyPojo myPojo = customClient.get()
                .uri("https://example.com/orders/{id}", id)
                .retrieve()
                .body(MyPojo.class);

        ResponseEntity<MyPojo> entity = customClient.get()
                .uri("https://example.com/orders/{id}", id)
                .retrieve()
                .toEntity(MyPojo.class);
        System.out.println("Response status: " + entity.getStatusCode());
        System.out.println("Response headers: " + entity.getHeaders());
        System.out.println("Contents: " + entity.getBody());
    }

    public static class MyClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            return null;
        }
    }

    public static class MyClientHttpRequestInitializer implements ClientHttpRequestInitializer {
        @Override
        public void initialize(ClientHttpRequest request) {

        }
    }

    public static class MyCustomMessageConverter implements HttpMessageConverter<MyPojo> {

        @Override
        public boolean canRead(Class<?> clazz, MediaType mediaType) {
            return false;
        }

        @Override
        public boolean canWrite(Class<?> clazz, MediaType mediaType) {
            return false;
        }

        @Override
        public List<MediaType> getSupportedMediaTypes() {
            return List.of();
        }

        @Override
        public MyPojo read(Class<? extends MyPojo> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
            return null;
        }

        @Override
        public void write(MyPojo myPojo, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        }
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPojo implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
    }
}
