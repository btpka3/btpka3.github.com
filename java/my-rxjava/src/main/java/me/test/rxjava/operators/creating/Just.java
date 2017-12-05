package me.test.rxjava.operators.creating;

import io.reactivex.*;

/**
 * http://reactivex.io/documentation/operators/just.html
 */
public class Just {


    public static void main(String[] args) {

        just01();
        //just02();

    }

    public static void just01() {
        System.out.println("----------------------just01");

        Flowable.just("Hello world")
                .subscribe(System.out::println);
    }

    public static void just02() {
        System.out.println("----------------------just02");

        // 打印的仍然但是 flowable , 而非字符串
        Flowable.just(Flowable.just("Hello world"))
                .subscribe(System.out::println);
    }

}
