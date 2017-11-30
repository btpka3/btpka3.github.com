package me.test.my.spring.boot.mock.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

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
}
