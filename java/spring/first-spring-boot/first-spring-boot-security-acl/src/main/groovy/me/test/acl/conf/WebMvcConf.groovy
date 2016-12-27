package me.test.acl.conf

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.View
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
class WebMvcConf extends WebMvcConfigurerAdapter {

    /** 自定义错误画面 */
    @Bean(name = ["error"])
    View error(ObjectMapper objectMapper) {
        return new MappingJackson2JsonView(objectMapper)
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

}