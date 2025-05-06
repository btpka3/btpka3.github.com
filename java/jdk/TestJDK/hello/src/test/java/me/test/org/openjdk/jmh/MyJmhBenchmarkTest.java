package me.test.org.openjdk.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author dangqian.zll
 * @date 2025/5/6
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.SECONDS)
public class MyJmhBenchmarkTest {
    @Benchmark
    public static void testStringKey() {
        //优化前的代码
    }

    @Benchmark
    public static void testObjectKey() {
        //要测试的优化后代码
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyJmhBenchmarkTest.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}