package me.test.first.spring.dubbo.consumer;

import me.test.first.spring.dubbo.DemoDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author dangqian.zll
 * @date 2023/5/12
 */
@SpringBootTest(
    classes = DemoDubboServiceConsumerTest.Conf.class,
    properties = "spring.config.location=classpath:/me/test/first/spring/dubbo/consumer/application-consumer.yaml",
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@SpringBootConfiguration
@EnableDubbo
public class DemoDubboServiceConsumerTest {

    @DubboReference(timeout = 5000)//(check = false, group = "group001", version = "version001")
    private DemoDubboService demoDubboService;

    @SpringBootConfiguration
    public static class Conf {

    }

    @Test
    public void test() {
        for (int i = 0; i < 3; i++) {
            String result = demoDubboService.sayHello("zhang3");
            System.out.println("result = " + result);
        }
    }

}
