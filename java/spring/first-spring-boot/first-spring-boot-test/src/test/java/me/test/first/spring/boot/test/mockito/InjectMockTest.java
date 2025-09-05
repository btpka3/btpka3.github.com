package me.test.first.spring.boot.test.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 *
 * @author dangqian.zll
 * @date 2025/8/15
 */
@ExtendWith(MockitoExtension.class)
public class InjectMockTest {


    @InjectMocks
    InjectMockTestResource.XxxApi xxxApi;

    @Mock
    InjectMockTestResource.XxxService xxxService;

    @Test
    public void testHello() {
        when(xxxService.doService(eq("zhang3")))
                .thenReturn("li4");
        String result = xxxApi.hello("zhang3");
        assertEquals("hello li4", result);
    }
}
