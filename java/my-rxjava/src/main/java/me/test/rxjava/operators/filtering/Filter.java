package me.test.rxjava.operators.filtering;

import io.reactivex.*;

/**
 *
 */
public class Filter {


    public static void main(String[] args) {

        filter01();

    }


    public static void filter01() {
        System.out.println("----------------------filter01");

        Flowable.range(0, 9)
                .filter(i -> i % 3 != 0)
                .subscribe(System.out::println);
    }


}
