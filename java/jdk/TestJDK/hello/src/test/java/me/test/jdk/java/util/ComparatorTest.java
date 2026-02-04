package me.test.jdk.java.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2019-06-26
 */
public class ComparatorTest {

    List<M> newList1() {

        List<M> list = new ArrayList<>();

        {
            M m = new M();
            m.setName("zhang3");
            m.setAge(28);
            list.add(m);
        }

        {
            list.add(null);
        }

        {
            M m = new M();
            m.setName("wang5");
            m.setAge(19);
            list.add(m);
        }

        {
            M m = new M();
            m.setName("zhao6");
            m.setAge(33);
            list.add(m);
        }

        {
            M m = new M();
            m.setName("sun7");
            m.setAge(null);
            list.add(m);
        }

        {
            M m = new M();
            m.setName("zhou8");
            m.setAge(30);
            list.add(m);
        }
        return list;
    }

    List<M> newList2() {

        List<M> list = new ArrayList<>();

        {
            M m = new M();
            m.setName("zhang3");
            m.setAge(28);
            list.add(m);
        }

        {
            M m = new M();
            m.setName("li4");
            m.setAge(null);
            list.add(m);
        }

        {
            M m = new M();
            m.setName("wang5");
            m.setAge(19);
            list.add(m);
        }

        {
            M m = new M();
            m.setName("zhao6");
            m.setAge(33);
            list.add(m);
        }
        return list;
    }

    @Test
    public void testNullFirstAndReverse01() {
        List<String> list = Arrays.asList("bb", "aa", null, "cc");
        list.sort(Comparator.nullsLast(Comparator.<String>naturalOrder()).reversed());

        Assertions.assertNull(null, list.get(0));
        Assertions.assertEquals("cc", list.get(1));
        Assertions.assertEquals("bb", list.get(2));
        Assertions.assertEquals("aa", list.get(3));
    }

    @Test
    public void testNullFirstAndReverse02() {

        List<M> list = newList1();

        // 先比较整个对象，再比较keyz
        list.sort(Comparator.nullsLast(
                        Comparator.comparing(M::getAge, Comparator.nullsLast(Comparator.<Integer>naturalOrder())))
                .reversed());

        Assertions.assertNull(list.get(0));
        Assertions.assertEquals("sun7", list.get(1).getName());
        Assertions.assertEquals("zhao6", list.get(2).getName());
        Assertions.assertEquals("zhou8", list.get(3).getName());
        Assertions.assertEquals("zhang3", list.get(4).getName());
        Assertions.assertEquals("wang5", list.get(5).getName());
    }

    @Test
    public void testNullFirstAndReverse03() {

        List<M> list = newList2();

        list.sort(Comparator.comparing(
                M::getAge,
                Comparator.nullsLast(Comparator.<Integer>naturalOrder()).reversed()));

        Assertions.assertEquals("li4", list.get(0).getName());
        Assertions.assertEquals("zhao6", list.get(1).getName());
        Assertions.assertEquals("zhang3", list.get(2).getName());
        Assertions.assertEquals("wang5", list.get(3).getName());
    }

    public static class M {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "M{" + "name='" + name + '\'' + ", age=" + age + '}';
        }
    }
}
