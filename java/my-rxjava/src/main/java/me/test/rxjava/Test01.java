package me.test.rxjava;

import io.reactivex.*;
import io.reactivex.schedulers.*;

import java.time.*;
import java.util.*;

/**
 * http://rxwiki.wikidot.com/101samples#toc24
 */
public class Test01 {
    public static void main(String[] args) {

        groupBy();

    }











    public static void groupBy() {
        System.out.println("----------------------groupBy");


        Flowable.range(1, 10)

                // groupBy 之后，元素长度为3
                // 内部函数返回对应元素所属 group 的 key
                .groupBy(i -> i % 3)
                .subscribe(f -> {  // f - GroupedFlowable : FlowableGroupBy$GroupedUnicast
                    // FIXME: 死锁
                    //f.subscribe(System.out::println    );


                    List<Integer> intList = f
                            //.observeOn(Schedulers.io())
                            .toList()
                            .blockingGet();

                    System.out.println(f + "," + f.getKey() + " : " +intList);
                });


    }



//    public static void async() {
//        System.out.println("----------------------firstReturnedEle");
//
//        long startTime = System.currentTimeMillis();
//        int firstReturnedEle = Flowable.fromArray(3, 5, 7, 2)
//                .ambWith(i -> Flowable.fromCallable(i, () -> {
//                    Thread.sleep(i * 1000);
//                    return i + 1;
//                }))
//
//                .blockingFirst();
//        long endTime = System.currentTimeMillis();
//        Duration duration = Duration.ofMillis(endTime - startTime);
//        System.out.println("firstReturnedEle=" + firstReturnedEle + ", duration = " + duration);
//    }

//    public static void firstReturnedEle() {
//        System.out.println("----------------------firstReturnedEle");
//
//        long startTime = System.currentTimeMillis();
//        int firstReturnedEle = Flowable.fromArray(3, 5, 7, 2)
//                .ambWith(i-> Flowable.fromCallable(i, ()->{
//                    Thread.sleep(i * 1000);
//                    return i+1;
//                }))
//
//                .blockingFirst();
//        long endTime = System.currentTimeMillis();
//        Duration duration = Duration.ofMillis(endTime - startTime);
//        System.out.println("firstReturnedEle=" + firstReturnedEle + ", duration = " + duration);
//    }

//    public static void lastReturnedEle() {
//
//        long startTime = System.currentTimeMillis();
//        int lastReturnedEle = Flowable.fromArray(3, 5, 7, 2)
//                .map(i -> {
//                    Thread.sleep(i * 1000);
//                    return i;
//                })
//                .blockingLast();
//        long endTime = System.currentTimeMillis();
//        Duration duration = Duration.ofMillis(endTime - startTime);
//        System.out.println("lastReturnedEle=" + lastReturnedEle + ", duration = " + duration);
//    }
}
