package me.test.first.chanpay.conf;

import com.fasterxml.jackson.databind.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import org.springframework.core.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.json.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {
    /**
     * 自定义错误画面
     */
    @Bean(name = {"error"})
    public View error(ObjectMapper objectMapper) {
        return new MappingJackson2JsonView(objectMapper);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        // 不建议使用，当出错时，错误处理可能会打破该期待。
        // 因为 ERROR dispatch 时， 会到 URL "/error", 而没有后缀信息
        configurer.favorPathExtension(true);

        configurer.favorParameter(true);
    }

}
