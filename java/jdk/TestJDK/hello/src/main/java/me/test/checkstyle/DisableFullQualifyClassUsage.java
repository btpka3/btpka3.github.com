package me.test.checkstyle;

/**
 * 测试禁止 使用全限定名（org.apache.commons.lang.StringUtils）。
 */
public class DisableFullQualifyClassUsage {

    public static void xxx() {
        System.out.println(org.apache.commons.lang.StringUtils.isEmpty("sss"));
    }
}
