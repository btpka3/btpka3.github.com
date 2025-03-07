package me.test.first.spring.boot.test.bpp;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 检查 BPP + @Order.
 * <p>
 * 结论:
 * （1）@Order 注解影响
 * - BeanPostProcessor#postProcessBeforeInitialization 的调用顺序
 *
 * <p>
 * （2）@Order 注解不影响
 * - BeanPostProcessor 实现类作为 bean 时，构造函数调用顺序（bean 实例的创建顺序）
 * - 依赖注入 List 类型 时，List 内元素的顺序
 *
 * @author dangqian.zll
 * @date 2023/11/7
 */
@SpringBootTest(
        classes = {
                Bpp04Test.Conf.class,
                Bpp04Test.MyBfpp2.class,
                Bpp04Test.MyBfpp1.class,
                Bpp04Test.MyBfpp3.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class Bpp04Test {

    @Configuration
    public static class Conf {
        @Bean
        MyPojo zhang3() {
            return MyPojo.builder()
                    .name("zhang3")
                    .build();
        }
    }


    @Data
    @Builder(toBuilder = true)
    public static class MyPojo {
        private String name;
    }


    @Component
    @Order(101)
    public static class MyBfpp1 implements BeanPostProcessor {
        public MyBfpp1() {
            System.out.println("==== MyBfpp1:Constructor");
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof MyPojo) {
                System.out.println("==== MyBfpp1:postProcessBeforeInitialization");
            }
            return bean;
        }

    }

    @Component
    @Order(102)
    public static class MyBfpp2 implements BeanPostProcessor {
        public MyBfpp2() {
            System.out.println("==== MyBfpp2:Constructor");
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof MyPojo) {
                System.out.println("==== MyBfpp2:postProcessBeforeInitialization");
            }
            return bean;
        }

    }

    @Component
    @Order(103)
    public static class MyBfpp3 implements BeanPostProcessor {
        public MyBfpp3() {
            System.out.println("==== MyBfpp3:Constructor");
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof MyPojo) {
                System.out.println("==== MyBfpp3:postProcessBeforeInitialization");
            }
            return bean;
        }

    }


    @Autowired
    List<BeanPostProcessor> bppList;

    @Test
    public void test01() {
        for (int i = 0; i < bppList.size(); i++) {
            System.out.println("----- bppList[" + i + "] : " + bppList.get(i).getClass().getName());
        }
        /* output:
==== MyBfpp2:Constructor
==== MyBfpp1:Constructor
==== MyBfpp3:Constructor
==== MyBfpp2:postProcessBeforeInitialization
==== MyBfpp1:postProcessBeforeInitialization
==== MyBfpp3:postProcessBeforeInitialization
----- bppList[0] : org.springframework.boot.test.mock.mockito.MockitoPostProcessor$SpyPostProcessor
----- bppList[1] : org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor
----- bppList[2] : org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
----- bppList[3] : org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
----- bppList[4] : me.test.first.spring.boot.test.bpp.Bpp04Test$MyBfpp1
----- bppList[5] : me.test.first.spring.boot.test.bpp.Bpp04Test$MyBfpp2
----- bppList[6] : me.test.first.spring.boot.test.bpp.Bpp04Test$MyBfpp3
----- bppList[7] : org.springframework.boot.test.mock.mockito.MockitoPostProcessor
----- bppList[8] : org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer$PropertyMappingCheckBeanPostProcessor
         */
    }

}
