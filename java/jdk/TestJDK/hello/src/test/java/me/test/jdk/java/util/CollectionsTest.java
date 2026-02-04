package me.test.jdk.java.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class CollectionsTest {

    @Test
    public void test01() {
        List<String> list1 = Arrays.asList("aa", "bb", "cc", "dd");
        List<String> list2 = Arrays.asList("cc", "dd", "xx", "yy");

        // 判断 list2, list2 是否有共通元素，true - 没有共通元素
        System.out.println(Collections.disjoint(list1, list2));
        System.out.println(list1.retainAll(list2));
    }

    @Test
    public void test02() {
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

    @Test
    public void test03() {
        List<String> list1 = Arrays.asList("aa", "bb", "cc", "dd");
        Collections.shuffle(list1);
        System.out.println(list1);
    }

    @Test
    public void test04() {
        Set<String> set1 = new HashSet<>(Arrays.asList("a" + 1, "b" + 1));
        Set<String> set2 = new LinkedHashSet<>(Arrays.asList("b1", "a1"));

        assertEquals(set1, set2);
        assertTrue(Objects.equals(set1, set2));
    }

    @Test
    public void test05() {
        Vector<String> v = new Vector<String>();
        v.add("aaa");
        v.add("bbb");
        v.add("ccc");
        System.out.println("Elements in vector : " + v);

        // getting enumeration of the vector element
        Enumeration<String> elementsEnumeration = v.elements();
        List<String> list = Collections.list(elementsEnumeration);
        assertEquals(Arrays.asList("aaa", "bbb", "ccc"), list);
    }
}
