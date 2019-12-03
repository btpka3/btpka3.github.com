package me.test.first.spring.boot.test;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.*;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dangqian.zll
 * @date 2019-09-28
 * @see <a href="https://micrometer.io/docs/concepts#_meters">micrometer</a>
 */
public class MetricsTest {


    public void counter01() {

        MeterRegistry registry = null;
        Counter counter = registry.counter("http.requests", "uri", "/api/users");
        counter.increment();
    }


    @Timed(extraTags = {"region", "us-east-1"})
    public void timer01() {

        MeterRegistry registry = null;
        Timer timer = registry.timer("xx", "aaa");
        timer.record(() -> System.out.println(11));


        Timer.builder("my.timer")
                .publishPercentiles(0.5, 0.95) // median and 95th percentile
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10));

    }


    public void gauge01() throws NoSuchAlgorithmException {

        MeterRegistry registry = null;

        List<String> list = registry.gauge("listGauge", Collections.emptyList(), new ArrayList<>(), List::size);

        List<String> list2 = registry.gaugeCollectionSize("listSize2", Tags.empty(), new ArrayList<>());

        Map<String, Integer> map = registry.gaugeMapSize("mapGauge", Tags.empty(), new HashMap<>());

        AtomicInteger n = registry.gauge("numberGauge", new AtomicInteger(0));
        n.set(1);
    }

    public void timer0x1() {

        MeterRegistry registry = null;

        DistributionSummary summary0 = DistributionSummary
                .builder("response.size")
                .description("a description of what this summary does") // optional
                .baseUnit("bytes") // optional (1)
                .tags("region", "test") // optional
                .scale(100) // optional (2)
                .register(registry);

        DistributionSummary summary1 = registry.summary("response.size");
    }
}
