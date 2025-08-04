package me.test.checkstyle;


/**
 * 校验不使用 import 语句，直接使用全限定名是否会被检测。
 */
public class Demo2 {

    public static void xxx() {
        System.out.println(org.apache.commons.lang.StringUtils.isEmpty("sss"));
    }
}
