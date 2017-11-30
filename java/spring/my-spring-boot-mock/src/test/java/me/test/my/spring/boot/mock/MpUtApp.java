package me.test.my.spring.boot.mock;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

@Configuration
@SpringBootApplication
public class MpUtApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MpUtApp.class, args);
    }

}
