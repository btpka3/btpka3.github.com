package me.test.first.spring.boot.integration

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["me.test"])
//@EnableAutoConfiguration // 该注解 @SpringBootApplication 已经启用了
class MyIntegrationApp {

    // TODO filters
    // TODO interceptor

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyIntegrationApp.class, args);
    }

}
