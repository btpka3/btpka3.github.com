package me.test.jdk.java.util.stream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class GroupByTest {
    public static void main(String[] args) {

        Map<Integer, List<Integer>> map = IntStream.range(0, 9)
                .boxed()
                .collect(Collectors.groupingBy((Integer i) -> i % 3));

        System.out.println(map);
    }
}
