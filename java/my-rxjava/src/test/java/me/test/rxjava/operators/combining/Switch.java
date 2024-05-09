package me.test.rxjava.operators.combining;


import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;

/**
 *
 *
 *
 * http://reactivex.io/documentation/operators/switch.html
 */
public class Switch {

    @Test
    public void switch01() {
        System.out.println("----------------------switch01");

        Flowable.fromArray("a", "b")
                .zipWith(Flowable.fromArray(1, 2), (str, num) -> str + num)
                .subscribe(System.out::println);
    }

}
