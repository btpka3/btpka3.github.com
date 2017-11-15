package me.test.rxjava.operators.creating;

import io.reactivex.*;
import io.reactivex.parallel.*;
import io.reactivex.schedulers.*;
import me.test.rxjava.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

public class Create {


    public static void main(String[] args) {

        create02();

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


    public static void create02() {
        System.out.println("----------------------create02");


        long startTime = System.currentTimeMillis();

        Flowable<Callable> f0 = Flowable.create((e) -> {

            List<Integer> finishedTaskIds = new ArrayList<>();

            Runnable checkFinished = ()->{
                for (int i = 0; i < 3; i++) {
                    // 只要有一个没完成，就返回
                    if(!finishedTaskIds.contains(i)){
                        return;
                    }
                    e.onComplete();
                }
            };
            for (int i = 0; i < 3; i++) {
                int n = i;

                e.onNext((Callable) () -> {
                    Thread.sleep(2000);
                    U.print("f0.onNext : ", n);
                    finishedTaskIds.add(n);
                    checkFinished.run();
                    return "A-" + n;
                });
            }
            //e.onComplete(); // FIXME 如何告知 onComplete
        }, BackpressureStrategy.BUFFER);

        ParallelFlowable.from(f0)
                .runOn(Schedulers.computation())
                .map(Callable::call)
                .sequential()
                .subscribe(
                        (i) -> {
                            long endTime = System.currentTimeMillis();
                            Duration duration = Duration.ofMillis(endTime - startTime);
                            System.out.println(Thread.currentThread().getName()
                                    + ": subscribe : i = " + i
                                    + ", duration = " + duration);

                        },
                        Throwable::printStackTrace,
                        () -> U.print("f1.onComplete", ""));

        try {
            Thread.sleep(8000);  // <--- wait for the flow to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        U.print("done.", "");
    }
}
