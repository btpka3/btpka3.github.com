package me.test.jdk.java.util;


import org.junit.Test;

import java.util.*;

/**
 * @author dangqian.zll
 * @date 2019-09-02
 */
public class LinkedHashSetTest {

    @Test
    public void test01() {
        Set<String> set = new LinkedHashSet<>();

        List<String> list0 = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        List<String> list1 = Arrays.asList("ddd", "ccc", "eee", "fff");

        set.addAll(list0);
        set.addAll(list1);

        List<String> list2 = new ArrayList<>(set);
        List<String> list = list2.size() < 10 ? list2 : list2.subList(0, 6);

        System.out.println(list);

    }
}
