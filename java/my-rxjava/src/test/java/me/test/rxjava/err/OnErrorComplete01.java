package me.test.rxjava.err;


import io.reactivex.rxjava3.core.Completable;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * https://github.com/ReactiveX/RxJava/wiki/Error-Handling-Operators#onerrorcomplete
 */
public class OnErrorComplete01 {



    @Test
    public void test() {


        Completable.fromAction(() -> {
            throw new IOException();
        })
                .onErrorComplete(error -> {
                    // Only ignore errors of type java.io.IOException.
                    return error instanceof IOException;
                })
                .subscribe(
                        () -> System.out.println("Done"),
                        err -> System.out.println("error : should not printed. : " + err)
                );
    }

}
