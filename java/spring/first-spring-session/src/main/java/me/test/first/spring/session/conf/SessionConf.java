package me.test.first.spring.session.conf;

import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import org.springframework.core.serializer.support.*;
import org.springframework.session.data.mongo.*;
import org.springframework.session.web.http.*;
import org.springframework.util.*;

import java.io.*;
import java.util.*;

@Configuration
public class SessionConf {

    @Bean
    public CookieSerializer cookieSerializer(
            ServerProperties serverProperties
    ) {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();


        String cookiePath = Arrays.asList(
                serverProperties.getSession().getCookie().getPath(),
                serverProperties.getContextPath(),
                "/"
        ).stream()
                .filter(StringUtils::hasText)
                .findFirst()
                .get();

        //serializer.setCookiePath(env.getProperty("server.session.cookie.path", "/"));
        serializer.setCookiePath(cookiePath);
        return serializer;
    }

    @Bean
    public JdkMongoSessionConverter jdkMongoSessionConverter() {

        // 参考：DefaultSerializer
        SerializingConverter sc = new SerializingConverter();

        // 参考：DefaultDeserializer
        DeserializingConverter dc = new DeserializingConverter(inputStream -> {

            ObjectInputStream objectInputStream = new ConfigurableObjectInputStream(
                    inputStream,
                    Thread.currentThread().getContextClassLoader()
            );
            try {
                return objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new NestedIOException("Failed to deserialize object type", e);
            }

        });

        return new JdkMongoSessionConverter(sc, dc);
    }


}
