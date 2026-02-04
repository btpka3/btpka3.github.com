package me.test.jdk.java.lang;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/6/14
 */
public class Class2Test {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Class2Test.class = " + getClassInfo(Class2Test.class));
    }

    static String getClassInfo(Object obj) {
        return obj.getClass().getName() + "@"
                + Integer.toHexString(obj.getClass().hashCode());
    }

    /**
     * 验证 A: 静态内部类， B: 方法内的内部类， C：类的non-static内部类， D：方法内的 lambda 中相关class的定义和加载情况。
     * <p>
     * 请打开以下JVM 开关，验证不同case 的类加载情况。
     *
     * <ul>
     *     <li>JDK9 以前: `-XX:+TraceClassLoading`</li>
     *     <li>JDK9 以及之后: `-Xlog:class+load=info`</li>
     * </ul>
     * <p>
     * 结论：类定义出现多少次，就 定义/load 多少次class。故：
     * <ul>
     *     <li>case（A）: 静态内部类只 触发类加载 一次</li>
     *     <li>case（B）、（C）、（D）都属于not-static的内部类，随着代码块定义（即new出现）的位置的不同而触发类加载</li>
     *     <li>for 循环内的 case（B）、（C）、（D），因为类定义的位置不变，故无论循环多少次，仅首次触发类加载</li>
     * </ul>
     */
    @Test
    public void testInnerNonStaticClass() {

        int count = 3;
        for (int i = 0; i < 2; i++) {
            System.out.println("========================== loop=" + i);
            testA(count);
            testB(count);
            testC(count);
            testD(count);
            System.out.println("========================== end");
        }
    }

    void testA(int count) {
        System.out.println("========================== A. test inner static class");
        // 类的内部类（static）
        for (int i = 0; i < count; i++) {
            Map obj = new StaticFluentHashMap<>().with("a", "aaa");
            System.out.println("[" + i + "] class = " + getClassInfo(obj));
        }
        Map objA1 = new StaticFluentHashMap<>().with("a", "aaa");
        Map objA2 = new StaticFluentHashMap<>().with("a", "aaa");
        System.out.println("objA1.class = " + getClassInfo(objA1));
        System.out.println("objA2.class = " + getClassInfo(objA2));
    }

    void testB(int count) {
        System.out.println("========================== B. test inner non-static class1 ");
        // 方法内的内部类
        for (int i = 0; i < count; i++) {
            Map obj = new HashMap<>() {
                {
                    put("a", "aaa");
                }
            };
            System.out.println("[" + i + "] class = " + getClassInfo(obj));
        }

        Map objB1 = new HashMap<>() {
            {
                put("a", "aaa");
            }
        };
        Map objB2 = new HashMap<>() {
            {
                put("a", "aaa");
            }
        };
        System.out.println("objB1.class = " + getClassInfo(objB1));
        System.out.println("objB2.class = " + getClassInfo(objB2));
    }

    void testC(int count) {
        System.out.println("========================== C. test inner non-static class2");
        // 类的内部类（非static）
        for (int i = 0; i < count; i++) {
            Map obj = new InstantFluentHashMap<>().with("b", "bbb");
            System.out.println("[" + i + "] class = " + getClassInfo(obj));
        }
        Map objC1 = new InstantFluentHashMap<>().with("b", "bbb");
        Map objC2 = new InstantFluentHashMap<>().with("b", "bbb");
        System.out.println("objC1.class = " + getClassInfo(objC1));
        System.out.println("objC2.class = " + getClassInfo(objC2));
    }

    void testD(int count) {
        System.out.println("========================== D. test inner lambda class");
        // 方法内的 lambda
        for (int i = 0; i < count; i++) {
            Runnable obj = () -> System.out.println(this.getClass().getName());
            System.out.println("[" + i + "] class = " + getClassInfo(obj));
        }
        Runnable objD1 = () -> System.out.println(this.getClass().getName());
        Runnable objD2 = () -> System.out.println(this.getClass().getName());
        System.out.println("objD1.class = " + getClassInfo(objD1));
        System.out.println("objD2.class = " + getClassInfo(objD2));
    }

    /**
     * 内部类，有 static 关键词修饰。
     *
     * @param <K>
     * @param <V>
     */
    public static class StaticFluentHashMap<K, V> extends HashMap<K, V> {

        public StaticFluentHashMap(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }

        public StaticFluentHashMap(int initialCapacity) {
            super(initialCapacity);
        }

        public StaticFluentHashMap() {}

        public StaticFluentHashMap(Map<? extends K, ? extends V> m) {
            super(m);
        }

        public StaticFluentHashMap<K, V> with(K key, V value) {
            put(key, value);
            return this;
        }
    }

    /**
     * 内部类，无 static 关键词修饰。
     *
     * @param <K>
     * @param <V>
     */
    public class InstantFluentHashMap<K, V> extends HashMap<K, V> {

        public InstantFluentHashMap(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }

        public InstantFluentHashMap(int initialCapacity) {
            super(initialCapacity);
        }

        public InstantFluentHashMap() {}

        public InstantFluentHashMap(Map<? extends K, ? extends V> m) {
            super(m);
        }

        public InstantFluentHashMap<K, V> with(K key, V value) {
            put(key, value);
            return this;
        }
    }
}
