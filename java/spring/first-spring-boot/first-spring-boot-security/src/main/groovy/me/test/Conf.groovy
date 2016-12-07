package me.test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.access.AccessDecisionVoter
import org.springframework.security.access.annotation.Jsr250Voter
import org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter
import org.springframework.security.access.vote.AbstractAccessDecisionManager
import org.springframework.security.access.vote.AffirmativeBased
import org.springframework.security.access.vote.AuthenticatedVoter
import org.springframework.security.access.vote.RoleVoter
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.access.expression.WebExpressionVoter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
class WebMvcConf extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
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
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecConf {

    @Autowired
    void configAuthenticationManager(AuthenticationManagerBuilder auth) {
        // @formatter:off
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .authorities("AAA")
                .roles("ADMIN", "USER")

            .and()
                .withUser("user")
                .password("user")
                .authorities("UUU")
                .roles("USER")

            .and()
                .withUser("basic")
                .password("basic")
                .authorities("UUU")
                .roles("U");
        // @formatter:on
    }


    @Bean
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {

            // 仅仅配置 URL 相关的安全设置，方法级别的通过注解处理
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                // @formatter:off
                http.requestMatchers()
                        .antMatchers("/SecConf/pub.html") // 只匹配这几个请求的URL路径
                        .antMatchers("/SecConf/sec.html")
                        .antMatchers("/SecConf/adm.html")
                        .antMatchers("/login")

                    .and()
                        .authorizeRequests()
                        .antMatchers("/SecConf/pub.html")
                        .permitAll()

                        .antMatchers("/SecConf/sec.html")
                        .authenticated()

                        .antMatchers("/SecConf/adm.html")
                        .access("isAuthenticated() && hasAnyRole('ADMIN')")

                        .antMatchers("/login")
                        .permitAll()

//                        .anyRequest()   // 禁用该配置，以方便测试默认的 http basic 认证
//                        .authenticated()

                    .and()
                        .formLogin()
                        .loginPage("/login")  // 这个如果不明确调用，会通过 DefaultLoginPageConfigurer 注册 DefaultLoginPageGeneratingFilter
//                        .failureUrl("/login?error")

                    //.and()                    // 默认值
                        //.logout()
                        //.logoutUrl("/logout")
                        //.logoutSuccessUrl("/login?logout")

//                    .and()
//                        .exceptionHandling()  // 默认已经配置
//                        .accessDeniedPage("/access?error")  // 如果没设置，则会通过错误码去找相应的错误画面。
                    .and()
                        .httpBasic()
                        .realmName("MySecApp")

                // @formatter:on
            }

//            // 这里的配置只会在 AuthenticationManagerConfiguration 之后运行
//            @Override
//            public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//                // super.configure(auth); //
//
//                auth.inMemoryAuthentication()
//                        .withUser("admin")
//                        .password("admin")
//                        .authorities("AAA")
//                        .roles("ADMIN", "USER")
//
//                        .and()
//                        .withUser("user")
//                        .password("user")
//                        .authorities("UUU")
//                        .roles("USER");
//            }
        }
    }


}

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