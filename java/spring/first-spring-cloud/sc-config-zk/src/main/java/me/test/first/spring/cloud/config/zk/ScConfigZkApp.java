package me.test.first.spring.cloud.config.zk;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
public class ScConfigZkApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ScConfigZkApp.class, args);
    }
}
