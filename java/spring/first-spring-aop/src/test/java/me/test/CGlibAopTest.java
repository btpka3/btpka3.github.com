package me.test;

import me.U;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
// 为了避免 CGlibAopTest.Conf 被扫描，统一使用xml文件配置
//@ContextConfiguration(classes = CGlibAopTest.Conf.class)
public class CGlibAopTest {


//    @Configuration
//    @ComponentScan(basePackages = "me.test")
//    @EnableAspectJAutoProxy(proxyTargetClass = true)
//    public static class Conf {
//
//    }

    private final Logger logger = LoggerFactory.getLogger(CGlibAopTest.class);

    // myImpl.getClass() = "class me.test.MyImpl$$EnhancerBySpringCGLIB$$f0fd21b8"
    @Autowired
    @Qualifier("aaa")
    private Object obj;

    @Test
    public void test() {
        U.logTest(logger, obj);
    }

}
