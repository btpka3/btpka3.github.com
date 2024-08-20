package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.JvmMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
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

    @SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class
    })
    public static class Conf {
        public static void main(String[] args) {
            SpringApplication.run(MetricsTest.Conf.class, args);
        }

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
        97661 [logging-metrics-publisher] INFO  i.m.c.i.l.LoggingMeterRegistry - request.size{region=test} throughput=2/s mean=78 B max=99 B

         其中 "throughput=2/s" 是因为总共记录了10条记录，loggingMeterRegistry.step()=5s， 所以平均后是每秒2个请求。
        */
    }

    public void testGlobalMetrics() {

        // gauge
        {
            AtomicInteger myInt = Metrics.gauge("xxxGauge", Tags.of("k1", "v2"), new AtomicInteger(0));
        }

        // counter
        {
            Counter featureCounter = Metrics.counter("feature", "region", "test");

        }



    }
}

