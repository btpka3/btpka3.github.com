package me.test.first.spring.boot.cxf.db

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 */
@SpringBootApplication
//@Import(H2Conf) // 提前数据库 bean
class MyDbApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyDbApp.class, args);
    }

}
