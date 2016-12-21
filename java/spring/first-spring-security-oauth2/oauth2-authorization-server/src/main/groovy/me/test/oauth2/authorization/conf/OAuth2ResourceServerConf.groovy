package me.test.oauth2.authorization.conf

import me.test.oauth2.authorization.MyOAuth2Properties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer

/**
 *
 * FIXME : @EnableResourceServer -> ResourceServerConfiguration 已经提供了一些配置，能否重用？
 */
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//@Configuration
//@EnableResourceServer
//public class OAuth2ResourceServerConf extends ResourceServerConfigurerAdapter {
//
//    @Autowired
//    private MyOAuth2Properties myOAuth2Props
//
//
//    // 先调用 @ ResourceServerConfiguration.configure(HttpSecurity)
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//
//        // 通过 application.yml "security.oauth2.resource.id" 配置
//        resources.resourceId(myOAuth2Props.resource.id)
//                .stateless(false);
//    }
//
//    // 后调用 @ ResourceServerConfiguration.configure(HttpSecurity)
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http.requestMatchers()
//                .antMatchers("/o2/**")
//
//            .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//            .and()
//                .cors()
//            // 权限的配置建议统一使用 @PreAuthorize 等注解来处理。
//            .and()
//                // FIXME : 必须调用该语句，否则 IllegalStateException @ ResourceServerSecurityConfigurer.configure() -> http.authorizeRequests()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//        // @formatter:on
//    }
//
//
//}

class OAuth2ResourceServerConf{}















