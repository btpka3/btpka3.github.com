package me.test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by zll on 02/09/2017.
 */
public class U {

    public static String now() {
        ZonedDateTime zdt = ZonedDateTime.now();
        return zdt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS z"));
    }

    public static void print(String step, Object data) {
        System.out.printf("[%s][%4d - %30s] - %10s : %s%n",
                now(),
                Thread.currentThread().getId(),
                Thread.currentThread().getName(),
                step,
                data
        );
    }
}
