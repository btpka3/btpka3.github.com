package com.github.btpka3.first.spring.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;

public class A {

    void x() {
        MeterRegistry reg = null;
        reg.counter("", "").count();
        reg.timer("", "").record(() -> {
        });

        Counter counter = Counter
                .builder("counter")
                .baseUnit("beans")
                .description("a description of what this counter does")
                .tags("region", "test")
                .register(reg);


        Metrics.counter("", "").increment();
    }
}
