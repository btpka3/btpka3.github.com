package me.test.first.chanpay.api.scan;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

@Configuration
//@EnableConfigurationProperties
@SpringBootApplication()
public class UtApp {
    static void main(String[] args) throws Exception {
        SpringApplication.run(UtApp.class, args);
    }
}
