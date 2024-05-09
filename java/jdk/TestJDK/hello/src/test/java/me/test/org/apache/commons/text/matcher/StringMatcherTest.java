package me.test.org.apache.commons.text.matcher;

import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/5/7
 */
public class StringMatcherTest {

    @Test
    public void test01() {
        StringMatcher sm = StringMatcherFactory.INSTANCE.doubleQuoteMatcher();
        int result = sm.isMatch("aaa\"bbb\"ccc", 3);
        Assertions.assertEquals(1, result);
    }
}
