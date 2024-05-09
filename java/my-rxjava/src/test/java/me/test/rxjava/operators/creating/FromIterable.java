package me.test.rxjava.operators.creating;

import io.reactivex.rxjava3.core.Flowable;
import me.test.rxjava.*;
import org.junit.jupiter.api.Test;
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


    @Test
    public void fromIterable01() {
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

    @Test
    public void walkFiles() throws IOException {
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
