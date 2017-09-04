package me.test.rxjava.operators.combining;

import io.reactivex.*;
import io.reactivex.schedulers.*;

import java.time.*;
import java.util.*;

/**
 *
 *
 *
 * http://reactivex.io/documentation/operators/switch.html
 */
public class Switch {


    public static void main(String[] args) {

        switch01();

    }


    public static void switch01() {
        System.out.println("----------------------switch01");

        Flowable.fromArray("a", "b")
                .zipWith(Flowable.fromArray(1, 2), (str, num) -> str + num)
                .subscribe(System.out::println);
    }

}
