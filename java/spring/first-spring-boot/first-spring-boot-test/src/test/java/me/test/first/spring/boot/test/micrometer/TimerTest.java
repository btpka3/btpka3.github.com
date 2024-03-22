package me.test.first.spring.boot.test.micrometer;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micrometer.prometheus.PrometheusRenameFilter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.test.first.spring.boot.test.MyApp;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import reactor.function.TupleUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author dangqian.zll
 * @date 2020/11/20
 */
@Slf4j
@SpringBootTest(
        classes = {TimerTest.Conf.class},
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
public class TimerTest {

    @SpringBootApplication
    public static class Conf {
        public static void main(String[] args) {
            SpringApplication.run(MyApp.class, args);
        }
    }

    @SneakyThrows
    @Test
    public void timer01() {
        MeterRegistry registry = new SimpleMeterRegistry();
        Timer timer = Timer
                .builder("my.timer")
                .description("a description of what this timer does") // optional
                .tags("region", "test") // optional
                .register(registry);

        Runnable targetRunnable = () -> {
            try {
                Thread.sleep(RandomUtils.nextInt(100, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable r = timer.wrap(targetRunnable);
        r.run();

        System.out.println("total 1 : " + timer.totalTime(TimeUnit.MICROSECONDS));

        // 数值叠加了
        r.run();
        System.out.println("total 2 : " + timer.totalTime(TimeUnit.MICROSECONDS));

        new Thread(r).start();
        Thread.sleep(60 * 60 * 1000);
    }

    public void x() {
        BiConsumer<String, String> consumer = (a, b) -> System.out.println(a + b);
        TupleUtils.consumer(consumer);
    }


    @SneakyThrows
//    @Test
    public void prometheus() {
        PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        prometheusRegistry.config().meterFilter(new PrometheusRenameFilter());
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/prometheus", httpExchange -> {
                String response = prometheusRegistry.scrape();
                httpExchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            });

            new Thread(server::start).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
