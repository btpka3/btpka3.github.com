package me.test.jdk.java.util;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author 当千
 * @date 2019-02-20
 */
public class TreeSetTest {

    public static void main(String[] args) throws Exception {
        Set<String> s = new TreeSet<>();
        s.add("xxx");
        s.add("ccc");
        s.add("ddd");
        s.add("eee");
        s.add("aaa");
        s.add("bbb");
        for (String str : s) {
            System.out.println(str);
        }
    }
}
