package me.test.first.spring.boot.test.context.a101;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 注意1：`@Qualifier` 必须 和 `@Autowired` 搭配使用才能注入，单纯使用 `@Qualifier` 是不会注入的。
 * 建议：使用 `@Resource`
 *
 * @author dangqian.zll
 * @date 2022/12/16
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class InitOrderTest {


    @Configuration
    @Import({Aaa.class, Bbb.class})
    public static class Conf {

    }


    @Component("myAaa")
    public static class Aaa implements ApplicationContextAware {

        ApplicationContext applicationContext;

        @Resource(name = "myBbb")
        Bbb bbb;

        public Aaa() {
            System.out.println("constructor init, bbb=" + bbb);
        }

        @PostConstruct
        public void initMe() {
            System.out.println("@PostConstruct called, bbb=" + bbb);
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            System.out.println("setApplicationContext injected, bbb=" + bbb);
            this.applicationContext = applicationContext;
        }
    }

    @Component("myBbb")
    public static class Bbb {
        public String name = "b101";

        public Bbb() {
            System.out.println("bbb-constructor");
        }
    }

    @SneakyThrows
    @Test
    public void test01() {
        System.out.println("start");
        Thread.sleep(3 * 1000);
        System.out.println("end");
    }

}
