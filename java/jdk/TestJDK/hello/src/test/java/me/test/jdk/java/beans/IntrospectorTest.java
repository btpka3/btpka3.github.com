package me.test.jdk.java.beans;

import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dangqian.zll
 * @date 2025/4/23
 * @see java.beans.Introspector
 * @see java.beans.BeanDescriptor
 * @see java.beans.PropertyDescriptor
 * @see java.beans.PropertyEditorSupport
 * @see java.beans.PropertyEditor
 * @see java.beans.ThreadGroupContext
 */
public class IntrospectorTest {

    @Test
    @SneakyThrows
    public void test01() {
        BeanInfo beanInfo = Introspector.getBeanInfo(MyPerson.class);
        PropertyDescriptor[] pdArr = beanInfo.getPropertyDescriptors();
        Set<String> propNames = Arrays.stream(pdArr)
                .map(PropertyDescriptor::getName)
                .collect(Collectors.toSet());
        Assertions.assertEquals(3, propNames.size());
        Assertions.assertTrue(propNames.contains("name"));
        Assertions.assertTrue(propNames.contains("age"));
        Assertions.assertTrue(propNames.contains("class"));

        MyPerson p = new MyPerson();
        p.setName("zhang3");
        p.setAge(18);

        PropertyDescriptor namePd = Arrays.stream(pdArr)
                .filter(pd -> pd.getName().equals("name"))
                .findFirst()
                .orElse(null);

        Method nameGetterMethod = namePd.getReadMethod();
        Assertions.assertEquals("zhang3", nameGetterMethod.invoke(p));
        Method nameSetterMethod = namePd.getWriteMethod();
        nameSetterMethod.invoke(p, "li4");
        Assertions.assertEquals("li4", p.getName());

    }


    @Data
    public static class MyPerson {
        private String name;
        private int age;

    }
}
