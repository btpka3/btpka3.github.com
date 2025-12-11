package me.test.jdk.java.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/5
 */
public class VarArgTest {

    public Object[] myVarArgsMethod(Object... objs) {
        return objs;
    }

    @Test
    public void x1() {
        Object[] arr = myVarArgsMethod("a", "b");
        Assertions.assertEquals(2, arr.length);
        Assertions.assertEquals("a", arr[0]);
        Assertions.assertEquals("b", arr[1]);
    }

    @Test
    public void x2() {
        Object[] arr = myVarArgsMethod(new String[]{"a", "b"});
        Assertions.assertEquals(2, arr.length);
        Assertions.assertEquals("a", arr[0]);
        Assertions.assertEquals("b", arr[1]);
    }
}
