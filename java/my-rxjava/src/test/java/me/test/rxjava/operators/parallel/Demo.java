package me.test.rxjava.operators.parallel;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * List<Integer>              // [1..9]
 * ->
 * Map<Integer,List<Integer> //  {0: [3,6,9],  1: [1,4,7],  2: [2,5,8] }
 */
public class Demo {

    @Test
    public void byCollect() throws InterruptedException {
        System.out.println("----------------------byCollect");
        Observable.range(1, 9)
                .groupBy(i -> i % 3)
                .flatMapSingle(f -> {  // GroupedObservable<Integer, List<Integer>>

                    // Look output : all runs on same thread,
                    print("flatMapSingle : ", f.getKey());

                    // "onComplete" has not been triggered.
                    // blockingGet will block current thread.
                    //return Observable.just(Collections.singletonMap(f.getKey(), f.toList().blockingGet()))

                    return f.collect(
                            // (Callable<Map<Integer, List<Integer>>>)
                            () -> Collections.singletonMap(f.getKey(), new ArrayList<Integer>()),

                            // (BiConsumer<Map<Integer, List<Integer>>, Integer>)
                            (m, i) -> m.get(f.getKey()).add(i)
                    );

                })
                .subscribe(
                        i -> print("subscribe : onNext", i),
                        err -> print("subscribe : onError", err),
                        () -> print("subscribe : onComplete", "")
                )
        ;
    }

    @Test
    public void byParallelAndBlockingGet() throws InterruptedException {
        System.out.println("----------------------byParallelAndBlockingGet");
        Flowable.range(1, 9)
                .groupBy(i -> i % 3)

                // There's no `parallel` method on `Observable` class
                .parallel()

                // Important!!!
                .runOn(Schedulers.computation())

                // ParallelFlowable<GroupedFlowable<Integer, List<Integer>>
                .flatMap(f -> {

                    // Look output : runs on different thread each.
                    print("flatMap : ", f.getKey());
                    return Flowable.just(Collections.singletonMap(f.getKey(), f.toList().blockingGet()));
                })
                .sequential()
                .subscribe(
                        i -> print("subscribe : onNext", i),
                        err -> print("subscribe : onError", err),
                        () -> print("subscribe : onComplete", "")
                )
        ;
        Thread.sleep(500);
    }

    public static void print(String step, Object data) {
        ZonedDateTime zdt = ZonedDateTime.now();
        String now = zdt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS z"));
        System.out.printf("[%s][%4d - %30s] - %10s : %s%n",
                now,
                Thread.currentThread().getId(),
                Thread.currentThread().getName(),
                step,
                data
        );
    }
}
