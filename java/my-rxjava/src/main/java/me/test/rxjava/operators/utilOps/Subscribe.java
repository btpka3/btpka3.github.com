package me.test.rxjava.operators.utilOps;

import io.reactivex.*;
import io.reactivex.disposables.*;
import io.reactivex.observers.*;

import java.util.concurrent.*;

/**
 * http://reactivex.io/documentation/operators/subscribe.html
 */
public class Subscribe {


    public static void main(String[] args) throws InterruptedException {

        subscribeWith01();

    }

    public static void subscribeWith01() throws InterruptedException {
        System.out.println("----------------------subscribeWith01");
        Disposable d = Observable.just("Hello world!")
                .delay(1, TimeUnit.SECONDS)
                .subscribeWith(new DisposableObserver<String>() {

                    @Override
                    public void onStart() {
                        System.out.println("Start!");
                    }

                    @Override
                    public void onNext(String t) {
                        System.out.println(t);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Done!");
                    }
                });

        Thread.sleep(3000);
        // the sequence now can be cancelled via dispose()
        d.dispose();
    }


}
