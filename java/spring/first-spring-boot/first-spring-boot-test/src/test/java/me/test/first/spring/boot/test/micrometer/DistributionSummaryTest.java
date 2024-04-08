package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * @author dangqian.zll
 * @date 2024/3/28
 */
public class DistributionSummaryTest {

    DistributionSummary distributionSummary;
    MeterRegistry registry;

    public void x(){
        DistributionSummary summary = DistributionSummary
                .builder("response.size")
                //.maximumExpectedValue(100.0)
                .description("a description of what this summary does") // optional
                .baseUnit("bytes") // optional (1)
                .tags("region", "test") // optional
                .scale(100) // optional (2)
                .sla(70, 80, 90)
                .register(registry);
    }
}
