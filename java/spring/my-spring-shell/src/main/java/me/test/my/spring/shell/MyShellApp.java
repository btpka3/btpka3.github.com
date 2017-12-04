package me.test.my.spring.shell;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
public class MyShellApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyShellApp.class, args);
    }

}
