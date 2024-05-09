package me.test.rxjava.operators.transforming;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class WindowTest {

    @Test
    public void test01() {
        System.out.println("----------------------test01");

        Flowable.fromArray(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .window(3)
                .subscribe(f ->
                        f.toList()
                                .subscribe(list -> {
                                    System.out.println("list = " + list);
                                })


                );
    }

    @Test
    public void test02() {
        System.out.println("----------------------test02");

        Flowable.fromArray(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .window(2, 3)
                .subscribe(f ->
                        f.toList()
                                .subscribe(list -> {
                                    System.out.println("list = " + list);
                                })
                );
    }
}
