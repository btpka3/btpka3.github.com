package me.test.reactor;

import reactor.core.publisher.FluxSink;

/**
 * @date 2018-11-03
 */
public class F {

    public static <T> void print(FluxSink<T> fluxSink) {
        System.out.println(" currentContext=" + fluxSink.currentContext()
                + ", isCancelled=" + fluxSink.isCancelled()
                + ", requestedFromDownstream=" + fluxSink.requestedFromDownstream()
        );
    }
}
