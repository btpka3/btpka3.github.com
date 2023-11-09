package me.test.first.spring.boot.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

/**
 * @author dangqian.zll
 * @date 2020/11/20
 */
public class RetryTest {

    protected String sayHi(String name) throws InterruptedException {
        if (name == null) {
            return "Hi, guest";
        }

        if (name.contains("Err")) {
            throw new IllegalArgumentException("Something is wrong");
        }

        Thread.sleep(101);

        return "Hi, " + name;
    }


    /**
     * 测试 是否可以 执行多个 doOnTerminate，以及他们的顺序
     */
    @Test
    public void test00() {

        Mono.just("aaa")
                .doOnTerminate(() -> System.out.println("doOnTerminate---1"))
                .doOnTerminate(() -> System.out.println("doOnTerminate---2"))
                .doOnError(e -> System.out.println(e))
                .subscribe((v) -> System.out.println("subscribe.v = " + v));
    }


    /**
     * @see <a href="https://projectreactor.io/docs/core/release/reference/#context">Project reactor : 9.8. Adding a Context to a Reactive Sequence</a>
     */
    @Test
    public void testContext01() {

        String key = "message";
        Mono<String> r = Mono
                .deferContextual(ctx -> Mono.just("Hello " + ctx.get(key)))
                .contextWrite(ctx -> ctx.put(key, "Reactor"))
                .flatMap(s -> Mono.deferContextual(ctx -> Mono.just(s + " " + ctx.get(key))))
                .contextWrite(ctx -> ctx.put(key, "World"));

        StepVerifier.create(r)
                .expectNext("Hello Reactor World")
                .verifyComplete();
    }


    @Test
    public void test02() {

        Mono<String> monoLogged = Mono.just("aaa")
                .transformDeferredContextual((original, ctx) -> {
                    System.out.println("for RequestID : " + ctx.get("RequestID"));
                    return original;
                });
        monoLogged.contextWrite(Context.of("RequestID", "requestA"))
                .subscribe();
        monoLogged.contextWrite(Context.of("RequestID", "requestB"))
                .subscribe();

    }

    @Test
    public void test01() {
        Mono m = Mono.fromCallable(() -> sayHi("zhang3"));
        m.doFinally(type -> {
            if (type == SignalType.ON_COMPLETE) {

            }
        });
    }
}
