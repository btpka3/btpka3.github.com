package me.test.jdk.java.util.stream;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CreateStremTest {
    public static void main(String[] args) {


        // 创建 子 Stream - 5 的倍数，7的倍数，11的倍数，并计算相应的平均值。


        Stream.Builder<Integer> stream3 = Stream.builder();
        AtomicInteger count3 = new AtomicInteger(0);
        AtomicInteger total3 = new AtomicInteger(0);


        Stream.Builder<Integer> stream7 = Stream.builder();
        AtomicInteger count7 = new AtomicInteger(0);
        AtomicInteger total7 = new AtomicInteger(0);


        Stream.Builder<Integer> stream11 = Stream.builder();
        AtomicInteger count11 = new AtomicInteger(0);
        AtomicInteger total11 = new AtomicInteger(0);


        IntStream.range(0, 1000)
                .forEach(i -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i % 3 == 0) {
                        stream3.accept(i);
                    } else if (i % 7 == 0) {
                        stream7.accept(i);
                    } else if (i % 11 == 0) {
                        stream11.accept(i);
                    }
                    if(i==999){
                        System.out.println("=======");
                    }
                });


        // Stream.Builder#builder() 方法只能在有数据时才能调用
        // 此例子中所有的数据都被缓存了。
        System.out.println(111);

        stream3.build().forEach(i -> {
            count3.addAndGet(1);
            total3.addAndGet(i);
        });

        stream7.build().forEach(i -> {
            count7.addAndGet(1);
            total7.addAndGet(i);
        });

        stream11.build().forEach(i -> {
            count11.addAndGet(1);
            total11.addAndGet(i);
        });

        System.out.println(" type : count : total");
        System.out.println(" 3 : " + count3.get() + " : " + total3.get());
        System.out.println(" 7 : " + count7.get() + " : " + total7.get());
        System.out.println("11 : " + count11.get() + " : " + total11.get());

    }
}
