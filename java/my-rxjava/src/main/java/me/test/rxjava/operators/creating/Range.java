package me.test.rxjava.operators.creating;

import io.reactivex.*;

/**
 *
 */
public class Range {


    public static void main(String[] args) {

        range01();

    }

    public static void range01() {
        System.out.println("----------------------just01");

        Flowable.range(0, 10)
                .subscribe(System.out::println);
    }
}
