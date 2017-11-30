package me.test.my.spring.boot.mock.service;


import mockit.*;
import mockit.integration.junit4.*;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.*;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

@RunWith(JMockit.class)
public class ServiceATestJMockit {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Tested
    private ServiceA a;

    @Injectable
    //@Mocked
    private ServiceB mockB;

    @Injectable
    //@Mocked
    private ServiceC mockC;

    @Test
    public void test1() throws IOException {

//        a.b = mockB;
//        a.c = mockC;

        // 设定期待的调用与返回
        new Expectations() {{

            // Tip：顺序无关紧要

            mockC.str(8);
            result = "99"; // 模拟掉返回值
            times = 1;

            mockB.add(1);
            result = 5; // 模拟掉返回值
            times = 1;
        }};

        String msg = a.hi("zhang3");
        assertThat(msg).isEqualTo("hi zhang3 5 99");

        new Verifications() {{
            mockB.add(1);
            times = 1;

            mockC.str(8);
            times = 1;
        }};

    }

//
//    @Test
//    public void test1() throws IOException {
//
//        ServiceB mockB = mock(ServiceB.class);
//        //ServiceC mockC = mock(ServiceC.class);
//
//        ServiceA a = new ServiceA();
//        a.b = mockB;
//        a.c = mockC;
//
//        // stubbing
//        when(mockB.add(1)).thenReturn(3);
//        when(mockC.str(8)).thenReturn("88");
//
//
//        String msg = a.hi("zhang3");
//        assertThat(msg).isEqualTo("hi zhang3 3 88");
//    }
//
//    @Test
//    public void test2() throws IOException {
//        ServiceA a = new ServiceA();
//        try {
//            a.hi("li4");
//            fail("应当抛出异常");
//        } catch (RuntimeException e) {
//            assertThat(e.getMessage()).isEqualTo("ERR");
//        }
//
//    }

}