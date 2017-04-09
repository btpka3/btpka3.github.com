package com.github.btpka3.first.spring.boot.data.geode

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 */
@SpringBootApplication
//@Import(H2Conf) // 提前数据库 bean
class MyGeodeApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(MyGeodeApp.class, args);
    }

}
