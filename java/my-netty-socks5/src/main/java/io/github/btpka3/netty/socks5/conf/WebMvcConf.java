package io.github.btpka3.netty.socks5.conf;

import com.fasterxml.jackson.databind.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.json.*;

@Configuration
public class WebMvcConf {
    /**
     * 自定义错误画面
     */
    @Bean(name = {"error"})
    View error(ObjectMapper objectMapper) {
        return new MappingJackson2JsonView(objectMapper);
    }

    @Bean
    WebMvcConfigurerAdapter WebMvcConfigurerAdapter() {
        return new WebMvcConfigurerAdapter() {

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                //registry.addViewController("/login").setViewName("login");
            }

            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

                // 不建议使用，当出错时，错误处理可能会打破该期待。
                // 因为 ERROR dispatch 时， 会到 URL "/error", 而没有后缀信息
                configurer.favorPathExtension(false);

                // http://a.com/path?format=json
                configurer.favorParameter(true);
            }

            // 全局 CORS 配置
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**");
            }
        };
    }
}
