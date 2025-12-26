package me.test.jdk.java.text;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Locale.CHINESE;
import static java.util.Locale.ENGLISH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MessageFormat01 {


//    private Map<Locale, List<Object>> map = Map.of(
//            Locale.CHINESE, List.of(
//                    List.of("{0}", new Object[]{1234567890.123456}, "1,234,567,890.123"),
//
//            )
//    );

    @Data
    @AllArgsConstructor
    static class Rec {
        String pattern;
        Object[] args;
        /**
         * KEY=目标 locale
         * VALUE=期待的结果
         */
        Map<Locale, String> expected;
    }

    List<Rec> recList = List.of(
            new Rec("{0}", new Object[]{1234567890.123456}, Map.of(Locale.CHINESE, "1,234,567,890.123", Locale.ENGLISH, "1,234,567,890.123"))
    );


    @Test
    public void test() {
//        Locale targetLocale = Locale.CHINESE;
        Locale targetLocale = Locale.ENGLISH;
        Locale.setDefault(targetLocale);
        assertSame(targetLocale, Locale.getDefault(Locale.Category.FORMAT));
        assertSame(targetLocale, Locale.getDefault());

        {
            String msg = MessageFormat.format("{0}", 1234567890.123456);
            assertEquals("1,234,567,890.123", msg);
        }

        {
            String msg = MessageFormat.format("{0,number,percent}", 1.23456);
            assertEquals("123%", msg);
        }
        // \u00A4 : `¤` 表示货币
        {
            String msg = MessageFormat.format("{0,number,currency}", 1234567890.123);
            assertEquals("¤1,234,567,890.12", msg);
        }
        {
            String msg = MessageFormat.format("{0,number,¤#.##}", 1234567890.123);
            assertEquals("¤1234567890.12", msg);
        }
        {
            String msg = MessageFormat.format("{0,number,¤¤#.##}", 1234567890.123);
            assertEquals("XXX1234567890.12", msg);
        }


        // 注意：默认数值还是会有逗号分隔的
        {
            String msg = MessageFormat.format("{0,number,integer}", 1234567890);
            assertEquals("1,234,567,890", msg);
        }
        // 数值，整数（比如数据库中的自增ID）
        {
            String msg = MessageFormat.format("{0,number,#}", 1234567890);
            assertEquals("1234567890", msg);
        }
        // 数值，负数
        {
            String msg = MessageFormat.format("{0,number,#}", -1234567890);
            assertEquals("-1234567890", msg);
        }
        // 参考  java.text.DecimalFormat， 整数部分按照4个数字一组
        {
            String msg = MessageFormat.format("{0,number,#,####.##}", 1234567890.123456);
            assertEquals("12,3456,7890.12", msg);
        }
        // 参考  java.text.DecimalFormat， 整数部分按照3个数字一组
        {
            String msg = MessageFormat.format("{0,number,#,###.##}", 1234567890.1);
            assertEquals("1,234,567,890.1", msg);
        }
        // 参考  java.text.DecimalFormat， 前导零
        {
            String msg = MessageFormat.format("{0,number,0000.00}", 123.1);
            assertEquals("0123.10", msg);
        }

        {
            String msg = MessageFormat.format("{0,number,0000.00}", 12345.1);
            assertEquals("12345.10", msg);
        }

        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0}", date);

            if (CHINESE == targetLocale) {
                assertEquals("2001/1/2 03:04", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("1/2/01, 3:04 AM", msg);
            }

        }

        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,date}", date);
            if (CHINESE == targetLocale) {
                assertEquals("2001年1月2日", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("Jan 2, 2001", msg);
            }
        }
        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,date,short}", date);
            if (CHINESE == targetLocale) {
                assertEquals("2001/1/2", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("1/2/01", msg);
            }
        }
        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,date,medium}", date);
            if (CHINESE == targetLocale) {
                assertEquals("2001年1月2日", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("Jan 2, 2001", msg);
            }
        }
        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,date,long}", date);
            if (CHINESE == targetLocale) {
                assertEquals("2001年1月2日", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("January 2, 2001", msg);
            }
        }
        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,date,full}", date);
            if (CHINESE == targetLocale) {
                assertEquals("2001年1月2日星期二", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("Tuesday, January 2, 2001", msg);
            }
        }
        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,time}", date);
            if (CHINESE == targetLocale) {
                assertEquals("03:04:05", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("3:04:05 AM", msg);
            }
        }
        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,time,short}", date);
            if (CHINESE == targetLocale) {
                assertEquals("03:04", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("3:04 AM", msg);
            }
        }
        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,time,medium}", date);
            if (CHINESE == targetLocale) {
                assertEquals("03:04:05", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("3:04:05 AM", msg);
            }
        }
        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,time,long}", date);
            if (CHINESE == targetLocale) {
                assertEquals("CST 03:04:05", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("3:04:05 AM CST", msg);
            }
        }
        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,time,full}", date);
            if (CHINESE == targetLocale) {
                assertEquals("中国标准时间 03:04:05", msg);
            }
            if (ENGLISH == targetLocale) {
                assertEquals("3:04:05 AM China Standard Time", msg);
            }
        }

        // "2001-01-02 03:04:05.789" == 978375845789
        // 参考 SimpleDateFormat, 注意：最后的一个大S数量多少均不影响结果
        {
            Date date = new Date(978375845789L);
            String msg = MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss.S}", date);
            assertEquals("2001-01-02 03:04:05.789", msg);
        }

        // 不允许出现 "{}"
        {
            assertThrows(IllegalArgumentException.class, () -> {
                MessageFormat.format("{},{0},{1},{2}", "aaa", "bbb", "ccc");
            });
        }

        // 单引号是转义字符
        {
            String msg = MessageFormat.format("'{0}',''{1}'',{2}", "aaa", "bbb", "ccc");
            assertEquals("{0},'bbb',ccc", msg);
        }
    }

}
