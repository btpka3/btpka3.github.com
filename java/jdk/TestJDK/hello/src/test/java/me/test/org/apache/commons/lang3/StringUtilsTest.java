package me.test.org.apache.commons.lang3;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author dangqian.zll
 * @date 2020-06-18
 */
public class StringUtilsTest {

    @Test
    public void testReplace01() {
        String tpl = "000_${RND}_111_${RND}_222";
        String result = StringUtils.replace(tpl, "${RND}", "AAA");
        System.out.println(result);
    }

    @Test
    public void isBlank() {
        assertTrue(StringUtils.isBlank(" "));
        assertTrue(StringUtils.isBlank("\n"));
        assertTrue(StringUtils.isBlank("\t"));
        assertTrue(StringUtils.isBlank("\n\t "));
    }
}
