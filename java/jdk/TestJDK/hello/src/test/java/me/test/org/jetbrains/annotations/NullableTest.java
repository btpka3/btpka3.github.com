package me.test.org.jetbrains.annotations;

import javax.annotation.Nullable;
import org.jetbrains.annotations.Contract;

/**
 * @author dangqian.zll
 * @date 2024/6/28
 */
public class NullableTest {

    public void test(@Nullable Object o) {
//        System.out.println(o.hashCode());
        //verify(o);


        assertTrue(o != null, "xxx");
        System.out.println(o.hashCode());

    }

    @Contract("null -> fail")
    protected void verify(Object param) {

        if (param == null) {
            throw new RuntimeException("xxx");
        }
    }

    protected void assertTrue(boolean b, String message) {
        if (!b) {
            throw new RuntimeException(message);
        }
    }
}
