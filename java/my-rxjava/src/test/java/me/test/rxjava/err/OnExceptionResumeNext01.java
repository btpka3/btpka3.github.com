package me.test.rxjava.err;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

/**
 * https://github.com/ReactiveX/RxJava/wiki/Error-Handling-Operators#onerrorresumenext
 */
public class OnExceptionResumeNext01 {


    @Test
    public void test() throws InterruptedException {


        Flowable.range(0, 10)
                .map(i -> {
                    if (i == 4) {
                        throw new RuntimeException("MockError");
                    }
                    return "a" + i;
                })
                .onErrorResumeNext(e->Flowable.fromArray("ERR_"))
                .subscribe(
                        d -> System.out.println("data : " + d),
                        err -> System.out.println("error : should not printed. : " + err),
                        () -> System.out.println("Done")
                );
    }

}
