package me.test.jdk.java.lang;

import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/5/26
 */
public class RuntimeTest {
    @Test
    public void shutdownHook01() {
        Thread printingHook = new Thread(() -> System.out.println("In the middle of a shutdown"));
        Runtime.getRuntime().addShutdownHook(printingHook);
    }
}
