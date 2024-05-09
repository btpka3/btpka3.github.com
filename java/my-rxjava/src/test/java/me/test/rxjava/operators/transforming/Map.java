package me.test.rxjava.operators.transforming;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class Map {

    @Test
    public  void map01() {
        System.out.println("----------------------map01");

        Flowable.range(0, 3)
                .map(i -> i * 2)
                .subscribe(System.out::println);
    }

    @Test
    public void mapWithIndex01() {
        System.out.println("----------------------mapWithIndex01");

        Flowable.range(0, 3)
                //.compose(Transformers.mapWithIndex())
                .subscribe(System.out::println);
    }


}
