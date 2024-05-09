package me.test.rxjava.operators.aggregate;


import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class Reduce {


    @Test
    public void max() {
        System.out.println("----------------------max");

        Flowable.fromArray(1, 4, 3, 5, 9, 2)
                .reduce((i, r) -> {

                    System.out.println("\ti = " + i + ", r = " + r);
                    return Math.max(i, r);

                })
                .subscribe(System.out::println);
    }

    @Test
    public void max02() {
        System.out.println("----------------------max02");

        Flowable.fromArray(1, 4, 3, 5, 9, 2)
                .reduce(Math::max)
                .subscribe(System.out::println);
    }

    @Test
    public void sum() {
        System.out.println("----------------------sum");

        Flowable.fromArray(1, 4, 3, 5, 9, 2)
                .reduce((i, r) -> i + r)
                .subscribe(System.out::println);
    }


}
