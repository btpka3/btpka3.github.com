package me.test.jdk.java.time;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/1/3
 */
public class LocalDateTimeTest {

    @Test
    public void test01() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minus(1, DAYS).truncatedTo(DAYS);
        System.out.println(yesterday);

        Instant yesterdayInstant = yesterday.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(yesterdayInstant);
        System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
        System.out.println("============================= test01");
    }
}
