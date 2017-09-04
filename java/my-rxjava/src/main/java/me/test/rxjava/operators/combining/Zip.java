package me.test.rxjava.operators.combining;

import io.reactivex.*;

/**
 * http://reactivex.io/documentation/operators/zip.html
 */
public class Zip {

    public static void main(String[] args) {

        zip01();

    }

    public static void zip01() {
        System.out.println("----------------------zip01");

        // 从两个消息源中按顺序一一匹配，组合成一个新的消息源
        Flowable.fromArray("a", "b")
                .zipWith(Flowable.fromArray(1, 2), (str, num) -> str + num)
                .subscribe(System.out::println);
    }
}
