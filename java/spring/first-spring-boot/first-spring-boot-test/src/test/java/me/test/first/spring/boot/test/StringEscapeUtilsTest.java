package me.test.first.spring.boot.test;

import io.micrometer.core.instrument.util.StringEscapeUtils;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2019-07-25
 */
public class StringEscapeUtilsTest {

    @Test
    public void test01() {
        String str = "{\"aaa\":\"中文1，2，\\\"3\"}";
        String s = StringEscapeUtils.escapeJson(str);
        System.out.println(str);
        System.out.println(s);
    }
}
