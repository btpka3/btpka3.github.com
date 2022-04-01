package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2021/11/9
 */
@SpringBootTest
@ContextConfiguration
public class InjectListTest {

    @Configuration
    public static class Conf {

        @Bean
        MyPojo pojo1() {
            MyPojo pojo = new MyPojo();
            pojo.setName("zhang3");
            System.out.println("pojo1");
            return pojo;
        }

        @Bean
        MyPojo pojo2() {
            MyPojo pojo = new MyPojo();
            pojo.setName("li4");
            System.out.println("pojo2");
            return pojo;
        }

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
                @Lazy
                @Qualifier("myPojoList") List<MyPojo> myPojoList,
                @Lazy
                @Qualifier("pojo3") MyPojo pojo3
        ) {
            MyContainer myContainer = new MyContainer();
            System.out.println("aaa");

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
    }

    public static class MyContainer {
        public List<MyPojo> myPojoList;
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
