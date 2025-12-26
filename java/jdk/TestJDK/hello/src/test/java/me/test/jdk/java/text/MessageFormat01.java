package me.test.jdk.java.text;

import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MessageFormat01 {

    @Test
    public void test() {
        Locale.setDefault(Locale.CHINESE);
        assertSame(Locale.CHINESE, Locale.getDefault(Locale.Category.FORMAT));
        assertSame(Locale.CHINESE, Locale.getDefault());
        {
            String msg = MessageFormat.format("{0,number,percent}", 1.23456);
            assertEquals("123%", msg);
        }
        // \u00A4 : `¤` 表示货币
        {
            String msg = MessageFormat.format("{0,number,currency}", 1234567890);
            assertEquals("¤1,234,567,890.00", msg);
        }
        {
            String msg = MessageFormat.format("{0,number,¤#}", 1234567890);
            assertEquals("¤1234567890", msg);
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
