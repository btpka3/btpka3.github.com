package me.test.jdk.java.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2026/2/5
 */
public class LazyEnumerationTest {

    @Test
    public void test() {

    }

    protected Enumeration<String> getEnumeration() {
        return null;
    }

    protected Enumeration<String> getEnumeration1() {
        return Collections.enumeration(Arrays.asList("a1", "a2"));
    }

    protected Enumeration<String> getEnumeration2() {
        return null;
    }

    protected Enumeration<String> getEnumeration3() {
        return null;
    }

}
