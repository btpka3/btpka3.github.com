package me.test.checkstyle;

import org.apache.commons.lang.StringUtils;

/**
 * 测试禁止 import org.apache.commons.lang.StringUtils;
 */
public class DisableImportClass {

    public static void xxx() {
        System.out.println(StringUtils.isEmpty("sss"));
    }
}
