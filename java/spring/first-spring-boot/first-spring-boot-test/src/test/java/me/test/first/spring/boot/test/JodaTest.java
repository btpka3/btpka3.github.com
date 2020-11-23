package me.test.first.spring.boot.test;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author dangqian.zll
 * @date 2019-09-16
 */
public class JodaTest {

    @Test
    public void testIso8601() {
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        Date now = new Date();
        DateTime dateTime = new DateTime(now);
        System.out.println(now);

        String str = fmt.print(dateTime);
        System.out.println(str);

        DateTime dateTime1 = fmt.parseDateTime(str);
        Date date1 = dateTime1.toDate();
        System.out.println(date1);
    }
}
