package me.test.rxjava.test;



import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * 自定义实现 ZIP 操作
 */
public class MyZipWith {


    public static void log(String msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }

    @Test
    public void main() throws InterruptedException {

        zipWith(
                Flowable.fromArray("1", "2", "3", "4", "5"),
                Flowable.fromArray("A", "B", "C", "D")
        ).subscribe(
                s -> log("=========~~~~~~~~~~ subscribe : " + s),
                s -> log("=========~~~~~~~~~~ onError : " + s),
                () -> log("=========~~~~~~~~~~ onComplete")
        );

        // Expected : 1A, 2B, 3C, 4D
        Thread.sleep(5000);
    }


    public static Flowable<String> zipWith(Flowable<String> r, Flowable<String> l) {

        return Flowable.<String>create(e -> {
            BlockingDeque<String> r_q = new LinkedBlockingDeque<>();
            BlockingDeque<String> l_q = new LinkedBlockingDeque<>();

            AtomicReference<Throwable> r_err = new AtomicReference<>();
            AtomicReference<Throwable> l_err = new AtomicReference<>();

            AtomicBoolean r_complete = new AtomicBoolean(false);
            AtomicBoolean l_complete = new AtomicBoolean(false);

            Object o = new Object();

            r
                    .observeOn(Schedulers.newThread())
                    .subscribe(

                            (s) -> {
                                log("--------- r : r_q.add : " + s);
                                r_q.add(s);
                                synchronized (o) {
                                    o.notifyAll();
                                }
                            },
                            (err) -> {
                                log("--------- r : onError : " + e);
                                r_err.set(err);
                                synchronized (o) {
                                    o.notifyAll();
                                }

                            },
                            () -> {
                                log("--------- r : onComplete");
                                r_complete.set(true);
                                synchronized (o) {
                                    o.notifyAll();
                                }

                            }
                    );

            l
                    .observeOn(Schedulers.newThread())
                    .subscribe(
                            (s) -> {
                                l_q.add(s);
                                log("--------- l : l_q.add : " + s);
                                synchronized (o) {
                                    o.notifyAll();
                                }

                            },
                            (err) -> {
                                e.onError(err);
                                log("--------- l : onError : " + e);
                                l_err.set(err);
                                synchronized (o) {
                                    o.notifyAll();
                                }

                            },
                            () -> {
                                log("--------- l : onComplete");
                                l_complete.set(true);
                                synchronized (o) {
                                    o.notifyAll();
                                }
                            }
                    );

            while (true) {
                try {
                    synchronized (o) {
                        o.wait();
                    }
                } catch (InterruptedException err) {
                }


                // 其中至少一个队列中还没有消息？
                while (r_q.peek() != null && l_q.peek() != null) {

                    // 每条队列中都至少有一个消息了，好，开始拼接
                    String s = r_q.take() + l_q.take();
                    log("---- onNext : " + s);
                    e.onNext(s);
                }


                Throwable t = r_err.get() != null
                        ? r_err.get()
                        : l_err.get() != null ? l_err.get() : null;

                // 任何一个队列已经出错了？
                if (t != null) {
                    log("---- onError : " + t);
                    Thread.sleep(1000);
//                    e.onError(t);
                    break;
                }

                // 任何一个队列已经结束了？
                if (r_complete.get() || l_complete.get()) {
                    log("---- onComplete");
                    e.onComplete();
                    break;
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread())
                ;
    }
}
