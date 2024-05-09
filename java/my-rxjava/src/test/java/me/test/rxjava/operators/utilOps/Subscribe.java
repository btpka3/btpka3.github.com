package me.test.rxjava.operators.utilOps;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.test.rxjava.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * http://reactivex.io/documentation/operators/subscribe.html
 */
public class Subscribe {


    @Test
    public void subscribeWith01() throws InterruptedException {
        System.out.println("----------------------subscribeWith01");
        Disposable d = Observable.just("Hello world!")
                .delay(1, TimeUnit.SECONDS)
                .subscribeWith(new DisposableObserver<String>() {

                    @Override
                    public void onStart() {
                        U.print("onStart", "Start!");
                    }

                    @Override
                    public void onNext(String t) {
                        U.print("onNext", t);
                    }

                    @Override
                    public void onError(Throwable t) {
                        U.print("onError", t);
                    }

                    @Override
                    public void onComplete() {
                        U.print("onComplete", "Done!");
                    }
                });

        Thread.sleep(3000);
        // the sequence now can be cancelled via dispose()
        d.dispose();
    }


    // 所有消息订阅都是 main 线程上执行的。
    @Test
    public void subscribe1() throws InterruptedException {
        System.out.println("----------------------subscribe1");
        Flowable<Integer> f = Flowable.range(0, 5);


        f.subscribe(i -> U.print("s1.subscribe : ", i));
        f.subscribe(i -> U.print("s2.subscribe : ", i));
        //.subscribe(System.out::println);
        Thread.sleep(3000);
    }

    // 一个 subscribe 使用一个独立的 线程
    @Test
    public void subscribe2() throws InterruptedException {
        System.out.println("----------------------subscribe2");
        Flowable<Integer> f = Flowable.range(0, 5)
                .subscribeOn(Schedulers.computation());


        f.subscribe(i -> U.print("s1.subscribe : ", i));
        f.subscribe(i -> U.print("s2.subscribe : ", i));
        //.subscribe(System.out::println);
        Thread.sleep(3000);
    }


    // 无论多晚，只要 subscribe，订阅的消息都是从第一个消息开始。
    @Test
    public void subscribe3() throws InterruptedException {
        System.out.println("----------------------subscribe3");
        Flowable<Long> f = Flowable.interval(1, TimeUnit.SECONDS);

        f.subscribe(i -> U.print("s1.subscribe : ", i));


        Thread.sleep(3000);
        // 虽然3秒后才订阅，但也仍然是从第一个消息（0）开始
        f.subscribe(i -> U.print("s2.subscribe ----------- : ", i));
        Thread.sleep(8000);
    }

    // 两次订阅得到的消息都不一样
    @Test
    public void subscribe4() throws InterruptedException {
        System.out.println("----------------------subscribe4");

        Flowable<Integer> f = Flowable.range(1, 1000000)
                .sample(1, TimeUnit.MILLISECONDS);

        f.subscribe(i -> U.print("s1.subscribe", i));

        Thread.sleep(3000);
        f.subscribe(i -> U.print("s2.subscribe ================= ", i));
    }

}
