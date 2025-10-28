package me.test.first.spring.boot.test.context;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/21
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
public class ObjectProviderTest {

    @Configuration
    public static class Conf {

        @Bean
        MyPojo pojo1(
        ) {
            MyPojo pojo = new MyPojo();
            pojo.setName("zhang3");
            pojo.setMyPojo(null);
            return pojo;
        }
    }

    @Qualifier("pojo1")
    @Autowired
    ObjectProvider<MyPojo> provider;

    @Autowired
    ObjectProvider<MyPerson> myPersonProvider;

    @Autowired
    DefaultListableBeanFactory beanFactory;

    @Test
    public void test() {
        {
            System.out.println("======== provider :" + provider);
            Assertions.assertNotNull(provider);
            MyPojo pojo = provider.getIfAvailable();
            System.out.println("======== pojo :" + JSON.toJSONString(pojo));
            Assertions.assertEquals("zhang3", pojo.getName());
            Assertions.assertNotNull(pojo);

            beanFactory.destroySingleton("pojo1");
            //beanFactory.removeBeanDefinition("pojo1");


            MyPojo pojo11 = provider.getIfAvailable();
            System.out.println("======== pojo11 :" + JSON.toJSONString(pojo11));
            Assertions.assertEquals("zhang3", pojo11.getName());
            beanFactory.destroySingleton("pojo1");

            MyPojo pojo2 = new MyPojo();
            pojo2.setName("li4");
            beanFactory.registerSingleton("pojo1", pojo2);
            // 如果有更新，能获取最新的，但查询性能会很低。
            MyPojo pojo21 = provider.getIfAvailable();
            System.out.println("======== pojo21 :" + JSON.toJSONString(pojo21));
            Assertions.assertEquals("li4", pojo21.getName());
        }

        {
            System.out.println("======== myPersonProvider :" + myPersonProvider);
            Assertions.assertNotNull(myPersonProvider);
            MyPerson myPerson = myPersonProvider.getIfAvailable();
            System.out.println("======== myPerson :" + JSON.toJSONString(myPerson));
            Assertions.assertNull(myPerson);
        }


    }

    @Data
    public static class MyPerson {
        private String name;
    }
}
