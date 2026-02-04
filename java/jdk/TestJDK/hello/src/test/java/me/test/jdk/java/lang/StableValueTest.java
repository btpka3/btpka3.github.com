package me.test.jdk.java.lang;

import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2026/2/4
 */
public class StableValueTest {
    @Test
    public void x() {
        StableValue<String> greeting = StableValue.of();

        String message = greeting.orElseSet(() -> "Hello from StableValue!");
        System.out.println(message);
    }
}
