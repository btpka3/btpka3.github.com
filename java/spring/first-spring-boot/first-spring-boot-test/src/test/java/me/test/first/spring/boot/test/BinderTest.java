package me.test.first.spring.boot.test;

import lombok.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.PropertySource;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.env.MockPropertySource;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/25
 */
public class BinderTest {

    @Test
    @SneakyThrows
    public void testBindList0() {
        MockEnvironment env = new MockEnvironment();
        Binder binder = Binder.get(env);

        List<MyPerson> personList = binder.bind("aaa.bbb", Bindable.listOf(MyPerson.class)).get();
        Assertions.assertEquals(0, personList.size());
    }

    @Test
    @SneakyThrows
    public void testBindList() {

        Properties properties = new Properties();
        properties.load(BinderTest.class.getResourceAsStream("BinderTest-01.properties"));
        MockPropertySource mockPropertySource = new MockPropertySource("my-property-source", properties);

        MockEnvironment env = new MockEnvironment();
        env.getPropertySources().addFirst(mockPropertySource);

        List<String> propertySourceName = env.getPropertySources().stream()
                .map(PropertySource::getName)
                .collect(Collectors.toList());
        System.out.println("propertySourceName=" + propertySourceName);
        Binder binder = Binder.get(env);

        List<MyPerson> personList = binder.bind("aaa.bbb", Bindable.listOf(MyPerson.class)).get();
        Assertions.assertEquals(2, personList.size());
        {
            MyPerson person = personList.get(0);
            Assertions.assertEquals("zhang3", person.getName());
            Assertions.assertEquals(2, person.getAddressList().size());
            Assertions.assertEquals("a00", person.getAddressList().get(0).getAddr());
            Assertions.assertEquals("p00", person.getAddressList().get(0).getPhone());
            Assertions.assertEquals("a01", person.getAddressList().get(1).getAddr());
            Assertions.assertEquals("p01", person.getAddressList().get(1).getPhone());
        }

        {
            MyPerson person = personList.get(1);
            Assertions.assertEquals("li4", person.getName());
            Assertions.assertEquals(2, person.getAddressList().size());
            Assertions.assertEquals("a10", person.getAddressList().get(0).getAddr());
            Assertions.assertEquals("p10", person.getAddressList().get(0).getPhone());
            Assertions.assertEquals("a11", person.getAddressList().get(1).getAddr());
            Assertions.assertEquals("p11", person.getAddressList().get(1).getPhone());
        }

    }


    @Test
    @SneakyThrows
    public void testBindList2() {

        Properties properties = new Properties();
        properties.load(BinderTest.class.getResourceAsStream("BinderTest-01.properties"));
        MockPropertySource mockPropertySource = new MockPropertySource("my-property-source", properties);

        MockEnvironment env = new MockEnvironment();
        env.getPropertySources().addFirst(mockPropertySource);
        Binder binder = Binder.get(env);

        List<MyPerson> personList = binder.bind("aaa.bbb", Bindable.listOf(MyPerson.class)).get();
        Assertions.assertEquals(2, personList.size());
        {
            MyPerson person = personList.get(0);
            Assertions.assertEquals("zhang3", person.getName());
            Assertions.assertEquals(2, person.getAddressList().size());
            Assertions.assertEquals("a00", person.getAddressList().get(0).getAddr());
            Assertions.assertEquals("p00", person.getAddressList().get(0).getPhone());
            Assertions.assertEquals("a01", person.getAddressList().get(1).getAddr());
            Assertions.assertEquals("p01", person.getAddressList().get(1).getPhone());
        }

        {
            MyPerson person = personList.get(1);
            Assertions.assertEquals("li4", person.getName());
            Assertions.assertEquals(2, person.getAddressList().size());
            Assertions.assertEquals("a10", person.getAddressList().get(0).getAddr());
            Assertions.assertEquals("p10", person.getAddressList().get(0).getPhone());
            Assertions.assertEquals("a11", person.getAddressList().get(1).getAddr());
            Assertions.assertEquals("p11", person.getAddressList().get(1).getPhone());
        }

    }


    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPerson {
        private String name;
        private List<MyAddr> addressList;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyAddr {
        private String addr;
        private String phone;
    }
}
