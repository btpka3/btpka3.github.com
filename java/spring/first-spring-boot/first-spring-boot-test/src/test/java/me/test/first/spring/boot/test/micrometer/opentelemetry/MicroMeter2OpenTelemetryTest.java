package me.test.first.spring.boot.test.micrometer.opentelemetry;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.*;
import io.micrometer.registry.otlp.OtlpConfig;
import io.micrometer.registry.otlp.OtlpMeterRegistry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
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
 * @see <a href="https://docs.micrometer.io/micrometer/reference/implementations/otlp.html">Micrometer OTLP</a>
 */
@SpringBootTest(
        classes = MicroMeter2OpenTelemetryTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableAutoConfiguration
@Slf4j
public class MicroMeter2OpenTelemetryTest {

    @SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class
    })
    public static class Conf {
        public static void main(String[] args) {
            SpringApplication.run(Conf.class, args);
        }

        @Bean
        OtlpMeterRegistry openTelemetryMeterRegistry() {

            Map<String, String> configMap = new HashMap<>(32);
            configMap.put("otlp.enabled", "true");
            configMap.put("otlp.url", "http://localhost:4318/v1/metrics");
            configMap.put("otlp.aggregationTemporality", "cumulative");
            configMap.put("otlp.step", "5s");
            configMap.put("otlp.headers", "header1=hv1,header2=hv2");
            configMap.put("otlp.resourceAttributes", "attr1=av1,attr2=av2");

//            OtlpConfig otlpConfig = configMap::get;
            OtlpConfig otlpConfig = new OtlpConfig() {
                @Override
                public String get(final String key) {
                    return configMap.get(key);
                }
            };

            return new OtlpMeterRegistry(otlpConfig, Clock.SYSTEM);
        }

        @Bean
        MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
            return registry -> registry.config().commonTags("region", "us-east-1");
        }
    }

    @Autowired
    MeterRegistry registry;

    @SneakyThrows
    @Test
    public void timer02() {

        DistributionSummary distributionSummary = DistributionSummary
                .builder("request.size")
                .description("a description of xxx")
                .baseUnit("bytes")
                .tags("region", "test")
                .scale(1.0)

                // 本地直接计算的 百分比（不可再聚合）
                .publishPercentiles(0.3, 0.5, 0.9)

                // 百分比直方图：远端可 与其他实例，按tag 汇总聚合
                .publishPercentileHistogram()
                .minimumExpectedValue(1.0)
                .maximumExpectedValue(100.0)

                .serviceLevelObjectives(0.95)

                .register(registry);

        distributionSummary.record(21);
        distributionSummary.record(42);
        distributionSummary.record(73);
        distributionSummary.record(74);
        distributionSummary.record(85);
        distributionSummary.record(95);
        distributionSummary.record(96);
        distributionSummary.record(97);
        distributionSummary.record(98);
        distributionSummary.record(99);

        Thread.sleep(10000);

        /*
        示例输出如下：

        */
    }
}

