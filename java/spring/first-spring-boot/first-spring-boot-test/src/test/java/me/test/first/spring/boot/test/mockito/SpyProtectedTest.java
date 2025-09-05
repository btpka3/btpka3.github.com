package me.test.first.spring.boot.test.mockito;

import me.test.first.spring.boot.test.mockito.child.Child;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

/**
 *
 * @author dangqian.zll
 * @date 2025/8/15
 */
@ExtendWith(MockitoExtension.class)
public class SpyProtectedTest {

    public static class ChildExt extends Child {
        public String findRealName(String name) {
            return super.findRealName(name);
        }
    }

    @Spy
    ChildExt child;

    @Test
    public void testHello() {
        doReturn("li4")
                .when(child)
                .findRealName(eq("zhang3"));
        String result = child.hello("zhang3");
        assertEquals("hello li4", result);
    }

}
