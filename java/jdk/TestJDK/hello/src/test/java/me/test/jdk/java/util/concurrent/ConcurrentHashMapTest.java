package me.test.jdk.java.util.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest1 {

    public static void main(String[] args) throws Exception {
        test01();
    }

    public static void test01() throws Exception {
        Map m = new ConcurrentHashMap();
        m.put(null, "aa");
        System.out.println(m);
    }

}
