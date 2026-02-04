package me.test.jdk.java.util.concurrent.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Java中的基本类型int、String和封装类型Integer都是不可修改的，参数传递过程中相当于值传递。
 * 而java.util.concurrent.atomic.*则是可修改的，是引用传递。
 */
public class AtomicBooleanTest {

    public static void main(String[] args) {

        System.out.println("------------------------Boolean");
        Boolean b1 = Boolean.TRUE;
        System.out.println("old value: " + b1);
        changeValue(b1);
        System.out.println("new value: " + b1);

        System.out.println("------------------------AtomicBoolean");
        AtomicBoolean b2 = new AtomicBoolean(true);
        System.out.println("old value: " + b2);
        changeValue(b2);
        System.out.println("new value: " + b2);
    }

    public static void changeValue(Boolean b) {

        System.out.println("param old value: " + b);
        b = !b;
        System.out.println("param new value: " + b);
    }

    public static void changeValue(AtomicBoolean b) {

        System.out.println("param old value: " + b);
        b.set(!b.get());
        System.out.println("param new value: " + b);
    }
}
