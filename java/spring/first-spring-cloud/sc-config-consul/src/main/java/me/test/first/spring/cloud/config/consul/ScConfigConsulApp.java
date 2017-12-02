package me.test.first.spring.cloud.config.consul;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
public class ScConfigConsulApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ScConfigConsulApp.class, args);
    }
}
