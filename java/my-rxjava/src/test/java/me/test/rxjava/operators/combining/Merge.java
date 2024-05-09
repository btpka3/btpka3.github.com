package me.test.rxjava.operators.combining;


import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.time.*;

/**
 *
 * http://reactivex.io/documentation/operators/merge.html
 */
public class Merge {


    @Test
    public void merge01() {


        System.out.println("----------------------merge01");
        long startTime = System.currentTimeMillis();

        // https://stackoverflow.com/questions/38903094/concat-vs-merge-operaror
        // if the sources are synchronous then then merge = concat

        Flowable.merge(
                Flowable.just("AA"),
                Flowable.fromCallable(() -> {
                    Thread.sleep(3000); //  imitate expensive computation
                    return "BB";
                }),
                Flowable.just("CC")
        )
                // `BB` 消息的产生 会 阻塞 "CC" 消息的产生。
                // 请参考 merge02() 使用异步消息解决。
                .observeOn(Schedulers.newThread())
                .subscribe((i) -> {
                    long endTime = System.currentTimeMillis();
                    Duration duration = Duration.ofMillis(endTime - startTime);
                    System.out.println(Thread.currentThread().getName()
                            + ": subscribe : i = " + i
                            + ", duration = " + duration);

                }, Throwable::printStackTrace);
    }


    @Test
    public void merge02() {


        System.out.println("----------------------merge02");
        long startTime = System.currentTimeMillis();


        Flowable.merge(
                Flowable.just("AA"),
                Flowable.create((e) -> {

                    // 独立的线程上单独执行的
                    new Thread() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                throw new RuntimeException(e1);
                            }
                            e.onNext("BB");

                        }
                    }.start();

                }, BackpressureStrategy.BUFFER),

                Flowable.just("CC")
        )
                // 因为 是在单个线程上、顺序 observe 的，所以 `BB` 消息的产生 会 阻塞 "CC" 消息的产生。
                .observeOn(Schedulers.newThread())
                .subscribe((i) -> {
                    long endTime = System.currentTimeMillis();
                    Duration duration = Duration.ofMillis(endTime - startTime);
                    System.out.println(Thread.currentThread().getName()
                            + ": subscribe : i = " + i
                            + ", duration = " + duration);

                }, Throwable::printStackTrace);
    }


}
