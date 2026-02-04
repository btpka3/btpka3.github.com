package me.test.jdk.java.lang.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.util.Arrays;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * JDK8以前都是通过 {@link sun.misc.Unsafe} 实现原子属性访问,从JDK9开始，会尽可能使用VarHandle代.
 *
 * @author dangqian.zll
 * @date 2026/2/4
 */
@Slf4j
public class VarHandleTest {
    private int x = 1;
    public int[] arrayData = new int[]{1, 2, 3};

    private int add(int a, int b) {
        return a + b;
    }

    /**
     * 访问私有字段
     */
    @SneakyThrows
    @Test
    public void test01() {
        VarHandle handle = MethodHandles.lookup().findVarHandle(VarHandleTest.class, "x", int.class);
        VarHandleTest obj = new VarHandleTest();
        log.info("==========A: x={}", obj.x);
        log.info("==========B: x={}", handle.get(obj));
        handle.set(obj, 2);
        log.info("==========C: x={}", obj.x);
    }

    /**
     * 数组元素比较并修改。
     */
    @SneakyThrows
    @Test
    public void test02() {
        VarHandle arrayVarHandle = MethodHandles.arrayElementVarHandle(int[].class);
        VarHandleTest obj = new VarHandleTest();
        log.info("==========A: arrayData={}", Arrays.toString(obj.arrayData));

        // 第0个索引，如果是1，则更新为11
        arrayVarHandle.compareAndSet(obj.arrayData, 0, 1, 11);
        // 第1个索引，如果是3（本来是2），则更新为22（不会更新）
        arrayVarHandle.compareAndSet(obj.arrayData, 1, 3, 22);

        log.info("==========B: arrayData={}", Arrays.toString(obj.arrayData));
    }

    /**
     * 访问私有方法
     */
    @SneakyThrows
    @Test
    public void test03() {
        MethodHandle handle = MethodHandles.lookup().findVirtual(
                VarHandleTest.class,
                "add",
                MethodType.methodType(int.class, int.class, int.class)
        );
        VarHandleTest obj = new VarHandleTest();

        int result = (int) handle.invoke(obj, 1, 2);
        Assertions.assertEquals(3, result);

    }
}
