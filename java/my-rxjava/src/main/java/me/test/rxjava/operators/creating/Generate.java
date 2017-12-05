package me.test.rxjava.operators.creating;

import io.reactivex.*;
import me.test.rxjava.*;
import org.reactivestreams.*;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Generate {


    public static void main(String[] args) {

        generate02();

    }


    public static void generate01() {
        System.out.println("----------------------generate01");

        Flowable.generate(() -> 0L, (state, emitter) -> {
            emitter.onNext(state);
            return state + 1;

        })
                .subscribe(
                        i -> {
                            U.print("subscribe : onNext", i);
                        },
                        err -> U.print("subscribe : onError", err),
                        () -> U.print("subscribe : onComplete", "")
                );

    }

    public static void generate02() {
        System.out.println("----------------------generate02");

        PrimitiveIterator.OfInt it = IntStream.range(1, 10).iterator();
        AtomicReference<Subscription> sRef = new AtomicReference<>();

        Flowable.generate(() -> it, (iterator, emitter) -> {

            // 注意：这里的 emitter 不是 FlowableEmitter, 因此没有 requested 方法。
            if (iterator.hasNext()) {
                emitter.onNext(iterator.next().toString());
            } else {
                emitter.onComplete();
            }
            //U.print("generate", emitter.getClass());
            return iterator;
        })
                .doOnSubscribe(s -> {
                    sRef.set(s);
                    s.request(1); // 明确指定 请求几个，否则会按照默认的来。
                })
                .subscribe(
                        i -> {
                            U.print("subscribe : onNext", i);
                            sRef.get().request(1);
                        },
                        err -> U.print("subscribe : onError", err),
                        () -> U.print("subscribe : onComplete", "")
                );

    }
}
