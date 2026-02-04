package me.test.org.openjdk.jmh;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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
@State(Scope.Benchmark)
public class MyJmhBenchmarkTest {

    /**
     * 要共享的数据。
     * - Scope.Benchmark : 按照【Benchmark】级别共享实例对象，参考： {@link Benchmark} 。适用于多线程下的性能测试。
     * - Scope.Group : 按照【线程组】级别共享实例对象，参考： {@link GroupThreads}
     * - Scope.Thread : 按照【线程】级别共享实例对象，参考： {@link Threads}
     */
    @State(Scope.Benchmark)
    public static class SharedParam {
        public String str = "hello";
    }

    /**
     * Level.Tiral : 运行每个性能测试的时候执行。推荐
     * Level.Iteration : 运行每次迭代的时候执行。
     * Level.Invocation : 运行方法调用的时候执行。慎用
     */
    @Setup(Level.Trial)
    public void jmhSetUp() {}

    @TearDown(Level.Trial)
    public void jmhTearDown() {}

    @Benchmark
    public void testStringKey(SharedParam sharedParam) {
        // 优化前的代码
    }

    @Benchmark
    public void testObjectKey(SharedParam sharedParam) {
        // 要测试的优化后代码
    }

    public static void main(String[] args) throws RunnerException {
        long startTime = System.currentTimeMillis();
        Options opt = new OptionsBuilder()
                .include(MyJmhBenchmarkTest.class.getSimpleName())
                .jvmArgs("-Xmx1g", "-Xms1g")
                .addProfiler("gc")
                .param("-lprof")
                .build();
        new Runner(opt).run();
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) + "ms");
    }
}
