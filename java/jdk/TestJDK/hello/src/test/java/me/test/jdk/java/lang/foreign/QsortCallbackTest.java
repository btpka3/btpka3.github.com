package me.test.jdk.java.lang.foreign;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * qsort 回调示例：展示如何使用 Java 回调函数调用标准 C 库的 qsort 函数
 * 
 * C 函数签名:
 * void qsort(void *base, size_t nmemb, size_t size,
 *            int (*compar)(const void *, const void *));
 * 
 * @author dangqian.zll
 * @date 2025/5/7
 * @see java.lang.foreign.Linker
 */
@Slf4j
public class QsortCallbackTest {

    /**
     * 示例：使用 Java 回调函数调用 qsort 对整数数组排序
     */
    @Test
    public void qsortWithJavaCallback() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        // 1. 查找 qsort 函数
        MemorySegment qsortFunc = linker.defaultLookup().find("qsort").orElseThrow();
        
        // 2. 定义 qsort 函数的描述符
        // void qsort(void *base, size_t nmemb, size_t size, int (*compar)(const void *, const void *));
        FunctionDescriptor qsortDesc = FunctionDescriptor.ofVoid(
            ValueLayout.ADDRESS,  // void *base - 数组指针
            ValueLayout.JAVA_LONG, // size_t nmemb - 元素个数
            ValueLayout.JAVA_LONG, // size_t size - 每个元素的大小
            ValueLayout.ADDRESS    // int (*compar)(const void *, const void *) - 比较函数指针
        );
        
        // 3. 创建 qsort 的方法句柄
        MethodHandle qsortHandle = linker.downcallHandle(qsortFunc, qsortDesc);
        
        // 4. 创建 Java 比较函数的 MethodHandle
        MethodHandle javaComparator = MethodHandles.lookup().findStatic(
            QsortCallbackTest.class,
            "compareIntegers",
            MethodType.methodType(int.class, MemorySegment.class, MemorySegment.class)
        );
        
