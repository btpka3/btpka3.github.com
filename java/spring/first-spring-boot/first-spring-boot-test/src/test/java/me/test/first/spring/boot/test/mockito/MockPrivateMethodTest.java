package me.test.first.spring.boot.test.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author dangqian.zll
 * @date 2025/8/15
 */
@ExtendWith(MockitoExtension.class)
public class MockPrivateMethodTest {

    @Spy
    @InjectMocks
    MockPrivateMethodTestResource.Calculator calculator;

    @Test
    void testPrivateMethod() {
//        // mock 私有方法 `add`
//        doAnswer(invocation -> 10)
//                .when(calculator,"add", anyInt(), anyInt())
//                .add(anyInt(), anyInt());
//
//
//        mockPrivate(calculator, "add", 3, 4);

        int result = calculator.sum(3, 4);
        assertEquals(10, result);
    }
}
