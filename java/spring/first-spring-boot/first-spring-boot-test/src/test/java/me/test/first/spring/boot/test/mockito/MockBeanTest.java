package me.test.first.spring.boot.test.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import static org.mockito.Mockito.when;

/**
 * @author dangqian.zll
 * @date 2024/9/13
 */
@SpringBootTest(classes = MockBeanTest.Conf.class)
public class MockBeanTest {

    @Configuration
    @Import(MyService.class)
    public static class Conf {
    }

    @MockBean
    MyDepService myDepService;

    @Autowired
    MyService myService;

    @Test
    public void test() {

        when(myDepService.call())
                .thenReturn("dep002");

        String result = myService.run();
        System.out.println("==============");
        System.out.println(result);
        System.out.println("==============");
        Assertions.assertEquals("MyService and dep002", result);
    }

    @Component
    public static class MyService {

        MyDepService myDepService;

        public MyService(MyDepService myDepService) {
            this.myDepService = myDepService;
        }

        public String run() {
            return "MyService and " + myDepService.call();
        }
    }

    @Component
    public static class MyDepService {
        public String call() {
            return "dep001";
        }
    }

}
