package me.test.jdk.java.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/7/17
 */
public class InheritStaticFieldTest {
    @Test
    public void test01() {
        Assertions.assertTrue(A.set.isEmpty());
        B b = new B();
        Assertions.assertEquals(Collections.singleton("B"), A.set);
        C C = new C();
        Assertions.assertEquals(new HashSet<>(Arrays.asList("B", "C")), A.set);
    }

    public static abstract class A {
        public static final Set<String> set = new HashSet<>();
    }

    public static class B extends A {
        public B() {
            set.add("B");
        }
    }

    public static class C extends A {
        public C() {
            set.add("C");
        }
    }


}
