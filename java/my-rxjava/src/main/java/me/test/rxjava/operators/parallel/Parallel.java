package me.test.rxjava.operators.parallel;

import io.reactivex.*;
import io.reactivex.schedulers.*;
import me.test.rxjava.*;
import org.reactivestreams.*;

public class Parallel {
    public static void main(String[] args) throws InterruptedException {

        parallel1();

    }


    //
    public static void parallel1() throws InterruptedException {
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
