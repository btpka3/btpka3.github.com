package me.test

import me.test.conf.H2Conf
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

/**
 *
 */
@SpringBootApplication
@Import(H2Conf)
class MyDbApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyDbApp.class, args);
    }

}
