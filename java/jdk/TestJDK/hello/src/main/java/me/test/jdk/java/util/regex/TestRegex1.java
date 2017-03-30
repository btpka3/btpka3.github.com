package me.test.jdk.java.util.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex1 {

    public static void main(String[] args) {
        testEscape();
    }

    /**
     * 处理字符串时，常常会有转义字符。
     * 顺序很重要。reg1 和 reg2 中的或是调换顺序的。
     */
    public static void testEscape() {

        String str = "file:/Users/zll/work/git-repo/github/btpka3/btpka3.github.com/java/spring/first-spring-boot-data-mongo/data-domain/build/libs/data-domain-0.1.0.jar\n";

        String reg1 = "/data-domain-.*\\.jar";
        Pattern p1 = Pattern.compile(reg1);


        System.out.println(p1.matcher(str).find());


    }

}
