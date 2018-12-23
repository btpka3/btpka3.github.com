package me.test.jdk.java.util.stream;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

/**
 * @see <a href="https://dzone.com/articles/think-twice-using-java-8">Think Twice Before Using Java 8 Parallel Streams</a>
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

    public static void main(String[] args) {

        IntStream.range(0, 10)
                .parallel()
                .forEach(i -> {
                    log("data", i);

                });
    }
}
