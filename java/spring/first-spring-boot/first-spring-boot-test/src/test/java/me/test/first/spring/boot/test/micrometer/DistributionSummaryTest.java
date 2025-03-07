package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author dangqian.zll
 * @date 2024/3/28
 */
public class DistributionSummaryTest extends BaseMetricsTest{

    @Resource(name="loggingMeterRegistry")
    MeterRegistry registry;


    @Test
    public void test01() throws InterruptedException {


        DistributionSummary distributionSummary = DistributionSummary
                .builder("request.size")
                .description("a description of xxx")
                .baseUnit("bytes")
                .tags("region", "test")
                .scale(1.0)

                // 本地直接计算的 百分比（不可再聚合）
                .publishPercentiles(0.3, 0.5, 0.9)

                // 百分比直方图：远端可 与其他实例，按tag 汇总聚合
                .publishPercentileHistogram()
                .minimumExpectedValue(1.0)
                .maximumExpectedValue(100.0)

                .serviceLevelObjectives(  0.95)

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

        Thread.sleep(10000);
    }
}
