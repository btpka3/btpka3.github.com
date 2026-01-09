package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 验证通过 @Bean 注入 自定义的 list 类型的 bean。
 *
 * @author dangqian.zll
 * @date 2021/11/9
 */
@SpringBootTest
@ContextConfiguration
public class InjectList1Test {

    @Configuration
    public static class Conf {

        @Order(3)
        @Bean
        MyPojo pojo1() {
            MyPojo pojo = new MyPojo();
            pojo.setName("zhang3");
            System.out.println("pojo1");
            return pojo;
        }

        @Order(2)
        @Bean
        MyPojo pojo2() {
            MyPojo pojo = new MyPojo();
            pojo.setName("li4");
            System.out.println("pojo2");
            return pojo;
        }

        @Order(1)
        @Bean
        MyPojo pojo3() {
            MyPojo pojo = new MyPojo();
            pojo.setName("wang5");
            System.out.println("pojo3");
            return pojo;
        }

        @Bean(name = "myPojoList")
        List<MyPojo> myPojoList(
                @Qualifier("pojo1") MyPojo pojo1,
                @Qualifier("pojo2") MyPojo pojo2
        ) {
            List<MyPojo> list = new ArrayList<>(4);
            list.add(pojo1);
            list.add(pojo2);
            System.out.println("bbb");
            return list;
        }

        @Bean
        MyContainer myContainer(
                List<MyPojo> pojoList,
                @Lazy
                @Qualifier("myPojoList")
                List<MyPojo> myPojoList,
                @Lazy
                @Qualifier("pojo3") MyPojo pojo3
        ) {
            MyContainer myContainer = new MyContainer();
            System.out.println("aaa");

            myContainer.defaultPojoList = pojoList;
            myContainer.myPojoList = myPojoList;
            myContainer.myPojo = pojo3;
            return myContainer;
        }

    }

    @Autowired
    MyContainer myContainer;

    @Test
    public void test() {
        System.out.println("111111111");
        System.out.println(JSON.toJSONString(myContainer));

        Assertions.assertEquals("wang5", myContainer.myPojo.getName());

        Assertions.assertEquals(3, myContainer.defaultPojoList.size());
        Assertions.assertEquals("wang5", myContainer.defaultPojoList.get(0).getName());
        Assertions.assertEquals("li4", myContainer.defaultPojoList.get(1).getName());
        Assertions.assertEquals("zhang3", myContainer.defaultPojoList.get(2).getName());

        Assertions.assertEquals(2, myContainer.myPojoList.size());
        Assertions.assertEquals("zhang3", myContainer.myPojoList.get(0).getName());
        Assertions.assertEquals("li4", myContainer.myPojoList.get(1).getName());

    }

    public static class MyContainer {
        // 按照spring 默认获取所有类型的list
        public List<MyPojo> defaultPojoList;
        // 手动注册的List<MyPojo> 类型的 bean
        public List<MyPojo> myPojoList;
        // 按照spring默认顺序获取的第一个优先高的bean
        public MyPojo myPojo;
    }


    public static class MyPojo {

        public MyPojo() {
            System.out.println("myPojo constructor");
        }

        @PostConstruct
        public void aaa() {
            System.out.println("myPojo @PostConstruct");
        }

        public void init() {
            System.out.println("myPojo init");
        }

        private String name;
        private Date birthday;
        private Boolean male;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public Boolean getMale() {
            return male;
        }

        public void setMale(Boolean male) {
            this.male = male;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

}
