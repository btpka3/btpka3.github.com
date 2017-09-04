package me.test.rxjava.operators.transforming;

import io.reactivex.*;

/**
 *
 */
public class Map {


    public static void main(String[] args) {

        map01();

    }


    public static void map01() {
        System.out.println("----------------------map01");

        Flowable.range(0, 3)
                .map(i -> i * 2)
                .subscribe(System.out::println);
    }


}
