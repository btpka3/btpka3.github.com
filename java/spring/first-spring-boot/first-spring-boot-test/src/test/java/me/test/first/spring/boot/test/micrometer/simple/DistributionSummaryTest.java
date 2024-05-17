package me.test.first.spring.boot.test.micrometer.simple;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.distribution.ValueAtPercentile;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dangqian.zll
 * @date 2024/5/15
 * @see <a href="https://www.baeldung.com/micrometer">Quick Guide to Micrometer</a>
 */
public class DistributionSummaryTest {

    @Test
    public void test01() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        DistributionSummary distributionSummary = DistributionSummary
                .builder("request.size")
                // 本地直接计算的 百分比（不可再聚合）
                .publishPercentiles(0.3, 0.5, 0.9)

                // 百分比直方图：远端可 与其他实例，按tag 汇总聚合
                .publishPercentileHistogram()
                .minimumExpectedValue(1.0)
                .maximumExpectedValue(100.0)

                .serviceLevelObjectives(  0.95)
                .baseUnit("bytes")
                .register(registry);

        distributionSummary.record(21);
        distributionSummary.record(42);
        distributionSummary.record(73);
        distributionSummary.record(74);
        distributionSummary.record(85);
        distributionSummary.record(95);
        distributionSummary.record(96);
        distributionSummary.record(97);
        distributionSummary.record(98);
        distributionSummary.record(99);

        assertEquals(10, distributionSummary.count());
        assertEquals(780, distributionSummary.totalAmount());
        assertEquals(99, distributionSummary.max());
        assertEquals(78, distributionSummary.mean());

        ValueAtPercentile[] percentiles = distributionSummary.takeSnapshot().percentileValues();

        for (int i = 0; i < percentiles.length; i++) {
            System.out.println(" i=" + i + " : " + percentiles[i].percentile() + "," + percentiles[i].value());
        }


    }
}
