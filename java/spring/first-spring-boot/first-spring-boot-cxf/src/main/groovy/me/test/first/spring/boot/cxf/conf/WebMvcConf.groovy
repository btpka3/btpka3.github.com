package me.test.first.spring.boot.cxf.conf

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.View
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
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
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        // 不建议使用，当出错时，错误处理可能会打破该期待。
        // 因为 ERROR dispatch 时， 会到 URL "/error", 而没有后缀信息
        configurer.favorPathExtension(true)

        configurer.favorParameter(true)
    }

}

//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//class MyGlobalMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
//
//    @Override
//    protected AccessDecisionManager accessDecisionManager() {
//        WebExpressionVoter expressionVoter = new WebExpressionVoter();
//        expressionVoter.setExpressionHandler()
//
//        AbstractAccessDecisionManager adm = (AbstractAccessDecisionManager) super.accessDecisionManager();
//        adm.getDecisionVoters().add(expressionVoter)
//        return adm;
//    }
//}

// @EnableWebSecurity(debug = false) 有该注解的话，就不会对 SpringBootWebSecurityConfiguration 进行配置了

//// 配置用户认证和权限信息
//@Configuration
//class SecAuthConf {
//
//    @Bean
//    public AuthenticationManager init(AuthenticationManagerBuilder auth) {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("admin")
//                .authorities("AAA")
//                .roles("ADMIN", "USER")
//
//                .and()
//                .withUser("user")
//                .password("user")
//                .authorities("UUU")
//                .roles("USER")
//
//        return auth.build()
//    }
//}

//// 配置用户认证和权限信息
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Configuration
//class SecAuthConf extends GlobalAuthenticationConfigurerAdapter {
//
//
//    @Override
//    public void init(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("admin")
//                .authorities("AAA")
//                .roles("ADMIN", "USER")
//
//                .and()
//                .withUser("user")
//                .password("user")
//                .authorities("UUU")
//                .roles("USER");
//    }
//}