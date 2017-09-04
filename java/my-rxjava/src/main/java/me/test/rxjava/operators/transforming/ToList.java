package me.test.rxjava.operators.transforming;

import io.reactivex.*;

import java.util.*;

/**
 *
 */
public class ToList {


    public static void main(String[] args) {

        toList01();

    }


    public static void toList01() {
        System.out.println("----------------------toList01");

        List<Integer> intList = Flowable.fromArray(1, 2, 3)
                .map(i -> i * 2)
                .toList()
                .blockingGet();
        System.out.println(intList);
    }


}
