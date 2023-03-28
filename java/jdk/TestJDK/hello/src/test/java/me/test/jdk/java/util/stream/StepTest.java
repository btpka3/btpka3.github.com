package me.test.jdk.java.util.stream;

import java.util.stream.Stream;

/**
 * @author 当千
 * @date 2018-10-26
 */
public class StepTest {

    public static void main(String[] args) {

        Stream.of("aaa", "bbb", "ccc")
                .peek(s -> System.out.println("peek 0 : " + s))
                .peek(s -> System.out.println("peek 1 : " + s))
                .filter(s -> s.contains("a") || s.contains("c"))
                .forEach(s -> System.out.println("forEach : " + s));
    }
}
