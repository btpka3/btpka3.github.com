package me.test.rxjava.observable.combining;

import io.reactivex.*;
import io.reactivex.functions.*;
import io.reactivex.schedulers.*;

import java.util.concurrent.*;

/**
 *
 * http://reactivex.io/documentation/operators/merge.html
 */
public class Merge {


    public static void main(String[] args) throws InterruptedException {

        merge04();

    }


    public static void merge01() throws InterruptedException {


        // https://stackoverflow.com/questions/38903094/concat-vs-merge-operaror
        // if the sources are synchronous then then merge = concat
        System.out.println("----------------------merge01");
        Observable<Integer> odds = Observable.just(1, 3, 5).subscribeOn(Schedulers.newThread());
        Observable<Integer> evens = Observable.just(2, 4, 6).subscribeOn(Schedulers.newThread());

        Observable.merge(odds, evens)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("accept: " + integer);
                    }
                });
        Thread.sleep(5000);
    }

    public static void merge02() throws InterruptedException {


        System.out.println("----------------------merge02");

        Observable.merge(
                Observable.interval(1, TimeUnit.SECONDS).map(id -> "A" + id).subscribeOn(Schedulers.newThread()),
                Observable.interval(1, TimeUnit.SECONDS).map(id -> "B" + id))
                .subscribe(System.out::println);


        Thread.sleep(5000);
    }


    public static void merge03() throws InterruptedException {


        System.out.println("----------------------merge03");
        Observable<String> odds = Observable.<String>create((e) -> {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(3000);
                e.onNext("A-" + i);
            }
        });//.subscribeOn(Schedulers.newThread());

        Observable<String> evens = Observable.<String>create((e) -> {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                e.onNext("BBBBB-" + i);
            }
        });//.subscribeOn(Schedulers.newThread());

        Observable.merge(odds, evens)
                .subscribe(System.out::println);


//
//        Thread.sleep(5000);
    }


    public static void merge04() throws InterruptedException {


        System.out.println("----------------------merge04");
        Observable<String> odds = Observable.<String>create((e) -> {

            new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 3; i++) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e1) {
                            throw new RuntimeException(e1);
                        }
                        e.onNext("A-" + i);
                    }
                }
            }.start();

        });//.subscribeOn(Schedulers.newThread());

        Observable<String> evens = Observable.<String>create((e) -> {

            new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            throw new RuntimeException(e1);
                        }
                        e.onNext("BBBBB-" + i);
                    }
                }
            }.start();
        });//.subscribeOn(Schedulers.newThread());

        Observable.merge(odds, evens)
                .subscribe(System.out::println);


//
//        Thread.sleep(5000);
    }

}
