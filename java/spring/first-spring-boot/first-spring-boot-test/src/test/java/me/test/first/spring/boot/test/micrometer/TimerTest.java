package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.TimeUnit;

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
                .description("a description of what this timer does")
                .tags("region", "test")
                .publishPercentileHistogram()
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
        MeterRegistry registry = null;
        registry.get("xxxTimer")
                .tag("xxx", "xxx")
                .timer()
                .count();
    }
}
