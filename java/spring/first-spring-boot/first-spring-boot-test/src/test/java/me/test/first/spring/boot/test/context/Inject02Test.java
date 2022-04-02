package me.test.first.spring.boot.test.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * 测试目的：
 * 基于 spring xml 配置文件 + setter注入，是可以将自动完成循环依赖注入
 * <p>
 * FIXME: 循环时 首次访问 相比后面的 rt 较高
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration("classpath:/me/test/first/spring/boot/test/context/Inject02Test.xml")
public class Inject02Test {

    @Configuration
    public static class Conf {
    }

    @Qualifier("pojo2")
    @Autowired
    MyPojo myPojo2;

    @Test
    public void test() {

        System.out.println(getClass() + "#test start");

        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            String nameStr = myPojo2.getNameStr();
            long endTime = System.nanoTime();
            long rt = endTime - startTime;
            System.out.printf("%3d : %15s : %9d%n", i, nameStr, rt);
        }

        System.out.println(getClass() + "#test end");
    }
}
