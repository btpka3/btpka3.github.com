package me.test.jdk.java.util;

import java.util.StringJoiner;

public class StringJoinerTest {

    public static void main(String[] args) throws Exception {

        StringJoiner sj = new StringJoiner(",");
        System.out.println(sj.add("a").add("b").add("c"));
    }
}
