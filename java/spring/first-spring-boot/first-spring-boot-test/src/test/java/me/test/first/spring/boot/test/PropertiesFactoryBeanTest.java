package me.test.first.spring.boot.test;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.core.io.ClassPathResource;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author dangqian.zll
 * @date 2020-03-28
 */
public class PropertiesFactoryBeanTest {


    @Test
    public void x() {
        ClassPathResource resource = new ClassPathResource("/me/test/first/spring/boot/test/person.yaml");

        YamlPropertiesFactoryBean factoryBean;
        factoryBean = new YamlPropertiesFactoryBean();
        // optional depends on your use-case
        factoryBean.setSingleton(true);
        factoryBean.setResources(resource);

        Properties properties = factoryBean.getObject();


        ConfigurationPropertySource c = new MapConfigurationPropertySource(properties);

        Binder b = new Binder(c);
        BindResult<Person> bindResult = b.bind("", Person.class);
        Person person = bindResult.get();
        System.out.println("=================" + person);
    }


    public static class X extends Person {

    }

    @Data
    @NoArgsConstructor
    public static class Person {
        private String name;
        private Integer age;

        // 必须添加，不支持相同class 递归属性
        @NestedConfigurationProperty
        private Person father;
        private List<Person> children;
        private Map<String, String> ext;
    }
}
