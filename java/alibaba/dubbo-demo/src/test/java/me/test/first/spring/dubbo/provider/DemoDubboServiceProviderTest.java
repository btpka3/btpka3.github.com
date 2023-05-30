package me.test.first.spring.dubbo.provider;

import lombok.SneakyThrows;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author dangqian.zll
 * @date 2023/5/12
 */
@SpringBootTest(
    classes = DemoDubboServiceProviderTest.Conf.class,
    properties = "spring.config.location=classpath:/me/test/first/spring/dubbo/provider/application-provider.yaml",
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)

@EnableDubbo
public class DemoDubboServiceProviderTest {

    @SpringBootConfiguration
    public static class Conf {

    }

    @SneakyThrows
    @Test
    public void test() {
        int minute = 10;
        System.out.println("Dubbo Service is up, Please run DemoDubboServiceConsumerTest in " + minute + " minute");
        Thread.sleep(minute * 60 * 1000);
    }
}
