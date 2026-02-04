package me.test.jdk.java.util.stream;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class GroupByNullTest {

    /**
     * 按单个POJO的某个字段分组
     */
    @Test
    public void test1() {

        Object m = Stream.of(
                        new Person("zhang3", 33), new Person("li4", 44), new Person("wang5", 55), new Person(null, 66))
                .collect(Collectors.groupingBy(Person::getName));

        System.out.println(m);
    }

    /**
     * 按单个POJO的某个字段分组， 但collector时再映射成新的类型
     */
    @Test
    public void test2() {

        Object m = Stream.of(
                        new Person("zhang3", 33), new Person("li4", 44), new Person("wang5", 55), new Person(null, 66))
                .collect(Collectors.groupingBy(
                        p -> p.getName() != null ? p.getName() : "-",
                        Collectors.mapping((Person p) -> p.getName() + "_" + p.getAge(), Collectors.toList())));
        System.out.println("=========");
        System.out.println(m);
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person {
        private String name;
        private Integer age;
    }
}
