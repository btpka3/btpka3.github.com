package me.test.rxjava.operators.aggregate;

import io.reactivex.*;

/**
 *
 */
public class Reduce {


    public static void main(String[] args) {

        max();

    }


    public static void max() {
        System.out.println("----------------------max");

        Flowable.fromArray(1, 4, 3, 5, 9, 2)
                .reduce((i, r) -> {

                    System.out.println("\ti = " + i + ", r = " + r);
                    return Math.max(i, r);

                })
                .subscribe(System.out::println);
    }

    public static void max02() {
        System.out.println("----------------------max02");

        Flowable.fromArray(1, 4, 3, 5, 9, 2)
                .reduce(Math::max)
                .subscribe(System.out::println);
    }

    public static void sum() {
        System.out.println("----------------------sum");

        Flowable.fromArray(1, 4, 3, 5, 9, 2)
                .reduce((i, r) -> i + r)
                .subscribe(System.out::println);
    }


}
