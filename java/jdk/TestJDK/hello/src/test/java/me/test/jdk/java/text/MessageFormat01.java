package me.test.jdk.java.text;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static java.util.Locale.CHINESE;
import static java.util.Locale.ENGLISH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

@Slf4j
public class MessageFormat01 {

    // "2001-01-02 03:04:05.789" == 978375845789
    private static final Date TEST_DATE = new Date(978375845789L);

    @Data
    @AllArgsConstructor
    static class Rec {
        String description;
        String pattern;
        Object[] args;
        /**
         * KEY=目标 locale
         * VALUE=期待的结果
         */
        Map<Locale, String> expected;
        boolean shouldThrowException;

        public Rec(String description, String pattern, Object[] args, Map<Locale, String> expected) {
            this(description, pattern, args, expected, false);
        }
    }

    List<Rec> recList = List.of(
            new Rec("基本数值格式化", "{0}", new Object[]{1234567890.123456},
                    Map.of(CHINESE, "1,234,567,890.123", ENGLISH, "1,234,567,890.123")),

            new Rec("百分比格式", "{0,number,percent}", new Object[]{1.23456},
                    Map.of(CHINESE, "123%", ENGLISH, "123%")),

            new Rec("货币格式", "{0,number,currency}", new Object[]{1234567890.123},
                    Map.of(CHINESE, "¤1,234,567,890.12", ENGLISH, "¤1,234,567,890.12")),

            new Rec("货币符号单个¤", "{0,number,¤#.##}", new Object[]{1234567890.123},
                    Map.of(CHINESE, "¤1234567890.12", ENGLISH, "¤1234567890.12")),

            new Rec("货币符号双个¤¤", "{0,number,¤¤#.##}", new Object[]{1234567890.123},
                    Map.of(CHINESE, "XXX1234567890.12", ENGLISH, "XXX1234567890.12")),

            new Rec("整数格式(带逗号)", "{0,number,integer}", new Object[]{1234567890},
                    Map.of(CHINESE, "1,234,567,890", ENGLISH, "1,234,567,890")),

            new Rec("整数格式(不带逗号)", "{0,number,#}", new Object[]{1234567890},
                    Map.of(CHINESE, "1234567890", ENGLISH, "1234567890")),

            new Rec("负数格式", "{0,number,#}", new Object[]{-1234567890},
                    Map.of(CHINESE, "-1234567890", ENGLISH, "-1234567890")),

            new Rec("4位分组", "{0,number,#,####.##}", new Object[]{1234567890.123456},
                    Map.of(CHINESE, "12,3456,7890.12", ENGLISH, "12,3456,7890.12")),

            new Rec("3位分组", "{0,number,#,###.##}", new Object[]{1234567890.1},
                    Map.of(CHINESE, "1,234,567,890.1", ENGLISH, "1,234,567,890.1")),

            new Rec("前导零(小数)", "{0,number,0000.00}", new Object[]{123.1},
                    Map.of(CHINESE, "0123.10", ENGLISH, "0123.10")),

            new Rec("前导零(大数)", "{0,number,0000.00}", new Object[]{12345.1},
                    Map.of(CHINESE, "12345.10", ENGLISH, "12345.10")),

            new Rec("Date默认格式", "{0}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "2001/1/2 03:04", ENGLISH, "1/2/01, 3:04 AM")),

            new Rec("Date格式", "{0,date}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "2001年1月2日", ENGLISH, "Jan 2, 2001")),

            new Rec("Date short格式", "{0,date,short}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "2001/1/2", ENGLISH, "1/2/01")),

            new Rec("Date medium格式", "{0,date,medium}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "2001年1月2日", ENGLISH, "Jan 2, 2001")),

            new Rec("Date long格式", "{0,date,long}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "2001年1月2日", ENGLISH, "January 2, 2001")),

            new Rec("Date full格式", "{0,date,full}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "2001年1月2日星期二", ENGLISH, "Tuesday, January 2, 2001")),

            new Rec("Time默认格式", "{0,time}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "03:04:05", ENGLISH, "3:04:05\u202fAM")),

            new Rec("Time short格式", "{0,time,short}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "03:04", ENGLISH, "3:04\u202fAM")),

            new Rec("Time medium格式", "{0,time,medium}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "03:04:05", ENGLISH, "3:04:05\u202fAM")),

            new Rec("Time long格式", "{0,time,long}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "CST 03:04:05", ENGLISH, "3:04:05\u202fAM CST")),

            new Rec("Time full格式", "{0,time,full}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "中国标准时间 03:04:05", ENGLISH, "3:04:05\u202fAM China Standard Time")),

            new Rec("自定义日期格式", "{0,date,yyyy-MM-dd HH:mm:ss.S}", new Object[]{TEST_DATE},
                    Map.of(CHINESE, "2001-01-02 03:04:05.789", ENGLISH, "2001-01-02 03:04:05.789")),

            new Rec("单引号转义", "'{0}',''{1}'',{2}", new Object[]{"aaa", "bbb", "ccc"},
                    Map.of(CHINESE, "{0},'bbb',ccc", ENGLISH, "{0},'bbb',ccc")),

            new Rec("空占位符异常", "{},{0},{1},{2}", new Object[]{"aaa", "bbb", "ccc"},
                    Map.of(CHINESE, "IllegalArgumentException", ENGLISH, "IllegalArgumentException"), true)
    );


    @Test
    public void test() {
        testWithLocale(CHINESE);
        testWithLocale(ENGLISH);
    }

    private void testWithLocale(Locale targetLocale) {
        Locale.setDefault(targetLocale);
        assertSame(targetLocale, Locale.getDefault(Locale.Category.FORMAT));
        assertSame(targetLocale, Locale.getDefault());

        log.info("\n========== Testing with Locale: {} ==========", targetLocale);

        for (int i = 0; i < recList.size(); i++) {
            Rec rec = recList.get(i);
            String expectedResult = rec.getExpected().get(targetLocale);

            try {
                if (rec.shouldThrowException) {
                    // 期望抛出异常的测试用例
                    assertThrows(IllegalArgumentException.class, () -> {
                        MessageFormat.format(rec.pattern, rec.args);
                    });
                    log.info("[{}] ✓ {} - Pattern: {} - Expected Exception: {}",
                            i, rec.description, rec.pattern, expectedResult);
                } else {
                    // 正常测试用例
                    String actualResult = MessageFormat.format(rec.pattern, rec.args);
                    assertEquals(String.format("Record [%d] %s failed", i, rec.description),
                            expectedResult, actualResult);
                    log.info("[{}] ✓ {} - Pattern: {} - Result: {}",
                            i, rec.description, rec.pattern, actualResult);
                }
            } catch (AssertionError e) {
                String actualResult = MessageFormat.format(rec.pattern, rec.args);
                throw new AssertionError(String.format("Record [%d] %s failed. Expected: %s, Actual: %s",
                        i, rec.description, expectedResult, actualResult), e);
            } catch (Exception e) {
                throw new RuntimeException(String.format("Record [%d] %s threw unexpected exception",
                        i, rec.description), e);
            }
        }

        log.info("\n========== All {} tests passed for Locale: {} ==========\n",
                recList.size(), targetLocale);
    }

}
