package me.test.jdk.java.util;

import org.junit.jupiter.api.Test;

import java.util.TreeMap;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
public class TreeMapTest {
    @Test
    public void test() {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("bbb", "b01");
        treeMap.put("ddd", "d01");
        treeMap.put("aaa", "a01");
        treeMap.put("ccc", "c01");
        System.out.println(treeMap);
    }
}
