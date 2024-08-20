package me.test.first.spring.boot.test.micrometer;

import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.instrument.distribution.pause.PauseDetector;

import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

/**
 * @author dangqian.zll
 * @date 2024/8/2
 */
public class MyMeterRegistry extends MeterRegistry {

    public MyMeterRegistry(Clock clock) {
        super(clock);
    }

    @Override
    protected <T> Gauge newGauge(Meter.Id id, T obj, ToDoubleFunction<T> valueFunction) {
        return null;
    }

    @Override
    protected Counter newCounter(Meter.Id id) {
        return null;
    }

    @Override
    protected Timer newTimer(Meter.Id id, DistributionStatisticConfig distributionStatisticConfig, PauseDetector pauseDetector) {
        return null;
    }

    @Override
    protected DistributionSummary newDistributionSummary(Meter.Id id, DistributionStatisticConfig distributionStatisticConfig, double scale) {
        return null;
    }

    @Override
    protected Meter newMeter(Meter.Id id, Meter.Type type, Iterable<Measurement> measurements) {
        return null;
    }

    @Override
    protected <T> FunctionTimer newFunctionTimer(Meter.Id id, T obj, ToLongFunction<T> countFunction, ToDoubleFunction<T> totalTimeFunction, TimeUnit totalTimeFunctionUnit) {
        return null;
    }

    @Override
    protected <T> FunctionCounter newFunctionCounter(Meter.Id id, T obj, ToDoubleFunction<T> countFunction) {
        return null;
    }

    @Override
    protected TimeUnit getBaseTimeUnit() {
        return null;
    }

    @Override
    protected DistributionStatisticConfig defaultHistogramConfig() {
        return null;
    }
}
