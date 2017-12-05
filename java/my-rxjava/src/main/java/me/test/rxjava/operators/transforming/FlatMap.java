package me.test.rxjava.operators.transforming;

import io.reactivex.*;

import java.util.*;
import java.util.Map;

/**
 *
 * Map 操作是同步的， 而 flatMap 则可以是 异步的，而且可能 一对一，一对多的返回。
 *
 * http://reactivex.io/documentation/operators/flatmap.html
 */
public class FlatMap {


    public static void main(String[] args) {

        //flatMap01();
        flatMap02();
        //flatMap03();

    }

    public static void flatMap01() {
        System.out.println("----------------------flatMap01");

        // 打印的仍然但是 flowable , 而非字符串
        Flowable.just("Hello world")
                .subscribe(System.out::println);
    }

    // 一个变多个
    public static void flatMap02() {
        System.out.println("----------------------flatMap02");
        Flowable.fromArray(1, 2, 3)
                .flatMap(i -> Flowable.fromArray(i * i, i * i * i))
                .subscribe(System.out::println);
    }


    public static void flatMap03() {
        System.out.println("----------------------flatMap03");
        java.util.Map<String, String> m1 = new LinkedHashMap<>();
        m1.put("a", "aaa");
        m1.put("b", "bbb");

        Map<String, String> m2 = new LinkedHashMap<>();
        m2.put("c", "ccc");
        m2.put("d", "ddd");
        m2.put("e", "eee");


        Flowable.fromArray(m1, m2)
                .flatMap(m -> {
                    System.out.println("\t flat-map  : " + Thread.currentThread().getName());
                    return Flowable.fromIterable(m.entrySet());
                })
                .subscribe(i -> {
                    System.out.println("\t subscribe : " + Thread.currentThread().getName() + " : " + i);
                });
    }


}
