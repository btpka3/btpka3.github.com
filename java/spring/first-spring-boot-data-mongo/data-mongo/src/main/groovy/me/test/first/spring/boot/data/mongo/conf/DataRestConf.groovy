package me.test.first.spring.boot.data.mongo.conf

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter

@Configuration
class DataRestConf {


    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurerAdapter() {

            @Override
            public void configureRepositoryRestConfiguration(
                    RepositoryRestConfiguration config) {
                //config.setBasePath("/api");
            }

            @Override
            public void configureJacksonObjectMapper(ObjectMapper objectMapper) {

                println " ======== " +objectMapper.getSerializationConfig()
            }
        };
    }

}