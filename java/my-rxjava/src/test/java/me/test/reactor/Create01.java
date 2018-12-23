package me.test.reactor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * @date 2018-11-03
 */
public class Create01 {

    Logger log = LoggerFactory.getLogger(Create01.class);

    @Test
    public void test01() {

        Flux.create(f -> {

            String k1 = "reactor.onNextError.localStrategy";
            f.currentContext().hasKey(k1);

            f.onCancel(() -> {
                System.out.println("fluxSink#onCancel is invoked.");
            });
            f.onDispose(() -> {
                System.out.println("fluxSink#onDispose is invoked.");
            });

            long l = 0;

            f.onRequest(consumer -> {
                System.out.println("fluxSink#onRequest is invoked. consumer=" + consumer);
                // consumer.accept(l);
            });


            AtomicLong requestCount = new AtomicLong();


            for (int i = 0; i < 10; i++) {


                System.out.println("~~~ context=" + f.currentContext() + ", " + f.requestedFromDownstream());

                if (i == 5) {

//                    f.currentContext().get(OnNextFailureStrategy.KEY_ON_NEXT_ERROR_STRATEGY)
                    Exception err = new RuntimeException("MockError_" + i);
                    final Integer errVal = i;


                    Context ctx = f.currentContext();

                    // OnNextFailureStrategy
                    boolean shouldContinue = ctx.getOrEmpty(k1)
                            .map(x -> {
                                BiPredicate<Throwable, Integer> test = (BiPredicate<Throwable, Integer>) x;
                                BiFunction<Throwable, Object, Throwable> errHandler = (BiFunction<Throwable, Object, Throwable>) x;
                                if (test.test(err, errVal)) {

                                    // FIXME 返回值 如何处理？
                                    errHandler.apply(err, errVal);
                                    return true;
                                }
                                return false;
                            })
                            .orElse(false);

                    if (!shouldContinue) {
                        f.error(err);
                        return;
                    }
                }

                f.next(i);
            }
            f.complete();
        })
                .onErrorContinue((err, data) -> {
                    System.out.println("~~~~EEEEEEEEEEEEEE err=" + err + ", data = " + data);
                })
//                .log()

                .subscribe(
                        d -> log.info("data = " + d),
                        e -> log.error(e.getMessage(), e),
                        () -> System.out.println("Done.")
                );
    }

    static void print() {

    }
}


