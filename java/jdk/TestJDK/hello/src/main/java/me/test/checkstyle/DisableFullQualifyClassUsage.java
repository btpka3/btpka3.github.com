package me.test.checkstyle;

import org.apache.commons.lang3.StringUtils;

/**
 * 测试禁止 使用全限定名（org.apache.commons.lang.StringUtils）。
 */
public class DisableFullQualifyClassUsage {

    public static void xxx() {
        System.out.println(StringUtils.isEmpty("sss"));
    }
}
