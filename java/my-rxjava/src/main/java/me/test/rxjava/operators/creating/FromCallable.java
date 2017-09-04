package me.test.rxjava.operators.creating;

import io.reactivex.*;
import io.reactivex.schedulers.*;

import java.time.*;

/**
 *
 */
public class FromCallable {


    public static void main(String[] args) {

        callable01();

    }



    public static void callable01() {
        System.out.println("----------------------callable01");

        long startTime = System.currentTimeMillis();
        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return 3;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe((i) -> {
                    long endTime = System.currentTimeMillis();
                    Duration duration = Duration.ofMillis(endTime - startTime);
                    System.out.println(Thread.currentThread().getName()
                            + ": subscribe : i = " + i
                            + ", duration = " + duration);

                }, Throwable::printStackTrace);

        try {
            Thread.sleep(2000);  // <--- wait for the flow to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
