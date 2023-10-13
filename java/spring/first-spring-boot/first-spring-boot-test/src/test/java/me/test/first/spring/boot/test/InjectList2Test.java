package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * 验证通过 spring xml 注入 list 到 构造函数
 *
 * @author dangqian.zll
 * @date 2023/9/21
 */
@SpringBootTest
@ContextConfiguration(locations = "classpath:/me/test/first/spring/boot/test/InjectList2Test.xml")
public class InjectList2Test {

    @Autowired
    MyContainer myContainer;

    @Test
    public void test() {
        System.out.println("111111111");
        System.out.println(JSON.toJSONString(myContainer));
    }


    public static class MyContainer {


        public List<MyPojo> myPojoList;

        public MyContainer(List<MyPojo> myPojoList) {
            this.myPojoList = myPojoList;
        }

        public List<MyPojo> getMyPojoList() {
            return myPojoList;
        }

    }


    public static class MyPojo {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
