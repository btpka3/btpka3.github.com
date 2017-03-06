package me.test.first.spring.boot.cxf.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 配置 Swagger
 *
 * https://cxf.apache.org/docs/swaggerfeature-swagger2feature.html#SwaggerFeature/Swagger2Feature-EnablinginSpringBoot
 * https://github.com/apache/cxf/tree/master/distribution/src/main/release/samples/jax_rs/spring_boot
 */

//
@Configuration
class SwaggerConf {



    @Bean
    public Server rsServer() {

        Swagger2Feature feature = new Swagger2Feature();
        // customize some of the properties
        feature.setBasePath("/api");

        // add this feature to the endpoint (e.g., to ServerFactoryBean's features)
        ServerFactoryBean sfb = new ServerFactoryBean();
        sfb.getFeatures().add(feature);
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setAddress("/");
        // Register 2 JAX-RS root resources supporting "/sayHello/{id}" and "/sayHello2/{id}" relative paths
        endpoint.setServiceBeans(Arrays.<Object>asList(new HelloServiceImpl1(), new HelloServiceImpl2()));
        endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
        return endpoint.create();
    }
}
