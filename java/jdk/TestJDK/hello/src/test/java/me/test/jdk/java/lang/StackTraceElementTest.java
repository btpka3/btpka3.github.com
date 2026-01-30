package me.test.jdk.java.lang;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/5/8
 */
public class StackTraceElementTest {

    @Test
    public void test01() {
        StackTraceElement[] s0 = null, s1 = null;
        for (int i = 0; i < 2; i++) {
            StackTraceElement[] s = getStack();
            if (i == 0) {
                s0 = s;
            }
            if (i == 1) {
                s1 = s;
            }
        }
        System.out.println(s0 == s1);
        System.out.println(s0.hashCode());
        System.out.println(s1.hashCode());
        System.out.println(s0.equals(s1));
        System.out.println(Arrays.equals(s0, s1));
        System.out.println(Arrays.hashCode(s0));
        System.out.println(Arrays.hashCode(s1));

    }

    protected StackTraceElement[] getStack() {
        return Thread.currentThread().getStackTrace();
    }
}
