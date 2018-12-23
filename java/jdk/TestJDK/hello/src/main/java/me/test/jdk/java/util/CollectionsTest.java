package me.test.jdk.java.util;

import java.io.IOException;
import java.util.*;

/**
 *
 */
public class CollectionsTest {

    public static void main(String[] args) throws IOException {
        test02();

    }

    public static void test01() {
        List<String> list1 = Arrays.asList("aa", "bb", "cc", "dd");
        List<String> list2 = Arrays.asList("cc", "dd", "xx", "yy");

        // 判断 list2, list2 是否有共通元素，true - 没有共通元素
        System.out.println(Collections.disjoint(list1, list2));
        System.out.println(list1.retainAll(list2));
    }

    public static void test02() {
        Set<String> set1 = new HashSet<>(Arrays.asList("aa", "bb", "11", "22"));
        Set<String> set2 = new HashSet<>(Arrays.asList("11", "22", "xx", "yy"));

        // 交集
        Set<String> intersection = new HashSet<>(set1);
        System.out.println(intersection.retainAll(set2));
        System.out.println("intersection = " + intersection);

        Set<String> onlyInSet1 = new HashSet<>(set1);
        onlyInSet1.removeAll(intersection);
        System.out.println("onlyInSet1 = " + onlyInSet1);

        Set<String> onlyInSet2 = new HashSet<>(set2);
        onlyInSet2.removeAll(intersection);
        System.out.println("onlyInSet2 = " + onlyInSet2);
    }

}
