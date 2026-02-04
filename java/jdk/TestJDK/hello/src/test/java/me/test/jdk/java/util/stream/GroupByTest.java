package me.test.jdk.java.util.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 */
public class GroupByTest {
    public static void main(String[] args) {

        test2();
    }

    public static void test1() {
        Map<Integer, List<Integer>> map =
                IntStream.range(0, 9).boxed().collect(Collectors.groupingBy((Integer i) -> i % 3));

        System.out.println(map);
    }

    /**
     * 按照分组，组内排序，然后按分组优先级合并输出。
     * 期待结果: 0,3,6,   1,4,7,    2,5,8
     */
    public static void test2() {
        // 无序的一组数列
        List<Integer> list =
                Stream.of(8, 4, 3, 5, 2, 1, 7, 0, 6)
                        .collect(Collectors.groupingBy((Integer i) -> i % 3))
                        .entrySet()
                        .stream()
                        .sorted(Comparator.comparing(Map.Entry::getKey))
                        .flatMap(entry -> {
                            System.out.println("======" + entry);
                            return entry.getValue().stream().sorted(Comparator.comparing(Function.identity()));
                        })
                        .collect(Collectors.toList());

        System.out.println(list);
    }

    /**
     * 按照分组，组内排序，然后按分组优先级合并输出。
     * 期待结果: 0,3,6,   1,4,7,    2,5,8
     */
    public static void test3() {

        // 无序的一组数列
        List<Integer> list = Stream.of(8, 4, 3, 5, 2, 1, 7, 0, 6)
                .sorted(Comparator.comparingInt((Integer i) -> i % 3).thenComparingInt(i -> i))
                .collect(Collectors.toList());

        System.out.println(list);
    }
}
