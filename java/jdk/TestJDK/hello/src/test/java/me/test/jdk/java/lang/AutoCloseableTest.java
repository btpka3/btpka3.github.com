package me.test.jdk.java.lang;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/5/9
 */
public class AutoCloseableTest {


    /**
     * 测试 AutoCloseable 的执行顺序。
     * <p>
     * 结论:
     * <ol>
     *     <li>{@link AutoCloseable#close()}</li>
     *     <li>catch block</li>
     *     <li>finally block</li>
     * </ol>
     */
    @SneakyThrows
    @Test
    public void test01() {
        log("test01: start");

        try (MyAutoCloseable myAutoCloseable = new MyAutoCloseable()) {
            Thread.sleep(100);
            throw new RuntimeException("test err");
        } catch (Exception e) {
            Thread.sleep(100);
            log("test01: catch");
        } finally {
            Thread.sleep(100);
            log("test01: finally");
        }
        log("test01: end");
    }

    public static class MyAutoCloseable implements AutoCloseable {
        @Override
        public void close() {
            log("MyAutoCloseable: close() : " + System.currentTimeMillis());
        }
    }

    public static void log(String msg) {
        System.out.println(System.currentTimeMillis() + " : " + msg);
    }
}
