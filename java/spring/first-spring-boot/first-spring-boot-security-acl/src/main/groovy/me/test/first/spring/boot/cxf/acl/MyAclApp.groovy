package me.test.first.spring.boot.cxf.acl

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication()
//@Import(H2Conf)
class MyAclApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyAclApp.class, args);
    }

}
