package me.test.rxjava.operators.creating;


import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class Range {


    @Test
    public void range01() {
        System.out.println("----------------------just01");

        Flowable.range(0, 10)
                .subscribe(System.out::println);
    }
}
