package me.test.first.spring.boot.test;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.JvmMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author dangqian.zll
 * @date 2019-09-28
 * @see <a href="https://micrometer.io/docs/concepts#_meters">micrometer</a>
 * @see MetricsAutoConfiguration
 * @see JvmMetricsAutoConfiguration
 */
@SpringBootTest(
        classes = MetricsTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableAutoConfiguration
@TestPropertySource
@Slf4j
public class MetricsTest {
    @Configuration
    public static class Conf {

        @Bean
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

        @Bean
        MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
            return registry -> registry.config().commonTags("region", "us-east-1");
        }
    }

    @Autowired
    MeterRegistry registry;

    @Test
    public void test00() {
        log.info("hello");
        System.out.println("------------hello");
    }

    @Test
    public void test01() throws InterruptedException {
        AtomicLong n = registry.gauge("numberGauge", new AtomicLong(0));
        for (int i = 0; i < 100; i++) {
            long l = System.currentTimeMillis();
            n.set(l);
            System.out.println("------ " + l);
            Thread.sleep(1000);
        }
    }


    public void counter01() {

        MeterRegistry registry = null;
        Counter counter = registry.counter("http.requests", "uri", "/api/users");
        counter.increment();
    }


    @Timed(extraTags = {"region", "us-east-1"})
    public void timer01() {

        MeterRegistry registry = null;
        Timer timer = registry.timer("xx", "aaa");
        timer.record(() -> System.out.println(11));


        Timer.builder("my.timer")
                .publishPercentiles(0.5, 0.95) // median and 95th percentile
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10));

    }

    @Test
    public void gauge01() throws NoSuchAlgorithmException {

        MeterRegistry registry = null;

        List<String> list = registry.gauge("listGauge", Collections.emptyList(), new ArrayList<>(), List::size);

        List<String> list2 = registry.gaugeCollectionSize("listSize2", Tags.empty(), new ArrayList<>());

        Map<String, Integer> map = registry.gaugeMapSize("mapGauge", Tags.empty(), new HashMap<>());

        AtomicInteger n = registry.gauge("numberGauge", new AtomicInteger(0));
        n.set(1);
    }

    public void timer0x1() {

        MeterRegistry registry = null;

        DistributionSummary summary0 = DistributionSummary
                .builder("response.size")
                .description("a description of what this summary does") // optional
                .baseUnit("bytes") // optional (1)
                .tags("region", "test") // optional
                .scale(100) // optional (2)
                .register(registry);

        DistributionSummary summary1 = registry.summary("response.size");
    }
}

