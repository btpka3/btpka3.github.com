package me.test.jdk.java.lang.foreign;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * FunctionDescriptor 示例：展示如何描述函数签名
 * 
 * @author dangqian.zll
 * @date 2025/5/7
 * @see java.lang.foreign.FunctionDescriptor
 */
@Slf4j
public class FunctionDescriptorTest {

    /**
     * 示例：描述无参数、无返回值的函数
     * C 函数: void func(void);
     */
    @Test
    public void voidFunction() {
        FunctionDescriptor descriptor = FunctionDescriptor.ofVoid();
        
        log.info("✓ void 函数描述符:");
        log.info("  返回类型: void");
        log.info("  参数数量: {}", descriptor.argumentLayouts().size());
    }

    /**
     * 示例：描述简单参数和返回值的函数
     * C 函数: int add(int a, int b);
     */
    @Test
    public void simpleFunction() {
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,  // 返回类型
            ValueLayout.JAVA_INT,  // 参数1
            ValueLayout.JAVA_INT   // 参数2
        );
        
        log.info("✓ int add(int, int) 函数描述符:");
        log.info("  返回类型: int");
        log.info("  参数类型: int, int");
        log.info("  参数数量: {}", descriptor.argumentLayouts().size());
    }

    /**
     * 示例：描述带指针参数的函数
     * C 函数: size_t strlen(const char *str);
     */
    @Test
    public void pointerArgument() {
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_LONG,  // 返回 size_t (long)
            ValueLayout.ADDRESS     // 参数: const char *
        );
        
        log.info("✓ size_t strlen(const char*) 函数描述符:");
        log.info("  返回类型: long (size_t)");
        log.info("  参数类型: ADDRESS (指针)");
    }

    /**
     * 示例：描述带多个指针参数的函数
     * C 函数: int strcmp(const char *s1, const char *s2);
     */
    @Test
    public void multiplePointers() {
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,  // 返回 int
            ValueLayout.ADDRESS,   // const char *s1
            ValueLayout.ADDRESS    // const char *s2
        );
        
        log.info("✓ int strcmp(const char*, const char*) 函数描述符:");
        log.info("  返回类型: int");
        log.info("  参数类型: ADDRESS, ADDRESS");
    }

    /**
     * 示例：描述可变参数函数
     * C 函数: int printf(const char *format, ...);
     */
    @Test
    public void variadicFunction() {
        // 可变参数函数需要特殊处理
        // 注意：FunctionDescriptor.ofVariadic 在某些 JDK 版本中可能不可用
        // 这里演示如何描述固定参数部分
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,  // 返回 int
            ValueLayout.ADDRESS    // const char *format
        );
        
        log.info("✓ int printf(const char*, ...) 函数描述符:");
        log.info("  返回类型: int");
        log.info("  固定参数: ADDRESS");
        log.info("  可变参数: ...");
    }

    /**
     * 示例：描述带结构体参数的函数
     * C 函数: void process_point(struct Point *p);
     */
    @Test
    public void structArgument() {
        // 定义结构体布局
        MemoryLayout pointLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("x"),
            ValueLayout.JAVA_INT.withName("y"),
            ValueLayout.JAVA_DOUBLE.withName("z")
        );
        
        FunctionDescriptor descriptor = FunctionDescriptor.ofVoid(
            ValueLayout.ADDRESS  // struct Point *p
        );
        
        log.info("✓ void process_point(Point*) 函数描述符:");
        log.info("  返回类型: void");
        log.info("  参数类型: ADDRESS (指向结构体的指针)");
        log.info("  结构体布局: {}", pointLayout);
    }

    /**
     * 示例：描述带数组参数的函数
     * C 函数: void process_array(int *arr, int size);
     */
    @Test
    public void arrayArgument() {
        FunctionDescriptor descriptor = FunctionDescriptor.ofVoid(
            ValueLayout.ADDRESS,  // int *arr
            ValueLayout.JAVA_INT  // int size
        );
        
        log.info("✓ void process_array(int*, int) 函数描述符:");
        log.info("  返回类型: void");
        log.info("  参数类型: ADDRESS (数组指针), int (大小)");
    }

    /**
     * 示例：描述返回指针的函数
     * C 函数: char* getenv(const char *name);
     */
    @Test
    public void pointerReturn() {
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.ADDRESS,  // 返回 char*
            ValueLayout.ADDRESS   // const char *name
        );
        
        log.info("✓ char* getenv(const char*) 函数描述符:");
        log.info("  返回类型: ADDRESS (char*)");
        log.info("  参数类型: ADDRESS");
    }

    /**
     * 示例：描述复杂的函数签名
     * C 函数: int complex_func(int a, double b, const char* c, void* d);
     */
    @Test
    public void complexFunction() {
        FunctionDescriptor descriptor = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,    // 返回 int
            ValueLayout.JAVA_INT,    // int a
            ValueLayout.JAVA_DOUBLE, // double b
            ValueLayout.ADDRESS,     // const char* c
            ValueLayout.ADDRESS      // void* d
        );
        
        log.info("✓ 复杂函数描述符:");
        log.info("  返回类型: int");
        log.info("  参数类型: int, double, ADDRESS, ADDRESS");
        log.info("  参数数量: {}", descriptor.argumentLayouts().size());
    }

    /**
     * 示例：描述带多个返回值的函数（通过指针参数）
     * C 函数: void divide(int dividend, int divisor, int* quotient, int* remainder);
     */
    @Test
    public void multipleReturnValues() {
        FunctionDescriptor descriptor = FunctionDescriptor.ofVoid(
            ValueLayout.JAVA_INT,  // int dividend
            ValueLayout.JAVA_INT,  // int divisor
            ValueLayout.ADDRESS,   // int* quotient (输出参数)
            ValueLayout.ADDRESS    // int* remainder (输出参数)
        );
        
        log.info("✓ void divide(int, int, int*, int*) 函数描述符:");
        log.info("  返回类型: void");
        log.info("  参数类型: int, int, ADDRESS, ADDRESS");
        log.info("  说明: 通过指针参数返回多个值");
    }

    /**
     * 示例：描述函数指针类型
     * C 类型: typedef int (*Callback)(int);
     */
    @Test
    public void functionPointer() {
        // 回调函数的描述符
        FunctionDescriptor callbackDesc = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,  // 返回 int
            ValueLayout.JAVA_INT   // 参数 int
        );
        
        log.info("✓ 函数指针类型 int (*)(int) 的描述符:");
        log.info("  返回类型: int");
        log.info("  参数类型: int");
        
        // 接受函数指针作为参数的函数
        // C 函数: void register_callback(int (*cb)(int));
        FunctionDescriptor registerDesc = FunctionDescriptor.ofVoid(
            ValueLayout.ADDRESS  // 函数指针
        );
        
        log.info("✓ void register_callback(int(*)(int)) 函数描述符:");
        log.info("  返回类型: void");
        log.info("  参数类型: ADDRESS (函数指针)");
    }
}