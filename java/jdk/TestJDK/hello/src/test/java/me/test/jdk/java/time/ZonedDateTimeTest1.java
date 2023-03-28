package me.test.jdk.java.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 */
public class ZonedDateTimeTest1 {


    public static void main(String[] args) throws InterruptedException {
        test1();
    }


    static void test1() {

        String text = "2007-12-03T10:15:30+01:00";
        ZonedDateTime zdt = ZonedDateTime.parse(text);
        System.out.println("ZonedDateTime : " + zdt);
        String str = zdt.format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("formated str  : " + str);

    }


}
