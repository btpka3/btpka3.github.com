package me.test.my.spring.boot.mock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class ServiceA {

    @Autowired
    ServiceB b;

    @Autowired
    ServiceC c;

    public String hi(String name) {

        if ("li4".equals(name)) {
            throw new RuntimeException("ERR");
        }

        return "hi " + name + " " + b.add(1) + " " + c.str(8);


    }

    public void s() {
        Stream.of(1, 2, 3)
                .forEach(System.out::println);
    }


    public void x() {
        System.out.println(c.str(8));
    }

}
