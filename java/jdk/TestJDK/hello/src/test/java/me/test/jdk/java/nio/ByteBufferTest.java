package me.test.jdk.java.nio;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * -XX:MaxDirectMemorySize 如果不设置，则等于 -Xmx 的值。
 * 如何定位？
 * - java debug 断点
 * - 通过java-agent 替换 ByteBuffer.allocateDirect 方法，并增加输出的调用堆栈
 * - 使用 Java Mission Control (JMC) 打开记录文件，搜索 BufferAllocation 事件，查看调用栈。
 * - 通过字节码操作工具（如 AspectJ、Byte Buddy 或 Javassist）在运行时动态插入监控代码
 * <p>
 * jmc追踪 ： sun.nio.ch.Util.BufferPool
 *
 * @author dangqian.zll
 * @date 2025/5/7
 * @see <a href="https://blog.csdn.net/A_saying/article/details/145659269">堆外内存暗杀事件：ByteBuffer.allocateDirect()引发的OOM之谜</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/tooldescr007.html">Native Memory Tracking</a>
 * @see <a href="https://github.com/async-profiler/async-profiler/blob/master/docs/ProfilingModes.md#native-memory-leaks">Profiling Modes</a>
 * @see java.nio.DirectByteBuffer
 * @see java.nio.Bits#MAX_MEMORY
 * @see java.nio.Bits#RESERVED_MEMORY
 * @see jdk.internal.misc.VM#maxDirectMemory()
 */
public class ByteBufferTest {

    @Test
    public void allocateDirect01() {


        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
    }

    public static ByteBuffer allocateDirect(int capacity) {
        StackTraceElement[] a = Thread.currentThread().getStackTrace();
        StackTraceElement[] b = Thread.currentThread().getStackTrace();
        return ByteBuffer.allocateDirect(capacity);
    }
}
