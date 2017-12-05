package me.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["me.test"])
//@EnableAutoConfiguration // 该注解 @SpringBootApplication 已经启用了
class MyHcApp {

    // TODO filters
    // TODO interceptor

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyHcApp.class, args);
    }

}
