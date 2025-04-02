package me.test.first.spring.boot.test.configuration.pkg1;

import org.springframework.context.annotation.Bean;

/**
 * @author dangqian.zll
 * @date 2025/4/2
 */
public class Conf1 {

    @Bean
    public MyPerson confZhang3() {
        return MyPerson.builder()
                .name("zhang3")
                .build();
    }
}
