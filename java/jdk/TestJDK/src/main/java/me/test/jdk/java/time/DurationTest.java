package me.test.jdk.java.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Duration Test
 */
public class DurationTest {


    public static void main(String[] args) throws InterruptedException {

        // 格式化时间
        LocalDateTime date = LocalDateTime.now();
        String text = date.format(DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime parsedDate = LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("1.1 - " + date);
        System.out.println("1.2 - " + text);
        System.out.println("1.3 - " + parsedDate);

        // 打印时间段
        long start = System.currentTimeMillis();
        Duration duration = Duration.ZERO.plusMillis(start);
        System.out.println("2.1 - " + duration);
    }
}
