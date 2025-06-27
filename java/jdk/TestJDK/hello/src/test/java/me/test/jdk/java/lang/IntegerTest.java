package me.test.jdk.java.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/6/18
 */
public class IntegerTest {
    @Test
    public void testHex() {
        {
            byte b = 3;
            String result = Integer.toHexString(b);
            Assertions.assertEquals("0x03", result);
        }
        {
            byte b =(byte) 0xAA;
            String result = Integer.toHexString(b);
            Assertions.assertEquals("0xAA", result);
        }
    }
}
