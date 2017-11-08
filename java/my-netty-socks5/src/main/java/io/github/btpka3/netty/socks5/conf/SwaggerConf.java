package io.github.btpka3.netty.socks5.conf;

import io.swagger.jaxrs.config.*;
import org.springframework.boot.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConf {

    @Bean
    BeanConfig swaggerBeanConfig(
            BuildProperties buildProperties
    ) {
        // 参考 org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration
        //      BuildProperties buildProperties
        //      GitProperties gitProperties

        String[] schemes = {"https", "http"};
        BeanConfig config = new BeanConfig();
        config.setTitle(buildProperties.getName());
        config.setVersion(buildProperties.getVersion());
        config.setBasePath("/api");
        config.setSchemes(schemes);
        config.setResourcePackage("io.github.btpka3.netty.socks5.resource");
        config.setScan(true);
        return config;
    }
}
