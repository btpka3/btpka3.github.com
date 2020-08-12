package me.test.jdk.java.time;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 */
public class ZonedDateTimeTest {


    // ZonedDateTime <- Instant <- Date

    @Test
    public void fromDate() {
        System.out.println("============================= fromDate");


        Date date = new Date();
        System.out.println("Date          : " + date + ", time=" + date.getTime() + ", Instant=" + Instant.now(Clock.systemDefaultZone()).toEpochMilli());
        Instant instant = date.toInstant();
        System.out.println("Instant       : " + instant + ",  " + instant.toEpochMilli());
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        System.out.println("LocalDateTime : " + zdt + ", time=");
    }

    // ZonedDateTime -> Instant -> Date
    @Test
    public void toDate() {
        System.out.println("============================= toDate");
        ZonedDateTime zdt = ZonedDateTime.now();
        System.out.println("ZonedDateTime : " + zdt);
        Instant instant = zdt.toInstant();
        System.out.println("Instant       : " + instant);
        Date date = Date.from(instant);
        System.out.println("Date          : " + date);
    }

    @Test
    public void format() {
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

    @Test
    public void plus() {
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

    @Test
    public void zone() {
        System.out.println("============================= zone");
        ZonedDateTime currentDate = ZonedDateTime.now(ZoneOffset.UTC);

        System.out.println(currentDate);
        System.out.println(currentDate.toInstant().toEpochMilli());
        System.out.println(System.currentTimeMillis());
        System.out.println(ZoneId.systemDefault());

    }

    @Test
    public void zone02() {
        System.out.println("============================= zone02");
        ZonedDateTime t0 = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime t8 = t0.withZoneSameInstant(ZoneId.of("+08:00"));
        ZonedDateTime t80 = t8.minusSeconds(t8.getOffset().getTotalSeconds());

        System.out.println("t0 = " + t0 + ", zone.totallSeconds = " + t0.getOffset().getTotalSeconds());
        System.out.println("t8 = " + t8 + ", zone.totallSeconds = " + t8.getOffset().getTotalSeconds());

        Date d0 = Date.from(t0.toInstant());
        Date d8 = Date.from(t8.toInstant());
        Date d80 = Date.from(t80.toInstant());
        System.out.println("d0 = " + d0);
        System.out.println("d8 = " + d8);
        System.out.println("d80 = " + d80);

    }

    protected Date toGmt0(ZonedDateTime time) {
        ZonedDateTime gmt0 = time.minusSeconds(time.getOffset().getTotalSeconds());
        return Date.from(gmt0.toInstant());
    }

@Test
public void test() {

    ZonedDateTime now = ZonedDateTime.now();
    Date dateAtSystemZone = Date.from(now.toInstant());
    Date dateAtGmt0 = toGmt0(now);

    SimpleDateFormat sdfWithoutZone = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    SimpleDateFormat sdfWithZoneGmt0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ITALIAN);
    sdfWithZoneGmt0.setTimeZone(TimeZone.getTimeZone("GMT"));

    System.out.println(""
            + "\ndateAtSystemZone          = " + dateAtSystemZone
            + "\ndateAtGmt0                = " + dateAtGmt0
            + "\ndiffInMillis              = " + (dateAtSystemZone.getTime() - dateAtGmt0.getTime())
            + "\n"
            + "\ndateWithSystemZone.format = " + sdfWithoutZone.format(dateAtSystemZone)
            + "\ndateAtGmt0.format         = " + sdfWithoutZone.format(dateAtGmt0)
            + "\n"
            + "\ndateFormatWithGmt0        = " + sdfWithZoneGmt0.format(dateAtSystemZone)
    );
    }


}
