package me.test.first.spring.boot.test.actuator;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author dangqian.zll
 * @date 2024/3/22
 * @see <a href="https://www.baeldung.com/spring-boot-actuators">Spring Boot Actuator</a>
 * @see org.springframework.boot.actuate.health.HealthIndicator
 */
@Slf4j
@SpringBootTest(
        classes = {
                ActuatorTest.Conf.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(properties = {
        "spring.config.location=classpath:me/test/first/spring/boot/test/actuator/ActuatorTest.yaml"
})
public class ActuatorTest {

    @SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class
    })
    public static class Conf {
        public static void main(String[] args) {
            SpringApplication.run(Conf.class, args);
        }


//        @Bean
//        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//            return http.authorizeExchange()
//                    .pathMatchers("/actuator/**").permitAll()
//                    .anyExchange().authenticated()
//                    .and().build();
//        }

        @RestController("/")
        public static class DemoController {

            @GetMapping({""})
            public String index() {
                return "hello : " + System.currentTimeMillis();
            }

        }

        //        @Component
//        public static class DownstreamServiceHealthIndicator implements ReactiveHealthIndicator {
//
//            @Override
//            public Mono<Health> health() {
//                return checkDownstreamServiceHealth().onErrorResume(
//                        ex -> Mono.just(new Health.Builder().down(ex).build())
//                );
//            }
//
//            private Mono<Health> checkDownstreamServiceHealth() {
//                // we could use WebClient to check health reactively
//                return Mono.just(new Health.Builder().up().build());
//            }
//        }
//
//        @Component
//        @Endpoint(id = "features")
//        public static class FeaturesEndpoint {
//
//            private Map<String, Feature> features = new ConcurrentHashMap<>();
//
//            @ReadOperation
//            public Map<String, Feature> features() {
//                return features;
//            }
//
//            @ReadOperation
//            public Feature feature(@Selector String name) {
//                return features.get(name);
//            }
//
//            @WriteOperation
//            public void configureFeature(@Selector String name, Feature feature) {
//                features.put(name, feature);
//            }
//
//            @DeleteOperation
//            public void deleteFeature(@Selector String name) {
//                features.remove(name);
//            }
//
//            public static class Feature {
//                private Boolean enabled;
//
//                // [...] getters and setters
//            }
//
//        }
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests().anyRequest().permitAll();

//            http.securityMatcher(EndpointRequest.toAnyEndpoint());
//            http.authorizeHttpRequests((requests) -> requests.anyRequest().hasRole("ENDPOINT_ADMIN"));
//            http.httpBasic(withDefaults());
            return http.build();
        }

        @Component
        @Endpoint(id = "my")
//        @WebEndpoint(id = "my")
        public static class MyEndpoint {
            @ReadOperation
            public CustomData getData() {
                System.out.println("====== MyEndpoint #  getData1");
                return new CustomData("zzz", 3);
            }

            @ReadOperation
            public CustomData getData(@Selector(match = Selector.Match.ALL_REMAINING) String... path) {
                System.out.println("====== MyEndpoint #  getData2: " + Arrays.asList(path));
                return new CustomData("test", 5);
            }
        }

        @Data
        @Builder(toBuilder = true)
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CustomData {
            private String name;
            private Integer age;
        }


        /**
         * spring bean 的名字就是 url 路径上的名字。
         */
        @Component("random")
        public static class RandomHealthIndicator implements HealthIndicator {

            @Override
            public Health health() {
                double chance = ThreadLocalRandom.current().nextDouble();
                Health.Builder status = Health.up();
                if (chance > 0.9) {
                    status = Health.down();
                }
                status.withDetail("a", "aaa");
                Health health = status.build();
                System.out.println("====== RandomHealthIndicator : " + health);
                return health;
            }
        }
    }


    @Test
    @SneakyThrows
    public void xxx() {
        Thread.sleep(60 * 60 * 1000);

        // curl -v http://localhost:8080/
        // curl -v http://localhost:8080/actuator | jq
        // curl -v http://localhost:8080/actuator/health | jq
        // curl -v http://localhost:8080/actuator/health/diskSpace | jq
        // curl -v http://localhost:8080/actuator/health/random | jq
        // curl -v http://localhost:8080/actuator/my

        // MockMvc
    }
}
