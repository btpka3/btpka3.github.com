package me.test.conf

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.View
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

@Configuration
class WebMvcConf extends WebMvcConfigurerAdapter {

    /** 自定义错误画面 */
    @Bean(name = ["error"])
    View error(ObjectMapper objectMapper) {
        return new MappingJackson2JsonView(objectMapper)
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        // 不建议使用，当出错时，错误处理可能会打破该期待。
        // 因为 ERROR dispatch 时， 会到 URL "/error", 而没有后缀信息
        configurer.favorPathExtension(true)

        configurer.favorParameter(true)
    }

}
