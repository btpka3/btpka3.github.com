package me.test.first.spring.boot.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2019-07-22
 * @see <a href="https://cassiomolin.com/2016/09/17/converting-pojo-map-vice-versa-with-jackson/">Converting POJO to Map and vice versa with Jackson</a>
 */
public class JacksonPojo2MapTest {

    @Test
    public void test01() {

        ObjectMapper mapper = new ObjectMapper();
        Foo foo = new Foo();
        foo.setName("zhang3");
        foo.setNow(new Date());
        Bar bar = new Bar();
        bar.setName("lalala");
        foo.setBar(bar);

        Map<String, Object> map = mapper.convertValue(foo, new TypeReference<Map<String, Object>>() {
        });
        System.out.println(map);

        Foo anotherFoo = mapper.convertValue(map, Foo.class);
        System.out.println(anotherFoo);

    }

    public static class Foo {
        private String name;
        private Date now;
        @JsonProperty("aaAge")
        private Integer age;

        private Bar bar;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getNow() {
            return now;
        }

        public void setNow(Date now) {
            this.now = now;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Bar getBar() {
            return bar;
        }

        public void setBar(Bar bar) {
            this.bar = bar;
        }

        @Override
        public String toString() {
            return "Foo{" +
                    "name='" + name + '\'' +
                    ", now=" + now +
                    ", age=" + age +
                    ", bar=" + bar +
                    '}';
        }
    }


    public static class Bar {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Bar{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }


}
