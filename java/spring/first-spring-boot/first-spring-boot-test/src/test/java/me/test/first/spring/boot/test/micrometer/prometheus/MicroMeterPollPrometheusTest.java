package me.test.first.spring.boot.test.micrometer.prometheus;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.HistogramFlavor;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micrometer.prometheus.PrometheusRenameFilter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.test.first.spring.boot.test.micrometer.TimerTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Duration;

/**
 * 用 micro meter 测试 Prometheus 推送。 比如 适用于 flink 任务。
 *
 * @author dangqian.zll
 * @date 2024/7/31
 *
 * @see <a href="https://docs.micrometer.io/micrometer/reference/implementations/prometheus.html">Micrometer Prometheus</a>
 *
 */
@Slf4j
@SpringBootTest(
        classes = {MicroMeterPollPrometheusTest.Conf.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@EnableAutoConfiguration(
        exclude = {
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        }
)
@ContextConfiguration
@TestPropertySource
public class MicroMeterPollPrometheusTest {


    @SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class
    })
    public static class Conf {
        public static void main(String[] args) {
            SpringApplication.run(MicroMeterPollPrometheusTest.Conf.class, args);
        }


        @Bean
        PrometheusConfig prometheusConfig() {
            return new PrometheusConfigImpl();
        }

//        @Bean
//        PrometheusMeterRegistry prometheusRegistry(PrometheusConfig prometheusConfig) {
//            return new PrometheusMeterRegistry(prometheusConfig);
//        }

        @Bean
        MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
            return registry -> registry.config().commonTags("region", "cn");
        }
    }

    public static class PrometheusConfigImpl implements PrometheusConfig {

        @Override
        public String prefix() {
            return "demo.metrics.export.prometheus";
        }

        @Override
        public String get(String key) {
            return null;
        }

        @Override
        public boolean descriptions() {
            return false;
        }

        @Override
        public HistogramFlavor histogramFlavor() {
            return HistogramFlavor.Prometheus;
        }

        @Override
        public Duration step() {
            return Duration.ofMinutes(1);
        }
    }
//
//
//    @SneakyThrows
////    @Test
//    public void prometheus() {
//        PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
//        prometheusRegistry.config().meterFilter(new PrometheusRenameFilter());
//        try {
//            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
//            server.createContext("/prometheus", httpExchange -> {
//                String response = prometheusRegistry.scrape();
//                httpExchange.sendResponseHeaders(200, response.getBytes().length);
//                try (OutputStream os = httpExchange.getResponseBody()) {
//                    os.write(response.getBytes());
//                }
//            });
//
//            new Thread(server::start).start();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        // curl -v http://127.0.0.1:8080/prometheus
//    }
}
