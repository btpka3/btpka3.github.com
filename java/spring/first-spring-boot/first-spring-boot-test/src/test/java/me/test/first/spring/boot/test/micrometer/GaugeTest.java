package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dangqian.zll
 * @date 2024/3/28
 * @see org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusMetricsExportAutoConfiguration
 * @see org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusMetricsExportAutoConfiguration.PrometheusPushGatewayConfiguration
 * @see org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint
 * @see org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusProperties
 */
@Slf4j
@SpringBootTest(
        classes = {
                GaugeTest.Conf.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(properties = {
        "spring.config.location=classpath:me/test/first/spring/boot/test/actuator/ActuatorTest.yaml"
})
public class GaugeTest {

    Gauge gauge;


    @SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class
    })
    public static class Conf {


        @RestController
        @RequestMapping("/")
        public static class DemoController {

            @GetMapping({""})
            public String index() {
                return "hello 1112 : " + System.currentTimeMillis();
            }
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests().anyRequest().permitAll();

//            http.securityMatcher(EndpointRequest.toAnyEndpoint());
//            http.authorizeHttpRequests((requests) -> requests.anyRequest().hasRole("ENDPOINT_ADMIN"));
//            http.httpBasic(withDefaults());
            return http.build();
        }

//        @Bean
//        MeterRegistry registry() {
//            //return new SimpleMeterRegistry();
//            return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
//        }

        @RestController
        @RequestMapping("/example")
        public static class ExampleController {
            private final LinkedList<Integer> list;

            private final MeterRegistry registry;
            private final AtomicInteger atomicInteger;

            public ExampleController(PrometheusMeterRegistry registry) {
                System.out.println("==================111");
                this.registry = registry;
                this.list = registry.gaugeCollectionSize("example.list.size", Tags.of("k1", "v1"), new LinkedList<>());
                this.atomicInteger = registry.gauge("example.gauge2", Tags.of("k2", "v2"), new AtomicInteger(0));
            }

            @GetMapping("gauge/{number}")
            @ResponseBody
            public Integer checkListSize(@PathVariable("number") int number) {

                // case 1: 手动设置值
                atomicInteger.set(number);

                // case 2 : 监测list长度
                if (number % 2 == 0) {
                    // add even numbers to the list
                    list.add(number);
                } else {
                    // remove items from the list for odd numbers
                    try {
                        number = list.removeFirst();
                    } catch (NoSuchElementException nse) {
                        number = 0;
                    }
                }
                return number;
            }


            @GetMapping("")
            public String index() {
                return "hello 1113 : " + System.currentTimeMillis() + " - " + atomicInteger;
            }
        }
    }

    @Test
    @SneakyThrows
    public void xxx() {
        Thread.sleep(60 * 60 * 1000);

    /*
curl -v http://localhost:8080/
curl -v http://localhost:8080/example
curl -v http://localhost:8080/example/gauge/1
curl -v http://localhost:8080/example/gauge/2
curl -v http://localhost:8080/example/gauge/4

curl -s http://localhost:8080/actuator/prometheus  | grep example
# HELP example_gauge2
# TYPE example_gauge2 gauge
example_gauge2{k2="v2",region="cn",} 2.0
# HELP example_list_size
# TYPE example_list_size gauge
example_list_size{k1="v1",region="cn",} 2.0
http_server_requests_seconds_count{exception="None",method="GET",outcome="SUCCESS",region="cn",status="200",uri="/example/gauge/{number}",} 2.0
http_server_requests_seconds_sum{exception="None",method="GET",outcome="SUCCESS",region="cn",status="200",uri="/example/gauge/{number}",} 18.76341266
http_server_requests_seconds_max{exception="None",method="GET",outcome="SUCCESS",region="cn",status="200",uri="/example/gauge/{number}",} 0.0

curl -v http://localhost:8080/example/gauge/6
curl -v http://localhost:8080/example/gauge/5
curl -v http://localhost:8080/example/gauge/7
curl -v http://localhost:8080/q/metrics
     */
    }


    @Test
    @SneakyThrows
    public void testMaxDelay() {
        MeterRegistry registry = null;
        MaxDelayPerSecond obj = new MaxDelayPerSecond();
        registry.gauge(
                "example.gauge2",
                Tags.of("k2", "v2"),
                obj,
                MaxDelayPerSecond::getMaxDelay
        );

    }

    public static class MaxDelayPerSecond {
        long second;

        long maxDelay;

        protected void resetIfNecessary() {
            long s = System.currentTimeMillis() / 1000;
            if (s != second) {
                second = s;
                maxDelay = 0;
            }
        }

        public void record(long delay) {
            resetIfNecessary();
            if (delay > maxDelay) {
                maxDelay = delay;
            }
        }
        public double getMaxDelay() {
            resetIfNecessary();
            return maxDelay;
        }
    }
}
