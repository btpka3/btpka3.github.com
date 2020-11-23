package me.test.first.spring.boot.test;

import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.instrument.distribution.pause.PauseDetector;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import io.micrometer.core.instrument.step.StepDistributionSummary;
import io.micrometer.core.instrument.step.StepMeterRegistry;
import io.micrometer.core.instrument.step.StepRegistryConfig;
import io.micrometer.core.instrument.step.StepTimer;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Comparator;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import static net.logstash.logback.marker.Markers.append;
import static net.logstash.logback.marker.Markers.empty;

/**
 * @author dangqian.zll
 * @date 2019-09-28
 * @see <a href="https://micrometer.io/docs/concepts#_meters">micrometer</a>
 * @see MetricsAutoConfiguration
 * @see JvmMetricsAutoConfiguration
 */
@SpringBootTest(
        properties = {
                //"logback.configurationFile=me/test/first/spring/boot/test/MetricsJsonLoggingTest-logback.xml"
                "logging.config=classpath:me/test/first/spring/boot/test/MetricsJsonLoggingTest-logback.xml"
        },
        classes = MetricsJsonLoggingTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableAutoConfiguration
@TestPropertySource
@Slf4j
public class MetricsJsonLoggingTest {
    @Configuration
    public static class Conf {

        @Bean
        LoggingMeterRegistryEx loggingMeterRegistryEx() {


            StepRegistryConfig config = new LoggingRegistryConfig() {
                @Override
                public Duration step() {
                    return Duration.ofSeconds(5);
                }

                @Override
                public String get(String key) {
                    return null;
                }
            };


            LoggingMeterRegistryEx reg =  new LoggingMeterRegistryEx(
                    config,
                    Clock.SYSTEM,
                    TimeUnit.MILLISECONDS,
                    new NamedThreadFactory("logging-metrics-publisher")
            );
            return reg;
        }

        @Bean
        MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
            return registry -> registry.config().commonTags("region", "us-east-1");
        }
    }

    @Autowired
    MeterRegistry registry;

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


    /**
     * @see LoggingMeterRegistry
     */
    public static class LoggingMeterRegistryEx extends StepMeterRegistry {

        Logger log = LoggerFactory.getLogger("json");
        private StepRegistryConfig config;
        private Clock clock;
        private TimeUnit baseTimeUnit;

        @Setter
        private Consumer<Gauge> gaugeConsumer = this::logGauge;
        @Setter
        private Consumer<Counter> counterConsumer = this::logCounter;
        @Setter
        private Consumer<Timer> timerConsumer = this::logTimer;
        @Setter
        private Consumer<DistributionSummary> summaryConsumer = this::logDistributionSummary;
        @Setter
        private Consumer<LongTaskTimer> longTaskTimerConsumer = this::logDistributionSummary;
        @Setter
        private Consumer<TimeGauge> timeGaugeConsumer = this::logTimeGauge;
        @Setter
        private Consumer<FunctionCounter> functionCounterConsumer = this::logFunctionCounter;
        @Setter
        private Consumer<FunctionTimer> functionTimerConsumer = this::logFunctionTimer;
        @Setter
        private Consumer<Meter> meterConsumer = this::logMeter;

        public LoggingMeterRegistryEx(
                StepRegistryConfig config,
                Clock clock,
                TimeUnit baseTimeUnit,
                ThreadFactory threadFactory
        ) {
            super(config, clock);
            this.config = config;
            this.clock = clock;
            this.baseTimeUnit = baseTimeUnit;
            start(threadFactory);
        }

        protected void appendMarkerWithId(LogstashMarker marker, Meter.Id id) {
            marker.and(append("type", id.getType().name()));
            marker.and(append("name", id.getName()));

            for (Tag tag : id.getTags()) {
                marker.and(append("tag_" + tag.getKey(), tag.getValue()));
            }
        }

        protected void appendMarkerWithMeter(LogstashMarker marker, Meter meter) {
            appendMarkerWithId(marker, meter.getId());

            for (Measurement measurement : meter.measure()) {
                marker.and(append("m_" + measurement.getStatistic().getTagValueRepresentation(), measurement.getValue()));
            }
        }


        protected void logGauge(Gauge meter) {
            LogstashMarker marker = empty();
            appendMarkerWithMeter(marker, meter);
            marker.and(append("value", meter.value()));
            log.info(marker, "aa");
        }

        protected void logCounter(Counter meter) {
            LogstashMarker marker = empty();
            appendMarkerWithMeter(marker, meter);
            marker.and(append("count", meter.count()));
            log.info(marker, null);
        }

        protected void logTimer(Timer meter) {
            LogstashMarker marker = empty();
            appendMarkerWithMeter(marker, meter);
            marker.and(append("count", meter.count()));
            marker.and(append("totalTime", meter.totalTime(getBaseTimeUnit())));
            marker.and(append("mean", meter.mean(getBaseTimeUnit())));
            marker.and(append("max", meter.max(getBaseTimeUnit())));
            log.info(marker, null);
        }

        protected void logDistributionSummary(DistributionSummary meter) {
            LogstashMarker marker = empty();
            appendMarkerWithMeter(marker, meter);
            marker.and(append("count", meter.count()));
            marker.and(append("totalAmount", meter.totalAmount()));
            marker.and(append("mean", meter.mean()));
            marker.and(append("max", meter.max()));
            log.info(marker, null);
        }

        protected void logDistributionSummary(LongTaskTimer meter) {
            LogstashMarker marker = empty();
            appendMarkerWithMeter(marker, meter);
            marker.and(append("duration", meter.duration(getBaseTimeUnit())));
            marker.and(append("active", meter.activeTasks()));
            log.info(marker, null);
        }

        protected void logTimeGauge(TimeGauge meter) {
            logGauge(meter);
        }

        protected void logFunctionCounter(FunctionCounter meter) {
            LogstashMarker marker = empty();
            appendMarkerWithMeter(marker, meter);
            marker.and(append("count", meter.count()));
            log.info(marker, null);
        }

        protected void logFunctionTimer(FunctionTimer meter) {
            LogstashMarker marker = empty();
            appendMarkerWithMeter(marker, meter);
            marker.and(append("count", meter.count()));
            marker.and(append("totalTime", meter.totalTime(getBaseTimeUnit())));
            marker.and(append("mean", meter.mean(getBaseTimeUnit())));
            log.info(marker, null);
        }

        protected void logMeter(Meter meter) {
            LogstashMarker marker = empty();
            appendMarkerWithMeter(marker, meter);
            log.info(marker, null);
        }


        public static <T> void noop(T t) {
        }

        @Override
        protected void publish() {
            if (!config.enabled()) {
                return;
            }
            getMeters().stream()
                    .sorted(Comparator.comparing((Meter m) -> m.getId().getType())
                            .thenComparing((Meter m) -> m.getId().getName())
                    )
                    .forEach(m -> {
                        m.use(
                                gaugeConsumer,
                                counterConsumer,
                                timerConsumer,
                                summaryConsumer,
                                longTaskTimerConsumer,
                                timeGaugeConsumer,
                                functionCounterConsumer,
                                functionTimerConsumer,
                                meterConsumer
                        );
                    });

        }

        @Override
        protected Timer newTimer(Meter.Id id, DistributionStatisticConfig distributionStatisticConfig, PauseDetector pauseDetector) {
            return new StepTimer(id, clock, distributionStatisticConfig, pauseDetector, getBaseTimeUnit(),
                    this.config.step().toMillis(), false);
        }

        @Override
        protected DistributionSummary newDistributionSummary(Meter.Id id, DistributionStatisticConfig distributionStatisticConfig, double scale) {
            return new StepDistributionSummary(id, clock, distributionStatisticConfig, scale,
                    config.step().toMillis(), false);
        }

        @Override
        protected TimeUnit getBaseTimeUnit() {
            return baseTimeUnit;
        }
    }
}

