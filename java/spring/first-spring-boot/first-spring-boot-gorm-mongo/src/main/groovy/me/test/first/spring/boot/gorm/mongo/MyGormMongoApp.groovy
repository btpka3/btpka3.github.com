package me.test.first.spring.boot.gorm.mongo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MyGormMongoApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(MyGormMongoApp.class, args);
    }

}
