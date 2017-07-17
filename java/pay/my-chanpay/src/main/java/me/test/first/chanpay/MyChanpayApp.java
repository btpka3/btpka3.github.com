package me.test.first.chanpay;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication(scanBasePackages = {"me.test"})
public class MyChanpayApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyChanpayApp.class, args);
    }

}
