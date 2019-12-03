package me.test.jdk.java.time;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 */
public class DateTimeFormatterTest {


    @Test
    public void format01() throws ParseException {
        ZonedDateTime zdt = ZonedDateTime.now();
        System.out.println("ZonedDateTime : " + zdt);
        String text = zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println("formated str  : " + text);
        System.out.println("formated str1 : " + zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));
        ZonedDateTime parsedDateZdt0 = ZonedDateTime.parse(text, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println("parsed0       : " + parsedDateZdt0);
        ZonedDateTime parsedDateZdt1 = ZonedDateTime.parse("2019-07-03T07:37:56.197Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println("parsed1       : " + parsedDateZdt1);

        System.out.println("sss           : " + DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        System.out.println("sdf.parse2    : " + df.parse(text));

        System.out.println("sdf.format    : " + df.format(new Date()));
        System.out.println("sdf.parse     : " + df.parse("2019-07-03T07:37:56.197Z"));

    }

}
