package me.test.rxjava.operators.utilOps;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.test.rxjava.*;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class SubscribeOn {


    @Test
    public void subscribeOn01() {

        System.out.println("----------------------subscribeOn01");

        Flowable.create(e -> {

            // 消息的创建操作，本应在当前线程上执行，但是因为 subscribeOn 会在 "aaaa" 调度器上执行
            for (int i = 0; i < 4; i++) {
                U.print("create", i);
                e.onNext(i);
            }

        }, BackpressureStrategy.DROP)

                // 让 观察者/Subscriber 在 指定的 Scheduler/线程 中执行 ：
                // (1) 消息创建的操作 —— 也就是 上面的 create 操作
                //（2）后续操作
                .subscribeOn(U.getNamedScheduler("aaaa"))
                .map(i -> {
                    U.print("map-1", i);
                    return i;
                })

                // 让 观察者/Subscriber 在 指定的 Scheduler/线程 中执行后续操作
                // 但是因为 subscribeOn
                .subscribeOn(U.getNamedScheduler("XXXXXXX"))
                .map(i -> {
                    U.print("map-2", i);
                    return i;
                })

                .subscribe(i -> U.print("subscribe", i));


        try {
            Thread.sleep(3000); // 让其他线程执行完。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // SubscribeOn 与 ObserveOn 混合使用
    @Test
    public void subscribeOn02() {

        System.out.println("----------------------subscribeOn02");


        Flowable.range(0, 4)

                // 在当前观察者/线程中执行
                .map(i -> {
                    U.print("map-1", i);
                    return i;
                })

                // 让 io 调度器来观察当前这个 Flowable/Publisher
                .observeOn(Schedulers.newThread())

//
//                // 后续的操作均在 在一个新的观察者/线程中执行
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe((t) -> print("doOnSubscribe-1", t))
                .map(i -> {
                    U.print("map-2", i);
                    return i;
                })
//
//                // 后续的操作均在 在一个新的观察者/线程中执行
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe((t) -> print("doOnSubscribe-2", t))
//                .map(i -> {
//                    print("map-3", i);
//                    return i;
//                })
//
//                .filter(i -> {
//                    print("filter", i);
//                    return i % 3 != 0;
//                })
                //
                .doOnSubscribe((t) -> U.print("doOnSub-1", t))
                .doOnSubscribe((t) -> U.print("doOnSub-2", t))

                // 影响该语句之前的 doOnSubscribe
                .subscribeOn(U.getNamedScheduler("S1"))
                .doOnSubscribe((t) -> U.print("doOnSub-3", t))


                .map(i -> {
                    U.print("map-3", i);
                    return i;
                })
                // 当
                .subscribeOn(U.getNamedScheduler("S2"))

                .subscribe(i -> {
                    U.print("subscribe", i);
                });


        try {
            Thread.sleep(3000); // 让其他线程执行完。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
