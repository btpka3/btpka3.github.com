package me.test.rxjava.operators.filtering;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class Filter {


    @Test
    public void filter01() {
        System.out.println("----------------------filter01");

        Flowable.range(0, 9)
                .filter(i -> i % 3 != 0)
                .subscribe(System.out::println);
    }


}
