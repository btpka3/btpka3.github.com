package me.test.first.spring.session.conf;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.*;

@Configuration
public class CommonConf {
    @Autowired
    void test(Environment env) {
        System.out.println("~~~~~~~~~~~ p 0= " + env.getProperty("p"));
        System.out.println("~~~~~~~~~~~ p 1= " + System.getProperty("p"));
    }
}
