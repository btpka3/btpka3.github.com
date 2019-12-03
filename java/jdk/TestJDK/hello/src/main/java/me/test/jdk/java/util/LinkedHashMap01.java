package me.test.jdk.java.util;

import java.util.LinkedHashMap;

/**
 * LRU cache?
 */
public class LinkedHashMap01 {

    public static void main(String[] args) {
        LinkedHashMap m = new LinkedHashMap(4, 0.75f, true);

        m.put("a", "aaa");
        m.put("b", "bbb");
        m.put("c", "ccc");
        m.put("d", "ddd");
        m.put("e", "eee");
        System.out.println(m);
        m.get("e");
        System.out.println(m);

    }
}
