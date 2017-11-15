package me.test.rxjava.observable.creating;

import io.reactivex.*;
import io.reactivex.observers.*;
import io.reactivex.schedulers.*;

import java.util.concurrent.*;

/**
 *
 * http://reactivex.io/documentation/operators/interval.html
 */
public class Interval {


    public static void main(String[] args) throws InterruptedException {

        interval01();

    }


    public static void interval01() throws InterruptedException {


        System.out.println("----------------------interval01");

        Observable<Long> o = Observable.interval(1, TimeUnit.SECONDS);
        o.observeOn(Schedulers.newThread())
                .safeSubscribe(new DefaultObserver<Long>() {

                    @Override
                    public void onComplete() {
                        System.out.println("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("error: " + e);
                    }

                    @Override
                    public void onNext(Long args) {
                        System.out.println("AAAA: " + args);
                    }
                });

        Thread.sleep(5000);

    }


}
