package me.test.jdk.java.lang.foreign;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.IntUnaryOperator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Upcall 示例：展示如何将 Java 方法作为回调传递给 C 函数
 * 
 * @author dangqian.zll
 * @date 2025/5/7
 * @see java.lang.foreign.Linker
 */
@Slf4j
public class UpcallTest {

    /**
     * 示例：创建简单的 Upcall Handle
     * 演示如何将 Java 方法转换为可被 C 调用的函数指针
     */
    @Test
    public void createUpcallHandle() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        // 1. 定义 Java 方法
        MethodHandle javaMethod = MethodHandles.lookup().findStatic(
            UpcallTest.class,
            "javaCallback",
            MethodType.methodType(int.class, int.class)
        );
        
        // 2. 定义函数描述符
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT
        );
        
        // 3. 创建 Upcall Handle
        MemorySegment upcallStub = linker.upcallStub(
            javaMethod,
            descriptor,
            Arena.global()
        );
        
        log.info("✓ Upcall Handle 创建成功:");
        log.info("  Java 方法: javaCallback(int)");
        log.info("  函数指针地址: 0x{}", Long.toHexString(upcallStub.address()));
        log.info("  这个指针可以传递给 C 函数作为回调");
    }

    /**
     * Java 回调方法：将输入值乘以 2
     */
    private static int javaCallback(int input) {
        int result = input * 2;
        log.info("  → Java 回调被调用: 输入={}, 输出={}", input, result);
        return result;
    }

    /**
     * 示例：使用函数式接口创建回调
     */
    @Test
    public void functionalInterfaceCallback() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        // 1. 创建函数式接口的 MethodHandle
        IntUnaryOperator operator = x -> x * x; // 平方
        
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(
            IntUnaryOperator.class,
            "applyAsInt",
            MethodType.methodType(int.class, int.class)
        ).bindTo(operator);
        
        // 2. 定义函数描述符
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT
        );
        
        // 3. 创建 Upcall Stub
        MemorySegment upcallStub = linker.upcallStub(
            methodHandle,
            descriptor,
            Arena.global()
        );
        
        log.info("✓ 函数式接口回调:");
        log.info("  Lambda: x -> x * x");
        log.info("  函数指针: 0x{}", Long.toHexString(upcallStub.address()));
    }

    /**
     * 示例：带多个参数的回调
     */
    @Test
    public void multiArgumentCallback() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        // 1. 定义 Java 方法
        MethodHandle methodHandle = MethodHandles.lookup().findStatic(
            UpcallTest.class,
            "addNumbers",
            MethodType.methodType(int.class, int.class, int.class)
        );
        
        // 2. 定义函数描述符
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,    // 返回 int
            ValueLayout.JAVA_INT,    // 参数1
            ValueLayout.JAVA_INT     // 参数2
        );
        
        // 3. 创建 Upcall Stub
        MemorySegment upcallStub = linker.upcallStub(
            methodHandle,
            descriptor,
            Arena.global()
        );
        
        log.info("✓ 多参数回调:");
        log.info("  方法: addNumbers(int, int)");
        log.info("  函数指针: 0x{}", Long.toHexString(upcallStub.address()));
    }

    /**
     * Java 方法：两个数相加
     */
    private static int addNumbers(int a, int b) {
        int sum = a + b;
        log.info("  → addNumbers({}, {}) = {}", a, b, sum);
        return sum;
    }

    /**
     * 示例：带指针参数的回调
     */
    @Test
    public void pointerArgumentCallback() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        // 1. 定义 Java 方法
        MethodHandle methodHandle = MethodHandles.lookup().findStatic(
            UpcallTest.class,
            "processPointer",
            MethodType.methodType(void.class, MemorySegment.class)
        );
        
        // 2. 定义函数描述符
        FunctionDescriptor descriptor = FunctionDescriptor.ofVoid(
            ValueLayout.ADDRESS  // MemorySegment 参数
        );
        
        // 3. 创建 Upcall Stub
        MemorySegment upcallStub = linker.upcallStub(
            methodHandle,
            descriptor,
            Arena.global()
        );
        
        log.info("✓ 带指针参数的回调:");
        log.info("  方法: processPointer(MemorySegment)");
        log.info("  函数指针: 0x{}", Long.toHexString(upcallStub.address()));
    }

    /**
     * Java 方法：处理指针参数
     */
    private static void processPointer(MemorySegment segment) {
        log.info("  → processPointer 被调用");
        log.info("    指针地址: 0x{}", Long.toHexString(segment.address()));
        log.info("    指针大小: {} 字节", segment.byteSize());
    }

    /**
     * 示例：使用匿名类作为回调
     */
    @Test
    public void anonymousClassCallback() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        // 1. 创建回调实现
        class CallbackImpl {
            public int execute(int value) {
                int result = value + 100;
                log.info("  → CallbackImpl.execute({}) = {}", value, result);
                return result;
            }
        }
        
        CallbackImpl callback = new CallbackImpl();
        
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(
            CallbackImpl.class,
            "execute",
            MethodType.methodType(int.class, int.class)
        ).bindTo(callback);
        
        // 2. 定义函数描述符
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT
        );
        
        // 3. 创建 Upcall Stub
        MemorySegment upcallStub = linker.upcallStub(
            methodHandle,
            descriptor,
            Arena.global()
        );
        
        log.info("✓ 匿名类回调:");
        log.info("  类: CallbackImpl");
        log.info("  方法: execute(int)");
        log.info("  函数指针: 0x{}", Long.toHexString(upcallStub.address()));
    }

    /**
     * 示例：不同 Arena 中的 Upcall Stub
     */
    @Test
    public void upcallInDifferentArenas() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        MethodHandle methodHandle = MethodHandles.lookup().findStatic(
            UpcallTest.class,
            "javaCallback",
            MethodType.methodType(int.class, int.class)
        );
        
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT
        );
        
        log.info("✓ 不同 Arena 中的 Upcall Stub:");
        
        // 1. 在 Global Arena 中创建
        MemorySegment globalStub = linker.upcallStub(
            methodHandle,
            descriptor,
            Arena.global()
        );
        log.info("  Global Arena Stub: 0x{}", Long.toHexString(globalStub.address()));
        
        // 2. 在 Confined Arena 中创建
        try (Arena confinedArena = Arena.ofConfined()) {
            MemorySegment confinedStub = linker.upcallStub(
                methodHandle,
                descriptor,
                confinedArena
            );
            log.info("  Confined Arena Stub: 0x{}", Long.toHexString(confinedStub.address()));
            log.info("  注意: Confined Arena 关闭后，Stub 将失效");
        }
        
        // 3. 在 Shared Arena 中创建
        Arena sharedArena = Arena.ofShared();
        try {
            MemorySegment sharedStub = linker.upcallStub(
                methodHandle,
                descriptor,
                sharedArena
            );
            log.info("  Shared Arena Stub: 0x{}", Long.toHexString(sharedStub.address()));
            log.info("  注意: Shared Arena 可以跨线程使用");
        } finally {
            sharedArena.close();
        }
    }

    /**
     * 示例：Upcall 的生命周期管理
     */
    @Test
    public void upcallLifecycle() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        MethodHandle methodHandle = MethodHandles.lookup().findStatic(
            UpcallTest.class,
            "javaCallback",
            MethodType.methodType(int.class, int.class)
        );
        
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT
        );
        
        log.info("✓ Upcall 生命周期管理:");
        
        // 创建临时 Arena
        try (Arena tempArena = Arena.ofConfined()) {
            MemorySegment tempStub = linker.upcallStub(
                methodHandle,
                descriptor,
                tempArena
            );
            log.info("  临时 Stub 创建: 0x{}", Long.toHexString(tempStub.address()));
            log.info("  临时 Stub 有效");
        }
        
        log.info("  临时 Arena 已关闭，Stub 已失效");
        
        // 创建永久 Stub（使用 Global Arena）
        MemorySegment permanentStub = linker.upcallStub(
            methodHandle,
            descriptor,
            Arena.global()
        );
        log.info("  永久 Stub 创建: 0x{}", Long.toHexString(permanentStub.address()));
        log.info("  永久 Stub 将一直有效");
    }
}