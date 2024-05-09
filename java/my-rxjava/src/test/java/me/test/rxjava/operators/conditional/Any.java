package me.test.rxjava.operators.conditional;


import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class Any {


    @Test
    public void any() {
        System.out.println("----------------------any");

        boolean hasGt5Ele = Flowable.fromArray(1, 4, 3, 5, 9, 2)
                .any(i -> i > 4)
                .blockingGet();

        System.out.println(hasGt5Ele);
    }


}
