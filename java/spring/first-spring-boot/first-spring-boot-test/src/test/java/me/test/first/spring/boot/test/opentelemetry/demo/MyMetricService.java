package me.test.first.spring.boot.test.opentelemetry.demo;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.Meter;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author dangqian.zll
 * @date 2024/4/8
 */
@Component
public class MyMetricService implements Runnable {

    OpenTelemetry openTelemetry;
    Meter meter;


    public MyMetricService(OpenTelemetry openTelemetry) {
        this.openTelemetry = openTelemetry;
        this.meter = openTelemetry.getMeter("my.metric");
    }

    public void run() {
        gauge();
        longCounter();
        histogram();
        randomException();
    }

    protected void gauge() {

        Attributes MY_ATTR1 = Attributes.of(
                AttributeKey.longKey("my.demo.attr2.key"),
                111L
        );
        meter
                .gaugeBuilder("my.jvm.memory.total")
                .setDescription("My Reports JVM memory usage.")
                .setUnit("byte")
                .buildWithCallback(result -> result.record(Runtime.getRuntime().totalMemory(), MY_ATTR1));
    }

    protected void longCounter() {
        LongCounter longCounter = meter
                .counterBuilder("directories_search_count")
                .setDescription("Counts directories accessed while searching for files.")
                .setUnit("unit")
                .build();
        Attributes MY_ATTR2 = Attributes.of(
                AttributeKey.stringKey("my.demo.attr1.key"),
                "my.demo.att1.value"
        );

        longCounter.add(RandomUtils.nextLong(1, 100), MY_ATTR2);
    }

    protected void histogram() {
        LongHistogram histogram = meter
                .histogramBuilder("people.ages")
                .ofLongs() // Required to get a LongHistogram, default is DoubleHistogram
                .setDescription("A distribution of people's ages")
                .setUnit("years")
                .build();
        Attributes MY_ATTR3 = Attributes.of(
                AttributeKey.booleanKey("my.demo.attr3.key1"), true,
                AttributeKey.stringArrayKey("my.demo.attr3.key2"),
                Arrays.asList("my.demo.attr3.value1", "my.demo.attr3.value2")
        );
        IntStream.range(0, 1000).forEach(x -> histogram.record(RandomUtils.nextLong(1, 115), MY_ATTR3));
    }

    protected void randomException() {
        if (RandomUtils.nextBoolean()) {
            throw new RuntimeException("demo exception");
        }
    }

}
