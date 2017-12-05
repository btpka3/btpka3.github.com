package me.test.rxjava.test;

import io.reactivex.*;
import io.reactivex.internal.operators.flowable.*;
import io.reactivex.schedulers.*;
import me.test.rxjava.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

// FIXME: 如何自己实现一个？
public class Hot {


    public static void main(String[] args) throws InterruptedException {

        AtomicBoolean stop = new AtomicBoolean();
        AtomicInteger event = new AtomicInteger();
        AtomicReference<FlowableEmitter> emitterRef = new AtomicReference<>();

        // 模拟独立的消息源，按照固有频率发送消息。
        Runnable mockHotEventSource = () -> {
            while (!stop.get()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    emitterRef.get().onError(e);
                    break;
                }
                if (emitterRef.get() == null) {
                    continue;
                }

                Integer i = event.addAndGet(1);
                emitterRef.get().onNext(i);
                if (i > 20) {
                    break;
                }

            }
            emitterRef.get().onComplete();

        };

        AtomicReference<Thread> ttt = new AtomicReference<>();
        Flowable<Integer> f = Flowable.<Integer>create(e -> {
            U.print("create", e);
            emitterRef.set(e);

        }, BackpressureStrategy.DROP)
                .doOnSubscribe(s -> {
                    U.print("doOnSubscribe", s);
                    synchronized (ttt) {
                        if (ttt.get() == null) {
                            Thread t = new Thread(mockHotEventSource);
                            ttt.set(t);
                            t.start();
                        }
                    }
                });


        myConnect(f)
                .observeOn(Schedulers.newThread())
                .subscribe(
                        i -> U.print("f1.subscribe : onNext", i),
                        err -> U.print("f1.subscribe : onError", err),
                        () -> U.print("f1.subscribe : onComplete", "")
                );

        Thread.sleep(3000);

        myConnect(f)
                .observeOn(Schedulers.newThread())
                .subscribe(
                        i -> U.print("f2222222222222222222222.subscribe : onNext", i),
                        err -> U.print("f2222222222222222222222.subscribe : onError", err),
                        () -> U.print("f2222222222222222222222.subscribe : onComplete", "")
                );
        Thread.sleep(10000);
    }

    public static Flowable<Integer> myConnect(Flowable<Integer> f) {

        BlockingDeque<Integer> q = new LinkedBlockingDeque<>( );
//        AtomicReference<Throwable>

        AtomicReference<FlowableEmitter<Integer>> emitterRef  =new AtomicReference<>();
        f.subscribe(
                i-> q.add(i),
                err->emitterRef.get().onError(err),
                ()->emitterRef.get().onComplete()
        );

        FlowableCreate<Integer> fc= new FlowableCreate< >(e -> {
//             while(true){
//                 e.onNext(q);
//             }
            emitterRef.set(e);
        }, BackpressureStrategy.BUFFER);

        Flowable<Integer> newBufferedFlow = Flowable.create(e -> {
            emitterRef.set(e);
        }, BackpressureStrategy.BUFFER);

        return newBufferedFlow;
    }
}
