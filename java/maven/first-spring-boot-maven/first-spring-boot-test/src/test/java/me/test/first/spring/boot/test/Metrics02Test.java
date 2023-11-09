package me.test.first.spring.boot.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author dangqian.zll
 * @date 2020-11-11
 * @see <a href="https://micrometer.io/docs/concepts#_meters">micrometer</a>
 * @see MetricsAutoConfiguration
 * @see JvmMetricsAutoConfiguration
 */
@SpringBootTest(
        classes = Metrics02Test.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableAutoConfiguration
@TestPropertySource
@Slf4j
public class Metrics02Test {
    @Configuration
    public static class Conf {

        @Bean
        ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        LoggingMeterRegistry loggingMeterRegistry(
                ObjectMapper objectMapper
        ) {
            LoggingRegistryConfig config = new LoggingRegistryConfig() {
                @Override
                public Duration step() {
                    return Duration.ofSeconds(5);
                }

                @Override
                public String get(String key) {
                    return null;
                }
            };
            return LoggingMeterRegistry.builder(config)
                    .clock(Clock.SYSTEM)
                    .meterIdPrinter(meter -> {
                        try {
                            return objectMapper.writeValueAsString(meter);
                        } catch (JsonProcessingException e) {
                            return meter.toString();
                        }
                    })
                    .build();
        }

        @Bean
        MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
            return registry -> registry.config()
                    .commonTags("region", "us-east-1")
                    .meterFilter(MeterFilter.deny(id -> !Objects.equals("numberGauge", id.getName())));
        }
    }

    @Autowired
    MeterRegistry registry;

    /**
     * 模拟一秒更新一次的 gauge 类型监控。
     *
     * @throws InterruptedException
     */
    @Test
    public void test01() throws InterruptedException {
        AtomicLong n = registry.gauge("numberGauge", new AtomicLong(0));
        for (int i = 0; i < 16; i++) {
            long l = System.currentTimeMillis();
            n.set(l);
            Thread.sleep(1000);
        }
    }
}

