package me.test.my.spring.boot.mock;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
public class MyApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyApp.class, args);
    }

}
