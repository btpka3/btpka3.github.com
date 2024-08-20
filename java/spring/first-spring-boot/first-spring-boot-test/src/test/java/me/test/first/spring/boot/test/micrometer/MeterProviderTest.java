package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.Data;

/**
 * @author dangqian.zll
 * @date 2024/8/5
 * @see <a href="https://docs.micrometer.io/micrometer/reference/concepts/meter-provider.html">Meter Provider</a>
 */
public class MeterProviderTest {

    MeterRegistry registry;
    private Meter.MeterProvider<Timer> timerProvider = Timer.builder("job.execution")
            .tag("job.name", "my-job")
            .withRegistry(registry);

    public void x() {
        Timer.Sample sample = Timer.start(registry);
        Job job = new Job();
        Result result = job.execute();

        sample.stop(timerProvider.withTags("status", result.getStatus()));
    }

    public static class Job {
        public Result execute() {
            return new Result();
        }

    }

    @Data
    public static class Result {
        private String status;
    }
}
