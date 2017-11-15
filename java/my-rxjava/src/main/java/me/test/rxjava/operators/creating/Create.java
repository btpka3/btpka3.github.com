package me.test.rxjava.operators.creating;

import io.reactivex.*;
import io.reactivex.schedulers.*;

import java.time.*;

public class Create {


    public static void main(String[] args) {

        create01();

    }


    public static void create01() {
        System.out.println("----------------------create01");

        long startTime = System.currentTimeMillis();
        Flowable.create((e) -> {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(2000);
                e.onNext("A-" + i);
            }
            e.onComplete();
        }, BackpressureStrategy.BUFFER)
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
            Thread.sleep(8000);  // <--- wait for the flow to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
