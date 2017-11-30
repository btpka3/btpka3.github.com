package me.test.my.spring.boot.mock.service;


import me.test.my.spring.boot.mock.*;
import org.junit.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

/**
 * 真实调用所有代码
 */
public class ServiceATestSpring extends BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    ServiceA a;

    @Test
    public void test1() throws IOException {

        String msg = a.hi("zhang3");
        assertThat(msg).isEqualTo("hi zhang3 2 81");
    }

    @Test
    public void test2() throws IOException {

        try {
            a.hi("li4");
            fail("应当抛出异常");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("ERR");
        }

    }

}