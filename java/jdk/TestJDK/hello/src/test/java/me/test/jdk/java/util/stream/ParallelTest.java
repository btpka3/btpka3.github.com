package me.test.jdk.java.util.stream;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

/**
 * @see <a href="https://dzone.com/articles/think-twice-using-java-8">Think Twice Before Using Java 8 Parallel Streams</a>
 * @see java.util.concurrent.ForkJoinPool#commonPool()
 * @see java.util.concurrent.ForkJoinTask#invoke()
 * @see java.util.stream.ForEachOps.ForEachTask#compute()
 * @see java.util.concurrent.ForkJoinTask#fork()
 * @see java.util.concurrent.ForkJoinPool#common
 */
public class ParallelTest {

    public static String now() {
        ZonedDateTime zdt = ZonedDateTime.now();
        return zdt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS z"));
    }

    public static void log(String step, Object data) {
        System.out.printf("[%s][%4d - %40s] - %10s : %s%n",
                now(),
                Thread.currentThread().getId(),
                Thread.currentThread().getName(),
                step,
                data
        );

    }

    @Test
    public void test01() {
        log("start", "start");

        // 直接使用 java.util.concurrent.ForkJoinPool.common 字段。
        IntStream.range(0, 10)
                .parallel()
                .forEach(i -> {
                    log("data", i);
                });

        /*
        最后的 forEach 操作，内部会 调用 ForkJoinTask#invoke()
        java.util.stream.ForEachOps.ForEachTask.compute
        java.util.concurrent.ForkJoinTask#fork()
        */
    }
}
