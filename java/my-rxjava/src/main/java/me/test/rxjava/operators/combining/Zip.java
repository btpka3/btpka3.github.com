package me.test.rxjava.operators.combining;

import io.reactivex.*;

/**
 * http://reactivex.io/documentation/operators/zip.html
 */
public class Zip {

    public static void main(String[] args) {

        zip01();
        zipWith01();

    }

    public static void zip01() {
        System.out.println("----------------------zip01");

        // 从两个消息源中按顺序一一匹配，组合成一个新的消息源
        Flowable.fromArray("a", "b", "c")
                .zipWith(Flowable.fromArray(1, 2), (str, num) -> str + num)
                .subscribe(System.out::println);
    }

    public static void zipWith01() {
        System.out.println("----------------------zipWith01");

        // 从两个消息源中按顺序一一匹配，组合成一个新的消息源

        Flowable.zip(
                Flowable.fromArray("a", "b", "c", "d"),
                Flowable.fromArray("1", "2", "3"),
                Flowable.fromArray("A", "B", "C", "D", "E"),
                (a, n, A) -> "" + a + n + A
        ).subscribe(System.out::println);
    }
}
