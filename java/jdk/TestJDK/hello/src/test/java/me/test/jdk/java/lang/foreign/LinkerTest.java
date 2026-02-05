package me.test.jdk.java.lang.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Linker 示例：展示如何调用本地 C 库函数
 * 
 * @author dangqian.zll
 * @date 2025/5/7
 * @see java.lang.foreign.Linker
 * @see java.lang.foreign.FunctionDescriptor
 */
@Slf4j
public class LinkerTest {

    /**
     * 示例：调用标准 C 库的 strlen 函数
     * 
     * C 函数签名: size_t strlen(const char *str);
     */
    @Test
    public void callStrlen() throws Throwable {
        // 1. 获取默认 linker
        Linker linker = Linker.nativeLinker();
        
        // 2. 查找 C 标准库
        MemorySegment stdLib = linker.defaultLookup().find("strlen").get();
        
        // 3. 定义函数描述符：参数为指针，返回值为 C 的 size_t (在 64 位系统上是 long)
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS
        );
        
        // 4. 创建方法句柄
        MethodHandle strlen = linker.downcallHandle(stdLib, descriptor);
        
        // 5. 在 Arena 中分配内存并写入字符串
        try (Arena arena = Arena.ofConfined()) {
            String testString = "Hello, Panama!中国";
            MemorySegment cString = arena.allocateFrom(testString);
            
            // 6. 调用 C 函数
            long length = (long) strlen.invokeExact(cString);
            
            log.info("✓ 字符串: \"{}\"", testString);
            log.info("✓ 长度: {}", length);
        }
    }

    /**
     * 示例：调用标准 C 库的 abs 函数（绝对值）
     * 
     * C 函数签名: int abs(int x);
     */
    @Test
    public void callAbs() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        // 查找 abs 函数
        MemorySegment absFunc = linker.defaultLookup().find("abs").get();
        
        // 定义函数描述符：参数为 int，返回值为 int
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT
        );
        
        MethodHandle abs = linker.downcallHandle(absFunc, descriptor);
        
        // 测试不同数值
        int[] testValues = { -42, 0, 42, -100, 200 };
        
        for (int value : testValues) {
            int result = (int) abs.invokeExact(value);
            log.info("✓ abs({}) = {}", value, result);
        }
    }

    /**
     * 示例：调用标准 C 库的 rand 函数（随机数）
     * 
     * C 函数签名: int rand(void);
     */
    @Test
    public void callRand() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        // 查找 rand 函数
        MemorySegment randFunc = linker.defaultLookup().find("rand").get();
        
        // 定义函数描述符：无参数，返回值为 int
        FunctionDescriptor descriptor = FunctionDescriptor.of(ValueLayout.JAVA_INT);
        
        MethodHandle rand = linker.downcallHandle(randFunc, descriptor);
        
        // 生成 5 个随机数
        log.info("✓ 生成 5 个随机数:");
        for (int i = 0; i < 5; i++) {
            int randomValue = (int) rand.invokeExact();
            log.info("  随机数 {}: {}", i + 1, randomValue);
        }
    }
}