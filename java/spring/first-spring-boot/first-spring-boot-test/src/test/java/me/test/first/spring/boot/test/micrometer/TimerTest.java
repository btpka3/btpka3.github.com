package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;
import reactor.function.TupleUtils;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author dangqian.zll
 * @date 2020/11/20
 */
public class TimerTest {

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
                Thread.sleep(
                        100
                );
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
    }

    public void x() {
        BiConsumer<String, String> consumer = (a, b) -> System.out.println(a + b);
        TupleUtils.consumer(consumer);
    }
}
