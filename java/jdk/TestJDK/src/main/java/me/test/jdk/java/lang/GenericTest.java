package me.test.jdk.java.lang;

import java.util.Arrays;
import java.util.List;

public class GenericTest<E> {

    public static void main(String[] args) {
        String e = "abc";
        List<String> list = Arrays.asList("abc", "def");

        System.out.println("============ test 1");
        GenericTest<String> g1 = new GenericTest<String>();
        g1.print(e);
        g1.print(list);

        System.out.println("============ test 2");
        GenericTest<Object> g2 = new GenericTest<Object>();
        g2.print(e);
        g2.print(list);
    }

    public void print(List<E> list) {
        System.out.println("print(List<E>) : " + list);
    }

    public void print(E e) {
        System.out.println("print(E) : " + e);
    }

}
