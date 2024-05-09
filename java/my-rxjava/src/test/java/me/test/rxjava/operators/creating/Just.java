package me.test.rxjava.operators.creating;


import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

/**
 * http://reactivex.io/documentation/operators/just.html
 */
public class Just {


    @Test
    public void just01() {
        System.out.println("----------------------just01");

        Flowable.just("Hello world")
                .subscribe(System.out::println);
    }

    @Test
    public void just02() {
        System.out.println("----------------------just02");

        // 打印的仍然但是 flowable , 而非字符串
        Flowable.just(Flowable.just("Hello world"))
                .subscribe(System.out::println);
    }

}
