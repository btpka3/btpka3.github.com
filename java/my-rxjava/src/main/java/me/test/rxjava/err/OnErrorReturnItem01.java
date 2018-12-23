package me.test.rxjava.err;

import io.reactivex.Flowable;

/**
 * https://github.com/ReactiveX/RxJava/wiki/Error-Handling-Operators#onerrorresumenext
 */
public class OnErrorReturnItem01 {


    public static void main(String[] args) throws InterruptedException {

        test();

    }


    public static void test() throws InterruptedException {


        Flowable.range(0, 10)
                .map(i -> {
                    if (i == 4) {
                        throw new RuntimeException("MockError");
                    }
                    return "a" + i;
                })
                .onErrorReturnItem("bbb")
                .subscribe(
                        d -> System.out.println("data : " + d),
                        err -> System.out.println("error : should not printed. : " + err),
                        () -> System.out.println("Done")
                );
    }

}
