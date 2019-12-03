package me.test.jdk.java.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 */
public class ZonedDateTimeTest {


    public static void main(String[] args) {

//        fromDate();
//        toDate();
//        format();
//        plus();
        zone();
    }


    // ZonedDateTime <- Instant <- Date
    static void fromDate() {
        System.out.println("============================= fromDate");


        Date date = new Date();
        System.out.println("Date          : " + date + ", time=" + date.getTime() + ", Instant=" + Instant.now(Clock.systemDefaultZone()).toEpochMilli());
        Instant instant = date.toInstant();
        System.out.println("Instant       : " + instant + ",  " + instant.toEpochMilli());
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        System.out.println("LocalDateTime : " + zdt + ", time=");
    }

    // ZonedDateTime -> Instant -> Date
    static void toDate() {
        System.out.println("============================= toDate");
        ZonedDateTime zdt = ZonedDateTime.now();
        System.out.println("ZonedDateTime : " + zdt);
        Instant instant = zdt.toInstant();
        System.out.println("Instant       : " + instant);
        Date date = Date.from(instant);
        System.out.println("Date          : " + date);
    }

    static void format() {
        System.out.println("============================= format");
        ZonedDateTime zdt = ZonedDateTime.now();
        System.out.println("ZonedDateTime : " + zdt);
        String text = zdt.format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("formated str  : " + text);
        System.out.println("formated str1  : " + zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));
        ZonedDateTime parsedDateZdt = ZonedDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("parsed        : " + parsedDateZdt);
        //System.out.println("parsed1        : " + ZonedDateTime.parse("2018-10-22 17-10-36", DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));


    }


    static void plus() {
        System.out.println("============================= plus");
        ZoneId.systemDefault();
        ZonedDateTime zdt = ZonedDateTime.of(2000, 1, 2, 11, 22, 33, 444 * 1000 * 1000, ZoneId.of("Asia/Shanghai"));
        ZonedDateTime newZdt = zdt
                .minusYears(1)
                .plusMonths(1)
                .plusDays(1)
                .plusHours(1)
                .plusMinutes(1)
                .plusSeconds(1)
                .plusNanos(1 * 1000 * 1000)
                .withZoneSameInstant(ZoneOffset.UTC);

        System.out.println("ZonedDateTime     : " + zdt);
        System.out.println("new ZonedDateTime : " + newZdt);
    }


      static void zone() {
        System.out.println("============================= zone");
        ZonedDateTime currentDate = ZonedDateTime.now(ZoneOffset.UTC);

        System.out.println(currentDate);
        System.out.println(currentDate.toInstant().toEpochMilli());
        System.out.println(System.currentTimeMillis());
        System.out.println(ZoneId.systemDefault());
    }



}
