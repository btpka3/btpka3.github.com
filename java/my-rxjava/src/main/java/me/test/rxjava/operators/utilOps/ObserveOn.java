package me.test.rxjava.operators.utilOps;

import io.reactivex.*;
import me.test.rxjava.*;

/**
 * observeOn作用于该操作符之后操作符直到出现新的observeOn操作符
 *
 *  文档 : http://reactivex.io/documentation/operators/observeon.html
 *  图片 : http://reactivex.io/documentation/operators/images/schedulers.png
 *  其他参考 : http://www.jianshu.com/p/59c3d6bb6a6b
 *
 *
 */
public class ObserveOn {

    public static void main(String[] args) {

        observeOn01();

    }


    public static void observeOn01() {
        System.out.println("----------------------observeOn01");
        Flowable.create(e -> {

            // 观察者/Subscriber 在 当前线程 执行
            for (int i = 0; i < 4; i++) {
                U.print("create", i);
                e.onNext(i);
            }
        }, BackpressureStrategy.DROP)

                // 让 观察者/Subscriber 在 指定的 Scheduler/线程 中执行后续操作
                .observeOn(U.getNamedScheduler("aaaa"))
                .map(i -> {
                    U.print("map-1", i);
                    return i;
                })

                // 让 观察者/Subscriber 在 指定的 Scheduler/线程 中执行后续操作
                .observeOn(U.getNamedScheduler("XXXXXXX"))
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


}
