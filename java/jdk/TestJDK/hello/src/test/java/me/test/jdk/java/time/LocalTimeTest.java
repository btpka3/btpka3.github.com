package me.test.jdk.java.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 */
public class LocalTimeTest {


    public static void main(String[] args) throws InterruptedException {

        fromDate();
        toDate();
        format();
        settingLocalDateTime();
        plus();
        minus();
    }


    // LocalDateTime <- Instant <- Date
    static void fromDate() {
        System.out.println("============================= fromDate");
        Date date = new Date();
        System.out.println("Date          : " + date);
        Instant instant = date.toInstant();
        System.out.println("Instant       : " + instant);
        //LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println("LocalDateTime : " + ldt);
    }

    // LocalDateTime -> Instant -> Date
    static void toDate() {
        System.out.println("============================= toDate");
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("LocalDateTime : " + ldt);
        // Instant instant = ldt.toInstant(ZoneOffset.UTC);
        Instant instant = ldt.atZone(
                // ZoneId.systemDefault()
                ZoneOffset.ofHours(8)
        ).toInstant();
        System.out.println("Instant       : " + instant);
        Date date = Date.from(instant);
        System.out.println("Date          : " + date);
    }

    static void format() {
        System.out.println("============================= format");
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("LocalDateTime : " + ldt);
        String text = ldt.format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("formated str  : " + text);
        LocalDateTime parsedDateLdt = LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("parsed        : " + parsedDateLdt);
        System.out.println("parsed1        : " + LocalDateTime.parse("2018-10-22 17-11-48", DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));
    }

    static void settingLocalDateTime() {
        System.out.println("============================= settingLocalDateTime");
        // 秒，
        // 毫秒 (ms, )
        // 微秒 (μs, microsecond)
        // 纳秒 (ns, nanosecond)
        LocalDateTime ldt = LocalDateTime.of(2000, 1, 2, 11, 22, 33, 444 * 1000 * 1000);
        System.out.println("LocalDateTime : " + ldt);
    }

    static void plus() {
        System.out.println("============================= plus");

        LocalDateTime ldt = LocalDateTime.of(2000, 1, 2, 11, 22, 33, 444 * 1000 * 1000);

        LocalDateTime newLdt = ldt
                .minusYears(1)
                .plusMonths(1)
                .plusDays(1)
                .plusHours(1)
                .plusMinutes(1)
                .plusSeconds(1)
                .plusNanos(1 * 1000 * 1000);

        System.out.println("LocalDateTime     : " + ldt);
        System.out.println("new LocalDateTime : " + newLdt);
    }

     static void minus() {
        System.out.println("============================= minus");

        LocalDateTime ldt = LocalDateTime.of(2019, 6, 21, 02, 24, 58, 444 * 1000 * 1000);

        LocalDateTime newLdt = ldt
                .minusDays(6*30);

        System.out.println("LocalDateTime     : " + ldt);
        System.out.println("new LocalDateTime : " + newLdt);
    }
}
