package me.test.first.spring.boot.webflux;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author 当千
 * @date 2018-12-06
 */
@EnableAutoConfiguration
//@Import(WebClientTest.Conf.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebClientTest {

    @Configuration
    public static class Conf {

        @Bean
        String xx() {
            return "x111";
        }
    }

    @Autowired(required = false)
    String xx;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void test() {

        System.out.println("xx = " + xx);
        Assertions.assertTrue(true);

        this.webClient
                .get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .value((bodyStr) -> bodyStr.contains("\"msg\":\"hi~\""));
    }
}
