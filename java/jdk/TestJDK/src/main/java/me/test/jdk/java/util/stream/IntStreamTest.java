package me.test.jdk.java.util.stream;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Study IntStream
 */
public class IntStreamTest {
    public static void main(String[] args) {

        IntStream intStream = IntStream.range(0, 10);
        LongStream longStream = LongStream.range(Long.MIN_VALUE, Long.MAX_VALUE);
        List<Object> list = Arrays.asList(1, 2L, "A", 3.3, true, 1, 4, 2);


        // 循环
        System.out.print("intStream : ");
        intStream.forEach(value -> System.out.print(value));
        System.out.println();

        // IntStream 不能重复使用
        // System.out.println(intStream.count());


        // 计数 - 注意: 这将遍历每一个元素, 非常耗时。
        System.out.println("list count : " + list.stream().count());
        // System.out.println(longStream.count());

        // 注意: 2L != 2
        System.out.println("list distinct : " + Arrays.toString(list.stream().distinct().toArray()));

        // Map-Reduce
        int bigNum = 10_0000;

        // 如果仅仅分片查找数据, 时间还是相差很大的
        // 1.1 : PT0.555S
        // 1.2 : PT1.578S
        // testParallel1(bigNum);

        // 如果分片处理后,在合并在一起排序, 则不如单线程进行
        // 2.1 : PT1.555S
        // 2.2 : PT1.325S
        testParallel2(bigNum);

    }

    static void testParallel1(int bigNum) {


        long start1 = System.currentTimeMillis();
        IntStream.range(0, bigNum)
                .parallel()
                .filter(IntStreamTest::isPrime)
                .forEach(System.out::println);
        long end1 = System.currentTimeMillis();
        System.out.println("================================================= ");

        long start2 = System.currentTimeMillis();
        IntStream.range(0, bigNum)
                .filter(IntStreamTest::isPrime)
                .forEach(System.out::println);
        long end2 = System.currentTimeMillis();


        System.out.println("1.1 : " + Duration.ZERO.plusMillis(end1 - start1));
        System.out.println("1.2 : " + Duration.ZERO.plusMillis(end2 - start2));
    }


    static void testParallel2(int bigNum) {


        long start1 = System.currentTimeMillis();
        IntStream.range(0, bigNum)
                .parallel()
                .filter(IntStreamTest::isPrime)
                .sequential()
                .sorted()
                .forEach(System.out::println);
        long end1 = System.currentTimeMillis();
        System.out.println("================================================= ");

        long start2 = System.currentTimeMillis();
        IntStream.range(0, bigNum)
                .filter(IntStreamTest::isPrime)
                .sorted()
                .forEach(System.out::println);
        long end2 = System.currentTimeMillis();


        System.out.println("2.1 : " + Duration.ZERO.plusMillis(end1 - start1));
        System.out.println("2.2 : " + Duration.ZERO.plusMillis(end2 - start2));
    }


    /**
     * 判断是否是素数
     */
    static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
