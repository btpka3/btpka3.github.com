package me.test.first.spring.boot.test.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dangqian.zll
 * @date 2025/4/2
 */
@Configuration
public class MyConf2 {

    @Bean
    public MyPerson li4() {
        return MyPerson.builder()
                .name("li4")
                .build();
    }
}
