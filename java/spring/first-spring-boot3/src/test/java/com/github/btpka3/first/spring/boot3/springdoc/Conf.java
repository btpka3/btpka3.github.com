package com.github.btpka3.first.spring.boot3.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author dangqian.zll
 * @date 2026/5/26
 */
public class Conf {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("XX 系统 API").version("v1").description("..."))
                .components(new Components().addSecuritySchemes("bearer",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")));
    }
}
