package com.github.btpka3.dubbo.demo.consumer.conf;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.github.btpka3.dubbo.demo.api.DemoService;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DubboConf {

    @Bean
    ApplicationConfig application(BuildProperties buildProperties) {
        ApplicationConfig app = new ApplicationConfig();
        app.setName(buildProperties.getName());
        return app;
    }

    @Bean
    RegistryConfig registry() {
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181?client=curator");
        return registry;
    }

    @Bean
    ReferenceConfig<DemoService> doubleDemoService(
            ApplicationConfig application,
            RegistryConfig registry
    ) {
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(DemoService.class);
        reference.setVersion("1.0.0");
        return reference;
    }


}
