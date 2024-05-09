package me.test.rxjava.operators.combining;


import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

/**
 * http://reactivex.io/documentation/operators/zip.html
 */
public class Zip {


    @Test
    public void zip01() {
        System.out.println("----------------------zip01");

        // 从两个消息源中按顺序一一匹配，组合成一个新的消息源
        Flowable.fromArray("a", "b", "c")
                .zipWith(Flowable.fromArray(1, 2), (str, num) -> str + num)
                .subscribe(System.out::println);
    }

    @Test
    public void zipWith01() {
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
