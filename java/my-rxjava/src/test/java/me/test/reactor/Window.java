package me.test.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;

/**
 *
 */
public class Window {

    @Test
    public void test01() {
        Flux.fromIterable(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
                .window(3)
                .subscribe(f -> {
                    f.collectList().subscribe(list -> System.out.println("list : " + list));
                });
    }

    @Test
    public void test02() {
        Flux.fromIterable(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
                .window(2, 3)
                .subscribe(f -> {
                    f.collectList().subscribe(list -> System.out.println("list : " + list));
                });
    }

}
