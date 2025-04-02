package me.test.first.spring.boot.test.autoconfigure;

import org.springframework.context.annotation.Bean;

/**
 * @author dangqian.zll
 * @date 2025/4/2
 */
public class MyConf1 {

    @Bean
    public MyPerson zhang3() {
        return MyPerson.builder()
                .name("zhang3")
                .build();
    }
}
