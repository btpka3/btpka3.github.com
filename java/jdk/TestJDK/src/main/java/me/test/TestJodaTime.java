package me.test;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Calendar;
import java.util.Locale;

public class TestJodaTime {
    public static void main(String[] args) {

        // JDK Caendar, Date -> Joda DateTime
        System.out.println(new DateTime(Calendar.getInstance()));
        System.out.println(new DateTime(Calendar.getInstance().getTime()));

        System.out.println("*****************");
        DateTime dt = DateTime.now();

        // Joda DateTime -> JDK Caendar, Date
        System.out.println(dt);
        System.out.println(dt.toDate());
        System.out.println(dt.toCalendar(Locale.CHINA));

        // Joda DateTime plus/minus
        DateTime dt1 = dt.plusYears(1).plusDays(1).minusHours(1);
        System.out.println(dt1);

        // Joda DateTime field access
        System.out.println("----------------");
        System.out.println(dt.getMonthOfYear());
        System.out.println(dt.monthOfYear().getAsText());
        System.out.println(dt.monthOfYear().getAsShortText(Locale.FRENCH));

        System.out.println("================");
        // format : ISO
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        String str = fmt.print(dt);
        System.out.println(str);

        // format : pattern
        fmt = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss.SSS Z");
        str = fmt.withLocale(Locale.ENGLISH).print(dt);
        System.out.println(str);

        System.out.println("################");
        // parse : pattern
        DateTime dt2 = fmt.parseDateTime("2000/01/02 03:04:05.006 +0800");
        System.out.println(dt2);
    }

}