        // 5. 定义比较函数的描述符
        FunctionDescriptor comparatorDesc = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,    // 返回 int
            ValueLayout.ADDRESS,     // const void *a
            ValueLayout.ADDRESS      // const void *b
        );
        
        // 6. 创建 Upcall Stub（Java 回调函数）
        MemorySegment comparatorStub = linker.upcallStub(
            javaComparator,
            comparatorDesc,
            Arena.global()
        );
        
        // 7. 准备要排序的数组
        int[] originalArray = { 5, 2, 8, 1, 9, 3, 7, 4, 6 };
        log.info("✓ 原始数组: {}", Arrays.toString(originalArray));
        
        // 8. 在 Arena 中分配内存并复制数组
        try (Arena arena = Arena.ofConfined()) {
            int arraySize = originalArray.length;
            MemorySegment arraySegment = arena.allocate(ValueLayout.JAVA_INT, arraySize);
            
            // 复制数组到内存段
            for (int i = 0; i < arraySize; i++) {
                arraySegment.setAtIndex(ValueLayout.JAVA_INT, i, originalArray[i]);
            }
            
            // 9. 调用 qsort 函数，传入 Java 回调
            log.info("✓ 调用 qsort 函数，使用 Java 回调进行比较...");
            qsortHandle.invokeExact(
                arraySegment,        // 数组指针
                (long) arraySize,    // 元素个数
                ValueLayout.JAVA_INT.byteSize(),  // 每个元素的大小
                comparatorStub       // 比较函数指针（Java 回调）
            );
            
            // 10. 读取排序后的数组
            int[] sortedArray = new int[arraySize];
            for (int i = 0; i < arraySize; i++) {
                sortedArray[i] = arraySegment.getAtIndex(ValueLayout.JAVA_INT, i);
            }
            
            log.info("✓ 排序后的数组: {}", Arrays.toString(sortedArray));
            log.info("✓ Java 回调函数在排序过程中被多次调用！");
        }
    }

    /**
     * Java 比较函数：比较两个整数
     * 对应 C 函数签名: int compar(const void *, const void *)
     * 
     * @param a 指向第一个元素的指针
     * @param b 指向第二个元素的指针
     * @return 如果 a < b 返回负数，如果 a == b 返回 0，如果 a > b 返回正数
     */
    private static int compareIntegers(MemorySegment a, MemorySegment b) {
        // qsort 传递的是裸地址，需要重新解释内存段大小
        MemorySegment segmentA = a.reinterpret(ValueLayout.JAVA_INT.byteSize());
        MemorySegment segmentB = b.reinterpret(ValueLayout.JAVA_INT.byteSize());
        
        // 从内存段中读取整数值
        int valueA = segmentA.get(ValueLayout.JAVA_INT, 0);
        int valueB = segmentB.get(ValueLayout.JAVA_INT, 0);
        
        log.debug("  → Java 回调被调用: 比较 {} 和 {}", valueA, valueB);
        
        // 返回比较结果
        return Integer.compare(valueA, valueB);
    }

    /**
     * 示例：使用 Java 回调函数对字符串数组排序
     */
    @Test
    public void qsortStringsWithJavaCallback() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        // 1. 查找 qsort 函数
        MemorySegment qsortFunc = linker.defaultLookup().find("qsort").orElseThrow();
        
        // 2. 定义 qsort 函数的描述符
        FunctionDescriptor qsortDesc = FunctionDescriptor.ofVoid(
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS
        );
        
        // 3. 创建 qsort 的方法句柄
        MethodHandle qsortHandle = linker.downcallHandle(qsortFunc, qsortDesc);
        
        // 4. 创建 Java 字符串比较函数的 MethodHandle
        MethodHandle javaComparator = MethodHandles.lookup().findStatic(
            QsortCallbackTest.class,
            "compareStrings",
            MethodType.methodType(int.class, MemorySegment.class, MemorySegment.class)
        );
        
        // 5. 定义比较函数的描述符
        FunctionDescriptor comparatorDesc = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS
        );
        
        // 6. 创建 Upcall Stub
        MemorySegment comparatorStub = linker.upcallStub(
            javaComparator,
            comparatorDesc,
            Arena.global()
        );
        
        // 7. 准备要排序的字符串数组
        String[] originalStrings = { "banana", "apple", "cherry", "date", "elderberry" };
        log.info("✓ 原始字符串数组: {}", Arrays.toString(originalStrings));
        
        // 8. 在 Arena 中分配内存
        try (Arena arena = Arena.ofConfined()) {
            int arraySize = originalStrings.length;
            
            // 创建指针数组（每个指针指向一个字符串）
            MemorySegment pointerArray = arena.allocate(ValueLayout.ADDRESS, arraySize);
            
            // 分配每个字符串并设置指针
            for (int i = 0; i < arraySize; i++) {
                MemorySegment stringSegment = arena.allocateFrom(originalStrings[i]);
                pointerArray.setAtIndex(ValueLayout.ADDRESS, i, stringSegment);
            }
            
            // 9. 调用 qsort 函数
            log.info("✓ 调用 qsort 函数排序字符串数组...");
            qsortHandle.invokeExact(
                pointerArray,
                (long) arraySize,
                ValueLayout.ADDRESS.byteSize(),  // 指针的大小
                comparatorStub
            );
            
            // 10. 读取排序后的字符串指针
            String[] sortedStrings = new String[arraySize];
            for (int i = 0; i < arraySize; i++) {
                MemorySegment stringPtr = pointerArray.getAtIndex(ValueLayout.ADDRESS, i);
                // 使用辅助方法读取 C 字符串
                sortedStrings[i] = readCString(stringPtr);
            }
            
            log.info("✓ 排序后的字符串数组: {}", Arrays.toString(sortedStrings));
            log.info("✓ Java 回调函数在字符串比较过程中被多次调用！");
        }
    }

    /**
     * Java 字符串比较函数
     * 对应 C 函数签名: int compar(const void *, const void *)
     * 
     * @param a 指向第一个字符串指针的指针
     * @param b 指向第二个字符串指针的指针
     * @return 比较结果
     */
    private static int compareStrings(MemorySegment a, MemorySegment b) {
        // qsort 传递的是裸地址，需要重新解释内存段大小
        MemorySegment segmentA = a.reinterpret(ValueLayout.ADDRESS.byteSize());
        MemorySegment segmentB = b.reinterpret(ValueLayout.ADDRESS.byteSize());
        
        // 读取字符串指针
        MemorySegment stringA = segmentA.get(ValueLayout.ADDRESS, 0);
        MemorySegment stringB = segmentB.get(ValueLayout.ADDRESS, 0);
        
        // 读取字符串内容 - 手动读取以 null 结尾的字符串
        String strA = readCString(stringA);
        String strB = readCString(stringB);
        
        log.debug("  → Java 回调被调用: 比较 \"{}\" 和 \"{}\"", strA, strB);
        
        // 返回比较结果
        return strA.compareTo(strB);
    }
    
    /**
     * 读取以 null 结尾的 C 字符串
     */
    private static String readCString(MemorySegment segment) {
        StringBuilder sb = new StringBuilder();
        int offset = 0;
        byte b;
        // 如果 segment 大小为 0，说明是裸地址，重新解释为足够大的大小
        MemorySegment safeSegment = segment.byteSize() == 0 
            ? segment.reinterpret(Integer.MAX_VALUE) 
            : segment;
        
        // 读取直到遇到 null 终止符
        while (true) {
            try {
                b = safeSegment.get(ValueLayout.JAVA_BYTE, offset);
                if (b == 0) {
                    break;
                }
                sb.append((char) b);
                offset++;
            } catch (IndexOutOfBoundsException e) {
                // 如果越界，停止读取
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 示例：使用 Java 回调进行降序排序
     */
    @Test
    public void qsortDescendingWithJavaCallback() throws Throwable {
        Linker linker = Linker.nativeLinker();
        
        // 1. 查找 qsort 函数
        MemorySegment qsortFunc = linker.defaultLookup().find("qsort").orElseThrow();
        
        // 2. 定义 qsort 函数的描述符
        FunctionDescriptor qsortDesc = FunctionDescriptor.ofVoid(
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS
        );
        
        // 3. 创建 qsort 的方法句柄
        MethodHandle qsortHandle = linker.downcallHandle(qsortFunc, qsortDesc);
        
        // 4. 创建降序比较函数的 MethodHandle
        MethodHandle javaComparator = MethodHandles.lookup().findStatic(
            QsortCallbackTest.class,
            "compareIntegersDescending",
            MethodType.methodType(int.class, MemorySegment.class, MemorySegment.class)
        );
        
        // 5. 定义比较函数的描述符
        FunctionDescriptor comparatorDesc = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS
        );
        
        // 6. 创建 Upcall Stub
        MemorySegment comparatorStub = linker.upcallStub(
            javaComparator,
            comparatorDesc,
            Arena.global()
        );
        
        // 7. 准备要排序的数组
        int[] originalArray = { 5, 2, 8, 1, 9, 3, 7, 4, 6 };
        log.info("✓ 原始数组: {}", Arrays.toString(originalArray));
        
        // 8. 在 Arena 中分配内存并复制数组
        try (Arena arena = Arena.ofConfined()) {
            int arraySize = originalArray.length;
            MemorySegment arraySegment = arena.allocate(ValueLayout.JAVA_INT, arraySize);
            
            // 复制数组到内存段
            for (int i = 0; i < arraySize; i++) {
                arraySegment.setAtIndex(ValueLayout.JAVA_INT, i, originalArray[i]);
            }
            
            // 9. 调用 qsort 函数进行降序排序
            log.info("✓ 调用 qsort 函数进行降序排序...");
            qsortHandle.invokeExact(
                arraySegment,
                (long) arraySize,
                ValueLayout.JAVA_INT.byteSize(),
                comparatorStub
            );
            
            // 10. 读取排序后的数组
            int[] sortedArray = new int[arraySize];
            for (int i = 0; i < arraySize; i++) {
                sortedArray[i] = arraySegment.getAtIndex(ValueLayout.JAVA_INT, i);
            }
            
            log.info("✓ 降序排序后的数组: {}", Arrays.toString(sortedArray));
            log.info("✓ Java 回调函数在降序排序过程中被多次调用！");
        }
    }

    /**
     * Java 降序比较函数
     * 
     * @param a 指向第一个元素的指针
     * @param b 指向第二个元素的指针
     * @return 如果 a < b 返回正数，如果 a == b 返回 0，如果 a > b 返回负数（降序）
     */
    private static int compareIntegersDescending(MemorySegment a, MemorySegment b) {
        // qsort 传递的是裸地址，需要重新解释内存段大小
        MemorySegment segmentA = a.reinterpret(ValueLayout.JAVA_INT.byteSize());
        MemorySegment segmentB = b.reinterpret(ValueLayout.JAVA_INT.byteSize());
        
        int valueA = segmentA.get(ValueLayout.JAVA_INT, 0);
        int valueB = segmentB.get(ValueLayout.JAVA_INT, 0);
        
        log.debug("  → Java 降序回调被调用: 比较 {} 和 {}", valueA, valueB);
        
        // 返回相反的比较结果，实现降序排序
        return Integer.compare(valueB, valueA);
    }
}