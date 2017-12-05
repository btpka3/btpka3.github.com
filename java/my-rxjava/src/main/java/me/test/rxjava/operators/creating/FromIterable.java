package me.test.rxjava.operators.creating;

import io.reactivex.*;
import me.test.rxjava.*;
import org.reactivestreams.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 *
 */
public class FromIterable {


    public static void main(String[] args) throws IOException {

        //fromIterable01();
        walkFiles();

    }

    public static void fromIterable01() {
        System.out.println("----------------------fromIterable01");

        AtomicReference<Subscription> subscriptionRef = new AtomicReference<>();
        Flowable.fromIterable(Arrays.asList("AAA", "BBB", "CCC"))
                .doOnSubscribe(s -> {

                    U.print("doOnSubscribe ", s);
                    subscriptionRef.set(s);
                    s.request(1);
                })
                .subscribe(
                        i -> {
                            U.print("subscribe : onNext", i);
                            subscriptionRef.get().request(1);
                        },
                        err -> U.print("subscribe : onError", err),
                        () -> U.print("subscribe : onComplete", "")

                );
    }


    public static void walkFiles() throws IOException {
        System.out.println("----------------------walkFiles");
        Stream<Path> s = Files.walk(Paths.get("/tmp"), FileVisitOption.FOLLOW_LINKS);
        AtomicReference<Subscription> subscriptionRef = new AtomicReference<>();

        Flowable.fromIterable(s::iterator)
//                .buffer(1)

                .subscribe(
                        i -> {
                            U.print("subscribe : onNext", i);
                            subscriptionRef.get().request(1);
                        },
                        err -> U.print("subscribe : onError", err),
                        () -> U.print("subscribe : onComplete", "")

                );
    }
}
