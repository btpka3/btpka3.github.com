package me.test.first.spring.boot.test.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * @author dangqian.zll
 * @date 2024/9/13
 */
@SpringBootTest(classes = SpyBeanTest.Conf.class)
public class SpyBeanTest {

    @Configuration
    public static class Conf {
    }

    @MockBean
    MyDepService myDepService;

    @SpyBean
    MyService myService;

    @Test
    public void test() {

        when(myDepService.call())
                .thenReturn("dep002");

        doReturn("my002")
                .when(myService)
                .supply();

        String result = myService.run();
        System.out.println("==============");
        System.out.println(result);
        System.out.println("==============");
        Assertions.assertEquals("MyService[my002] and dep002", result);
    }

    @Component
    public static class MyService {

        MyDepService myDepService;

        public MyService(MyDepService myDepService) {
            this.myDepService = myDepService;
        }

        public String run() {
            return "MyService[" + supply() + "] and " + myDepService.call();
        }

        protected String supply() {
            return "my001";
        }
    }

    @Component
    public static class MyDepService {
        public String call() {
            return "dep001";
        }
    }

}
