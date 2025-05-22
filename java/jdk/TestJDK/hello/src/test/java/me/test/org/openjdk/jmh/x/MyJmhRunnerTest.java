package me.test.org.openjdk.jmh.x;

import me.test.org.openjdk.jmh.MyJmhBenchmarkTest;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.CommandLineOptionException;
import org.openjdk.jmh.runner.options.CommandLineOptions;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author dangqian.zll
 * @date 2025/5/6
 * @see CommandLineOptions
 */
public class MyJmhRunnerTest {
    public static void main(String[] args) throws RunnerException, CommandLineOptionException {
        System.setProperty("jmh.blackhole.autoDetect", "false");

        Options opt = new OptionsBuilder()
                .build();
        CommandLineOptions commandLineOptions = new CommandLineOptions("-lprof");
        new Runner(opt).listWithParams(commandLineOptions);
    }
}
