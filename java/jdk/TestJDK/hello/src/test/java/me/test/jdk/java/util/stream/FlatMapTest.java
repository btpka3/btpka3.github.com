package me.test.jdk.java.util.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class FlatMapTest {
    public static void main(String[] args) {

        List<Integer> intList = IntStream.range(0, 10)
                .flatMap(i -> IntStream.of(i * 10, i * 100))
                .boxed()
                .collect(Collectors.toList());

        System.out.println(intList);
    }
}
