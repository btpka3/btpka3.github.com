package me.test.first.spring.boot.test.configuration.pkg1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dangqian.zll
 * @date 2025/4/2
 */
@Configuration
public class Conf2 {

    @Bean
    public MyPerson confLi4() {
        return MyPerson.builder()
                .name("li4")
                .build();
    }
}
