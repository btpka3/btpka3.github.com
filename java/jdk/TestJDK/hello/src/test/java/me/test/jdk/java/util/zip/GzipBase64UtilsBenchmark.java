package me.test.jdk.java.util.zip;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
public class GzipBase64UtilsBenchmark {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(GzipBase64UtilsBenchmark.class.getSimpleName())
                .jvmArgs("-Xmx128m", "-Xms128m")
                .forks(1)
                // .param("-lprof")
                // 启用 GC 分析
                .addProfiler("gc")
                .build();
        new Runner(opt).run();
    }

    @Setup(Level.Trial)
    public void setUp() {
        // 成员变量的初始化可以放到这里（有状态）。
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testGetGzipBase64FromStr() {
        String src = "hello world1234567890AliAliAlibaba111222333444555";
        GzipBase64Utils.getGzipBase64FromStr(src);
    }
}
