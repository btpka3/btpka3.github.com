package me.test.first.spring.session.conf;

import org.springframework.context.annotation.*;
import org.springframework.session.web.http.*;

@Configuration
public class SessionConf {
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookiePath("/");
        //serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return serializer;
    }
}
