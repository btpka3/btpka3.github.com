package me.test.rxjava.operators.parallel;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.test.rxjava.*;
import org.junit.jupiter.api.Test;
import org.reactivestreams.*;

public class Parallel {
    @Test
    public void parallel1() throws InterruptedException {
        System.out.println("----------------------parallel1");

        Flowable.fromArray("AA", "BB", "CC")
                .parallel()
                .runOn(Schedulers.computation())
                .map(i -> {
                    U.print("f1.map", i);
                    return i + "-1";
                })
                .sequential()
                .subscribe(i -> U.print("f0.subscribe", i))
        ;
        Thread.sleep(3000);


    }

    public static class MySubscriber<T> implements Subscriber<T> {

        @Override
        public void onSubscribe(Subscription s) {
            U.print("" + this + " : onSubscribe", s);
        }

        @Override
        public void onNext(Object o) {
            U.print("" + this + " : onNext", o);
        }

        @Override
        public void onError(Throwable t) {
            U.print("" + this + " : onError", t);
        }

        @Override
        public void onComplete() {
            U.print("" + this + " : onComplete", "");
        }
    }
}
