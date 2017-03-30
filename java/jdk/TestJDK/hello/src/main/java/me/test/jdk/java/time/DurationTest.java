package me.test.jdk.java.time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Duration Test
 */
public class DurationTest {


    public static void main(String[] args) throws InterruptedException {

        test01();
        test02();
        test03();
        addDuration();
    }

    static void test01() {

        System.out.println("============================= test01");

        // 打印时间段
        long start = System.currentTimeMillis();
        Duration duration = Duration.ZERO.plusMillis(start);
        System.out.println("duration        : " + duration);
        System.out.println("millis          : " + duration.toMillis());
    }


    static void test02() {

        System.out.println("============================= test02");

        LocalDateTime ldt1 = LocalDateTime.of(2000, 1, 2, 11, 22, 33, 444 * 1000 * 1000);
        LocalDateTime ldt2 = LocalDateTime.of(2000, 2, 2, 11, 22, 33, 444 * 1000 * 1000);
        Duration duration = Duration.between(ldt1, ldt2);
        System.out.println("duration        : " + duration);
        System.out.println("minutes         : " + duration.toMinutes());
    }


    static void test03() {

        System.out.println("============================= test03");

        Duration duration = Duration.ofSeconds(100);
        System.out.println("duration        : " + duration);
        System.out.println("minutes         : " + duration.toMinutes());
    }

    static void addDuration() {

        System.out.println("============================= addDuration");
        LocalDateTime ldt = LocalDateTime.of(2000, 1, 2, 11, 22, 33, 444 * 1000 * 1000);
        Instant startInstant = ldt.atZone(ZoneId.systemDefault()).toInstant();
        System.out.println("startInstant    : " + startInstant);

        Duration duration = Duration.ofSeconds(100);
        System.out.println("duration        : " + duration);

        Instant endInstant = startInstant.plus(duration);
        System.out.println("endInstant      : " + endInstant);
    }

}
