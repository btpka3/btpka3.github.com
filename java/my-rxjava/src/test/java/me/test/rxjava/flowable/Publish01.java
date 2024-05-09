package me.test.rxjava.flowable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import me.test.rxjava.U;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dangqian.zll
 * @date 2019-08-12
 */

public class Publish01 {

    Logger logger = LoggerFactory.getLogger(Publish01.class);


    @Test
    public void test01() throws InterruptedException {
        logger.info("test01 started");


        Flowable<Long> f0 = Flowable.generate(() -> 0L, (state, emitter) -> {
            if (state >= 10) {
                emitter.onComplete();
                return state;
            }
            emitter.onNext(state);
            Thread.sleep(500);
            Long i = state + 1;
            U.print("emitter : onNext", i);
            return i;

        });
        ConnectableFlowable<Long> f1 = f0.publish();


        f1.subscribe(
                i -> {
                    U.print("subscribe1 : onNext", i);
                },
                err -> U.print("subscribe1 : onError", err),
                () -> U.print("subscribe1 : onComplete", "")
        );


        f1.subscribe(
                i -> {
                    U.print("subscribe2 : onNext", i);
                },
                err -> U.print("subscribe2 : onError", err),
                () -> U.print("subscribe2 : onComplete", "")
        );


        f1.connect();


        Thread.sleep(1500);
        f1.subscribe(
                i -> {
                    U.print("subscribe3 : onNext", i);
                },
                err -> U.print("subscribe3 : onError", err),
                () -> U.print("subscribe3 : onComplete", "")
        );

    }


    /**
     * FIXME flowable.publish() -> Completable 会阻塞。 why?
     *
     * @throws InterruptedException
     */

    @Test
    public void test02() throws InterruptedException {
        logger.info("test01 started");


        Flowable<Long> f0 = Flowable.generate(() -> 0L, (state, emitter) -> {
            if (state >= 10) {
                emitter.onComplete();

                U.print("emitter : onComplete", "");
                return state;
            }
            emitter.onNext(state);
            Thread.sleep(500);
            Long i = state + 1;
            U.print("emitter : onNext", i);
            return i;

        });
        ConnectableFlowable<Long> f1 = f0.publish();

        Completable c1 = f1.flatMapCompletable((i) -> {
            U.print("subscribe1 : onNext", i);

            Completable c = Completable.complete();
            return c;
        });
        c1.subscribe(
                () -> U.print("c1.subscribe : onComplete", ""),
                err -> U.print("c1.subscribe : onError", err)

        );


        Completable c2 = f1.flatMapCompletable((i) -> {
            U.print("subscribe2 : onNext", i);
            return Completable.complete();
        });

        //Completable.fromPublisher(f1);


        f1.connect();


        Completable c = Completable.mergeArray(
                c1,
                c2
        );
        // FIXME ： 这里有问题
//        c.blockingAwait();
        U.print("Done.", "");

    }
}
