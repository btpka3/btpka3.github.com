package me.test.first.spring.boot.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Provider;
import java.util.List;
import java.util.Optional;

/**
 * @author dangqian.zll
 * @date 2021/2/24
 */
@SpringBootTest(
        classes = OptionalTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OptionalTest {

    @Configuration
    public static class Conf {
        //        @Bean(name = "p1")
        Person person() {
            return new Person();
        }

        @Bean(name = "s1")
        Student student() {
            return new Student();
        }

        @Bean(name = "c")
        ClassRoom classRoom(
                Optional<Person> personOptional,
                @Qualifier("s1")
                        Optional<Student> studentOptional,
                Provider<Student> studentProviderNoName,
                @Qualifier("s1")
                        Provider<Student> studentProviderWithName,
                Optional<List<Student>> studentListOptional
        ) {
            ClassRoom classRoom = new ClassRoom();
            classRoom.p = personOptional.orElse(null);
            classRoom.s = studentOptional.orElse(null);
            classRoom.studentList = studentListOptional.orElse(null);
            return classRoom;
        }
    }

    @Autowired
    ClassRoom classRoom;

    @Test
    public void test001() {
        System.out.println(classRoom);
    }

    public static class Person {
        public String name = "person001";
    }

    public static class Student {
        public String name = "student001";
    }

    public static class ClassRoom {
        public Person p;
        public Student s;
        public List<Student> studentList;
    }

}
