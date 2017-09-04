package me.test.rxjava.operators.transforming;

import io.reactivex.*;
import io.reactivex.Observable;

import java.util.*;

/**
 * http://reactivex.io/documentation/operators/groupby.html
 */
public class GroupBy {


    public static void main(String[] args) {

        groupBy01();
        groupBy02();
        groupBy03();

    }


    public static void groupBy01() {
        System.out.println("----------------------groupBy01");
        Flowable.range(1, 9)

                // groupBy 之后，元素长度为3
                // 内部函数返回对应元素所属 group 的 key
                .groupBy(i -> i % 3)
                .flatMapSingle(Flowable::toList)
                .subscribe(System.out::println)
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


    // FIXME : 有问题， why？
    public static void groupBy03() {
        Observable.range(1, 9)
                .groupBy(i -> i % 3)
                .map(f -> Single.just(Collections.singletonMap(f.getKey(), f.toList().blockingGet())))
                .subscribe(System.out::println)
        ;

    }


}
