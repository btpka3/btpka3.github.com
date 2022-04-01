package me.test.first.spring.boot.test.context;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration("classpath:/me/test/first/spring/boot/test/context/Inject02Test.xml")
public class Inject02Test {

    @Configuration
    public static class Conf {}

    @Qualifier("pojo2")
    @Autowired
    MyPojo myPojo2;

    @Test
    public void test() {
        // 成功：基于 spring xml 配置文件 + setter ，是可以将自动完成循环依赖注入
        System.out.println("myPojo2 = " + JSON.toJSONString(myPojo2));
    }
}
