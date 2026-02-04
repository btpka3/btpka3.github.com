package me.test.org.openjdk.jmh.x;

import java.io.IOException;
import org.openjdk.jmh.Main;

/**
 * 列出支持的
 *
 * @author dangqian.zll
 * @date 2025/5/6
 */
public class MyJmhMain {
    public static void main(String[] args) throws IOException {
        Main.main(new String[] {"-lprof"});

        /* 示例输出如下：
        Supported profilers:
                  cl: Classloader profiling via standard MBeans
                comp: JIT compiler profiling via standard MBeans
                  gc: GC profiling via standard MBeans
                 jfr: Java Flight Recorder profiler
             mempool: Memory pool/footprint profiling via standard MBeans
              pauses: Pauses profiler
          safepoints: Safepoints profiler
               stack: Simple and naive Java stack profiler

        Unsupported profilers:
               async: <none>
        Unable to load async-profiler. Ensure asyncProfiler library is on LD_LIBRARY_PATH (Linux), DYLD_LIBRARY_PATH (Mac OS), or -Djava.library.path. Alternatively, point to explicit library location with -prof async:libPath=<path>.
           dtraceasm: <none>
        [sudo: a password is required
        ]
                perf: <none>
        [Cannot run program "perf": error=2, No such file or directory]
             perfasm: <none>
        [Cannot run program "perf": error=2, No such file or directory]
             perfc2c: <none>
        [Cannot run program "perf": error=2, No such file or directory]
            perfnorm: <none>
        [Cannot run program "perf": error=2, No such file or directory]
            xperfasm: <none>
        Unable to start the profiler. Try running JMH as Administrator, and ensure that previous profiling session is stopped. Use 'xperf -stop' to stop the active profiling session.: [Cannot run program "xperf": error=2, No such file or directory]
                 */
    }
}
