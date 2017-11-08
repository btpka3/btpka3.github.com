package io.github.btpka3.netty.socks5.conf;

import org.springframework.boot.autoconfigure.security.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.web.cors.*;

import java.util.*;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConf {

    @Bean
    AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setExposedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(
            CorsConfigurationSource corsConfigurationSource
    ) {
        return new WebSecurityConfigurerAdapter() {

            @Override
            public void configure(AuthenticationManagerBuilder auth) {

            }

            @Override
            public void configure(WebSecurity web) throws Exception {
                // empty
            }


            @Override
            protected void configure(HttpSecurity http) throws Exception {

                // 只对以下路径规则应用该安全设置。
                http
                        .authorizeRequests()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().permitAll();


//                http.sessionManagement()
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                // 默认对所有URL都开放权限
//                http.authorizeRequests()
//                        .anyRequest()
//                        .permitAll();

                // 对所有的路径均不使用 CSRF token （因为 stateless）
                http.csrf().disable();

//                // 统一使用 spring-webmvc 中相关的cors配置，不使用 spring-security 的。
                http.cors().configurationSource(corsConfigurationSource);

            }
        };
    }
}
