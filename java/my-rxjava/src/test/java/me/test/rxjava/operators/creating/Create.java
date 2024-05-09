package me.test.rxjava.operators.creating;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.test.rxjava.U;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Create {


    @Test
    public void create01() {
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

    @Test
    public void create02() {
        System.out.println("----------------------create02");


        long startTime = System.currentTimeMillis();

        Flowable<Callable> f0 = Flowable.create((e) -> {

            List<Integer> finishedTaskIds = new ArrayList<>();

            Runnable checkFinished = () -> {
                for (int i = 0; i < 3; i++) {
                    // 只要有一个没完成，就返回
                    if (!finishedTaskIds.contains(i)) {
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


    @Test
    /**
     * 按 3，7，11 的整倍数创建子流。
     */
    public void create03() {
        System.out.println("----------------------create03");

        long startTime = System.currentTimeMillis();

        AtomicInteger count3 = new AtomicInteger(0);
        AtomicInteger total3 = new AtomicInteger(0);
        AtomicReference<FlowableEmitter> emitterRef3 = new AtomicReference<>();
        Flowable.<Integer>create(e -> {
            U.print("3.create", e);
            emitterRef3.set(e);
        }, BackpressureStrategy.DROP)
                .doOnNext(i -> count3.addAndGet(1))
                .subscribe(i -> total3.addAndGet(i),
                        (e) -> U.print("3.error", e),
                        () -> U.print("3.complete", " count = " + count3.get() + ", total = " + total3.get())
                );

        AtomicInteger count7 = new AtomicInteger(0);
        AtomicInteger total7 = new AtomicInteger(0);
        AtomicReference<FlowableEmitter> emitterRef7 = new AtomicReference<>();
        Flowable.<Integer>create(e -> {
            U.print("7.create", e);
            emitterRef7.set(e);
        }, BackpressureStrategy.DROP)
                .doOnNext(i -> count7.addAndGet(1))
                .subscribe(i -> total7.addAndGet(i),
                        (e) -> U.print("7.error", e),
                        () -> U.print("7.complete", " count = " + count7.get() + ", total = " + total7.get())
                );

        AtomicInteger count11 = new AtomicInteger(0);
        AtomicInteger total11 = new AtomicInteger(0);
        AtomicReference<FlowableEmitter> emitterRef11 = new AtomicReference<>();
        Flowable.<Integer>create(e -> {
            U.print("11.create", e);
            emitterRef11.set(e);
        }, BackpressureStrategy.DROP)
                .doOnNext(i -> count11.addAndGet(1))
                .subscribe(i -> total11.addAndGet(i),
                        (e) -> U.print("11.error", e),
                        () -> U.print("11.complete", " count = " + count11.get() + ", total = " + total11.get())
                );

        Flowable.range(0, 1000)
                .subscribe((i) -> {
                            if (i % 3 == 0) {
                                emitterRef3.get().onNext(i);
                            } else if (i % 7 == 0) {
                                emitterRef7.get().onNext(i);
                            } else if (i % 11 == 0) {
                                emitterRef11.get().onNext(i);
                            }
                        },
                        (e) -> {
                            U.print("error", e);
                            emitterRef3.get().onError(e);
                            emitterRef7.get().onError(e);
                            emitterRef11.get().onError(e);
                        },
                        () -> {
                            U.print("complete", "");
                            emitterRef3.get().onComplete();
                            emitterRef7.get().onComplete();
                            emitterRef11.get().onComplete();
                        }
                );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
