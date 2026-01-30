package me.test.jdk.java.util.stream;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToMapTest {


    public static void main(String[] args) {

        Map<String, Person> map = Stream.of(
                        new Person("zhang3", 33),
                        new Person("li4", 44),
                        new Person("wang5", 55)
                )
                .collect(Collectors.toMap(Person::getName, Function.identity()));


        System.out.println(map);
    }


    public static class Person {
        private String name;
        private Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
