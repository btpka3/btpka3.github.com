package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * @author dangqian.zll
 * @date 2024/3/22
 * @see <a href="https://www.baeldung.com/spring-boot-actuators">Spring Boot Actuator</a>
 * @see org.springframework.boot.actuate.health.HealthIndicator
 */
@Slf4j
@SpringBootTest(
        classes = {
                BaseMetricsTest.Conf.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(properties = {
        "spring.config.location=classpath:me/test/first/spring/boot/test/actuator/ActuatorTest.yaml"
})
public abstract class BaseMetricsTest {

    @SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class
    })
    public static class Conf {
        public static void main(String[] args) {
            SpringApplication.run(Conf.class, args);
        }

        @RestController("/")
        public static class DemoController {
            @GetMapping({""})
            public String index() {
                return "hello : " + System.currentTimeMillis();
            }
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests().anyRequest().permitAll();
            return http.build();
        }

        @Bean(name = "loggingMeterRegistry")
        LoggingMeterRegistry loggingMeterRegistry() {

            return new LoggingMeterRegistry(new LoggingRegistryConfig() {
                @Override
                public Duration step() {
                    return Duration.ofSeconds(5);
                }

                @Override
                public String get(String key) {
                    return null;
                }
            }, Clock.SYSTEM);
        }
    }

    /*
            curl -v http://localhost:8080/
            curl -v http://localhost:8080/actuator | jq
            curl -v http://localhost:8080/actuator/metrics | jq
            curl -v http://localhost:8080/actuator/metrics/executor.pool.core | jq
         */

    @Test
    @SneakyThrows
    public void xxx() {
        Thread.sleep(60 * 60 * 1000);
    }
}
