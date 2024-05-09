package me.test.rxjava.observable.creating;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 *
 * http://reactivex.io/documentation/operators/interval.html
 */
public class Interval {


    @Test
    public void interval01() throws InterruptedException {


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
