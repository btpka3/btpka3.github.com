package me.test.rxjava.operators.transforming;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.test.rxjava.*;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 *
 */
public class ToList {



    @Test
    public void toList01() {
        System.out.println("----------------------toList01");

        List<Integer> intList = Flowable.fromArray(1, 2, 3)
                .map(i -> i * 2)
                .toList()
                .blockingGet();
        System.out.println(intList);
    }

    @Test
    public void toList02() throws InterruptedException {
        System.out.println("----------------------toList02");
        Flowable.range(1, 9)
                .groupBy(i -> i % 3)
                .parallel()  // FIXME: Observable 没有该方法
                .runOn(Schedulers.computation())
                .flatMap(f -> {

                    // 如果使用 blockingGet() : 因为所有操作都会在同一个线程上执行。而没有 complete，所以会阻塞。
                    U.print("flatMap : ", f);


                    return Flowable.just(Collections.singletonMap(f.getKey(), f.toList().blockingGet()));
                })
                //.sequential()
                .sorted((m1, m2) -> Objects.compare(
                        m1.keySet().stream().findFirst().get(),
                        m2.keySet().stream().findFirst().get(),
                        Comparator.naturalOrder()
                ))
                .subscribe(
                        i -> U.print("subscribe : onNext", i),
                        err -> U.print("subscribe : onError", err),
                        () -> U.print("subscribe : onComplete", "")
                )
        ;

        Thread.sleep(1000);

    }


}
