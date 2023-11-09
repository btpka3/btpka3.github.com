package me.test.first.spring.boot.webflux.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.result.view.View;

/**
 * @author 当千
 * @date 2018-12-06
 */
@Configuration
public class MvcConf {

    /**
     * 自定义错误画面
     */
//    @Bean(name ="error")
//    View error(ObjectMapper objectMapper) {
//        return new MappingJackson2JsonView(objectMapper);
//    }
}
