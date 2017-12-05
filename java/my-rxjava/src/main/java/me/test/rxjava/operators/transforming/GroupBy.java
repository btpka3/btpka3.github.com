package me.test.rxjava.operators.transforming;

import io.reactivex.*;
import io.reactivex.Observable;
import io.reactivex.schedulers.*;
import me.test.rxjava.*;

import java.util.*;

/**
 *
 * 注意：Flowable<GroupedFlowable<K, T>> ： 内部的 GroupedFlowable 没有订阅完成，则外部的 Flowable将不会完成。
 * http://reactivex.io/documentation/operators/groupby.html
 */
public class GroupBy {


    public static void main(String[] args) throws InterruptedException {

        groupBy01();
        groupBy02();
        groupBy03();
//        groupBy00();
        groupBy04();

    }

    public static void groupBy00() {
        System.out.println("----------------------groupBy00");
        Flowable.range(1, 9)

                // groupBy 之后，元素长度为3
                // 内部函数返回对应元素所属 group 的 key
                .groupBy(i -> i % 3)

                .subscribe(
                        i -> U.print("subscribe : onNext", i),
                        err -> U.print("subscribe : onError", err),
                        () -> U.print("subscribe : onComplete", "")
                )
        ;

//        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//                .groupBy(i -> i % 2 == 0)
//                .subscribe(
//                        i -> U.print("subscribe : onNext", i),
//                        err -> U.print("subscribe : onError", err),
//                        () -> U.print("subscribe : onComplete", "")
//                );

    }

    public static void groupBy01() {
        System.out.println("----------------------groupBy01");
        Flowable.range(1, 9)

                // groupBy 之后，元素长度为3
                // 内部函数返回对应元素所属 group 的 key
                .groupBy(i -> i % 3)
                .flatMapSingle(Flowable::toList)
                .subscribe(
                        i -> U.print("subscribe : onNext", i),
                        err -> U.print("subscribe : onError", err),
                        () -> U.print("subscribe : onComplete", "")
                )
        ;

    }


    // copy from `RxGroovy groupBy` demo
    public static void groupBy02() {
        System.out.println("----------------------groupBy02");
        Flowable.range(1, 9)

                // groupBy 之后，元素长度为3
                // 内部函数返回对应元素所属 group 的 key
                .groupBy(i -> i % 3)
                .flatMapSingle(f -> {
                    List<Integer> list = new ArrayList<>();
                    list.add(f.getKey());
                    return f.reduce(list,
                            (a, b) -> {
                                a.add(b);
                                return a;
                            }
                    );
                })
                .subscribe(System.out::println)
        ;

    }


    /**
     * 目的：将 [1..9] 收集为以下 Map：
     * {
     *     0: [3,6,9],
     *     1: [1,4,7],
     *     2: [2,5,8],
     * }
     *
     * 发现 toList().blockingGet() 会阻塞，不行。 FIXME : 有问题， why？
     * 但是使用 Observable#collect() 则 OK。
     */
    public static void groupBy03() throws InterruptedException {
        System.out.println("----------------------groupBy03");
        Observable.range(1, 9)
                .groupBy(i -> i % 3)
                .flatMapSingle(f -> {

                    // 如果使用 blockingGet() : 因为所有操作都会在同一个线程上执行。而没有 complete，所以会阻塞。
                    U.print("flatMapSingle : ", f);

                    return f.collect(
                            // (Callable<java.util.Map<Integer, List<Integer>>>)
                            () -> {
                                java.util.Map<Integer, List<Integer>> m = Collections.singletonMap(f.getKey(), new ArrayList<Integer>());
                                return m;
                            },
                            // (BiConsumer<java.util.Map<Integer, List<Integer>>, Integer>)
                            (m, i) -> {
                                m.get(f.getKey()).add(i);

                            }
                    );
                    //return Observable.just(Collections.singletonMap(f.getKey(), f.toList().blockingGet()))
                })
                .subscribe(
                        i -> U.print("subscribe : onNext", i),
                        err -> U.print("subscribe : onError", err),
                        () -> U.print("subscribe : onComplete", "")
                )
        ;


    }


    public static void groupBy04() throws InterruptedException {
        System.out.println("----------------------groupBy04");
        Flowable.range(1, 9)
                .groupBy(i -> i % 3)
                .parallel()  // FIXME: Observable 没有该方法
                .runOn(Schedulers.computation())
                .flatMap(f -> {

                    // 如果使用 blockingGet() : 因为所有操作都会在同一个线程上执行。而没有 complete，所以会阻塞。
                    U.print("flatMap : ", f);

//                    return f.collect(
//                            // (Callable<java.util.Map<Integer, List<Integer>>>)
//                            () -> {
//                                java.util.Map<Integer, List<Integer>> m = Collections.singletonMap(f.getKey(), new ArrayList<Integer>());
//                                return m;
//                            },
//                            // (BiConsumer<java.util.Map<Integer, List<Integer>>, Integer>)
//                            (m, i) -> {
//                                m.get(f.getKey()).add(i);
//
//                            }
//                    ).toFlowable();
                    return Flowable.just(Collections.singletonMap(f.getKey(), f.toList().blockingGet()));
                })
                .sequential()
                .subscribe(
                        i -> U.print("subscribe : onNext", i),
                        err -> U.print("subscribe : onError", err),
                        () -> U.print("subscribe : onComplete", "")
                )
        ;

        Thread.sleep(1000);

    }

}
