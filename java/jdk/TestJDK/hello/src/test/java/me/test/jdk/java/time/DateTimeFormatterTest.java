package me.test.jdk.java.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class DateTimeFormatterTest {

    /*
    java
    - java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME
      示例结果: "2024-05-06T15:58:08.081874+08:00"

    bash date command:
    示例 :
    `date +%Y-%m-%dT%H:%M:%S.%N%:z`
    `date +%FT%T.%N%:z`
    `date --iso-8601=ns`    # 示例输出: "2024-05-07T10:09:28,134277988+0800"


    MySql
    - CONVERT_TZ 函数:
      示例SQL: `select CONVERT_TZ('2007-03-11 10:00:00','US/Eastern','-08:01') AS time1`
    - STR_TO_DATE 函数:
      示例SQL: `SELECT STR_TO_DATE('2007-03-11T10:00:00.081874','%Y-%m-%dT%H:%i:%S.%f.%z') AS time1`
    - DATE_FORMAT 函数
      示例SQL: `SELECT DATE_FORMAT(NOW(),'%Y-%m-%dT%H:%i:%S.%f.%z') AS time1`

    阿里云SLS
    - to_iso8601 函数：
      示例SQL: `* | select to_iso8601(current_timestamp) AS ISO8601 limit 1`
      示例结果: "2024-05-06T16:24:00.201+08:00"
    - from_iso8601_timestamp 函数：
      示例SQL: `* | select from_iso8601_timestamp('2024-05-06T15:58:08.081874+08:00') limit 1`

    Postgresql

    阿里云Hologres

    */

    @Test
    public void format01() throws ParseException {
        long millis = System.currentTimeMillis();
        ZonedDateTime zdt = ZonedDateTime.now();
        System.out.println("ZonedDateTime : " + zdt);
        String text = zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println("formated str  : " + text);

        Instant instantNow = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.ofInstant(instantNow, ZoneId.systemDefault());
        System.out.println("formated str0 : " + DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTimeNow));
        System.out.println("formated str1 : " + zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));
        ZonedDateTime parsedDateZdt0 = ZonedDateTime.parse(text, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println("parsed0       : " + parsedDateZdt0);
        ZonedDateTime parsedDateZdt1 =
                ZonedDateTime.parse("2019-07-03T07:37:56.197Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println("parsed1       : " + parsedDateZdt1);

        text = "2024-05-07T09:58:45.139599168+08:00";
        ZonedDateTime parsedDateZdt2 = ZonedDateTime.parse(text, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println("parsed2       : " + parsedDateZdt2);

        System.out.println("sss           : " + DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        System.out.println("sdf.parse2    : " + df.parse(text));

        System.out.println("sdf.format    : " + df.format(new Date()));
        System.out.println("sdf.parse     : " + df.parse("2019-07-03T07:37:56.197Z"));
    }

    @Test
    public void test02() {
        System.out.println("============================= fromDate");
        Date date = new Date();
        System.out.println("Date          : " + date);
        Instant instant = date.toInstant();
        System.out.println("Instant       : " + instant);
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());

        System.out.println("LocalDateTime : " + ldt);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("LocalDateTime : " + dtf.format(ldt));
    }

    @Test
    @SneakyThrows
    public void simpleDateFormat01() {
        String dateStr = "2024-01-02T03:04:05.789+08:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date date = sdf.parse(dateStr);
        assertEquals(1704135845789L, date.getTime());
    }

    @Test
    @SneakyThrows
    public void simpleDateFormat02() {
        String dateStr = "2024-01-02T03:04:05.78+08:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date date = sdf.parse(dateStr);
        assertEquals(1704135845078L, date.getTime());
    }

    public void toIsoTimeStr01() {
        long epochMilli = 1704135845789L;
        Instant instant = Instant.ofEpochMilli(epochMilli);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        String isoTimeStr = zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public void isoTimeStr2ZonedDateTime01() {
        String isoTimeStr = "2024-05-07T09:58:45.139599168+08:00";
        ZonedDateTime parsedDateZdt = ZonedDateTime.parse(isoTimeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public void isoTimeStr2OffsetDateTime01() {
        String isoTimeStr = "2024-05-07T09:58:45.139599168+08:00";
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(isoTimeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public void isoTimeStr2Instant01() {
        String isoTimeStr = "2024-05-07T09:58:45.139599168+08:00";
        ZonedDateTime parsedDateZdt = ZonedDateTime.parse(isoTimeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        Instant instant = parsedDateZdt.toInstant();
    }

    public void isoTimeStr2LocalDateTime01() {
        String isoTimeStr = "2024-05-07T09:58:45.139599168+08:00";
        ZonedDateTime parsedDateZdt = ZonedDateTime.parse(isoTimeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        LocalDateTime localDateTime = parsedDateZdt.toLocalDateTime();
    }

    public void isoTimeStr2Date() {
        String isoTimeStr = "2024-05-07T09:58:45.139599168+08:00";
        ZonedDateTime parsedDateZdt = ZonedDateTime.parse(isoTimeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        Date date = new Date(parsedDateZdt.toInstant().toEpochMilli());
    }

    @SneakyThrows
    public void isoTimeStr2Date2() {
        String dateStr = "2024-01-02T03:04:05.789+08:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date date = sdf.parse(dateStr);
    }

    /**
     * LocalDateTime -> milliseconds
     */
    @Test
    public void localDateTime01() {
        LocalDateTime localDateTime = LocalDateTime.now();
        long milliseconds =
                localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(milliseconds);
    }

    @Test
    public void compareFmt() {
        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String str1 = LocalDateTime.now(ZoneId.systemDefault()).format(dtf);
        System.out.println("str1 = " + str1);

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String str2 = sdf.format(date);
        System.out.println("str2 = " + str2);
    }
}
