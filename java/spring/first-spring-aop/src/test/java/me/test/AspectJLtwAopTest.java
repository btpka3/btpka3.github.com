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
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
//@ContextConfiguration(classes = {AspectJLtwAopTest.Conf.class})
public class AspectJLtwAopTest {

//    @Configuration
//    @ComponentScan(basePackages = "me.test")
//    @EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
//    public static class Conf {
//
//    }

    private final Logger logger = LoggerFactory.getLogger(AspectJLtwAopTest.class);

    @Autowired
    @Qualifier("aaa")
    private Object obj;

    @Test
    public void test() {
        U.logTest(logger, obj);
    }


}
