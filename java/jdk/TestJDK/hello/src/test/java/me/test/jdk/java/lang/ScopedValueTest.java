package me.test.jdk.java.lang;

import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2026/2/4
 */
public class ScopedValueTest {

    ScopedValue<Integer> JAVA_VERSION = ScopedValue.newInstance();

    @Test
    public void test01() {
        ScopedValue.where(JAVA_VERSION, 24).run(this::update);
    }

    private void update() {
        // prints 24
        IO.println("Hello, aaa " + JAVA_VERSION.get());
        ScopedValue.where(JAVA_VERSION, JAVA_VERSION.get() + 1).run(() -> {
            // prints 25
            IO.println("Hello, Java " + JAVA_VERSION.get());
        });
    }
}
