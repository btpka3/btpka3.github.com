package me.test.first.spring.boot.jersey.conf

import io.swagger.jaxrs.config.BeanConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry

// https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5#adding-the-dependencies-to-your-application
@Configuration
class SwaggerConf {


    @Bean
    BeanConfig swaggerBeanConfig() {
        BeanConfig config = new BeanConfig()
        config.setTitle("Swagger sample app")
        config.setVersion("1.0.0");
        config.setBasePath("/api");
        config.setSchemes(["https"] as String[]);
        config.setResourcePackage("me.test.first.spring.boot.jersey.resource")
        config.setScan(true)
        return config;
    }



//
//    @Bean
//    ServletRegistrationBean ServletRegistrationBean(HttpServlet swaggerServlet) {
//        ServletRegistrationBean regBean = new ServletRegistrationBean();
//
//        regBean.servlet = swaggerServlet
//        regBean.loadOnStartup = 2
//
//        return regBean
//    }
//
//    @Bean
//    JerseyJaxrsConfig jerseyJaxrsConfig() {
//
//        return new JerseyJaxrsConfig();
//
//        return new HttpServlet() {
//
//            @Override
//            void init(ServletConfig servletConfig) throws ServletException {
//                super.init(servletConfig)
//
//                println "----------============SwaggerConf"
//
//                BeanConfig config = new BeanConfig()
//                config.setTitle("Swagger sample app")
//                config.setVersion("1.0.0");
//                config.setBasePath("/api");
//                config.setSchemes(["https"] as String[]);
//                config.setResourcePackage("me.test.first.spring.boot.jersey.controller")
//                config.setScan(true)
//            }
//        }
//    }

//
//        Swagger swagger = new Swagger();
//
//        swagger.securityDefinition("api_key", new ApiKeyAuthDefinition("api_key", In.HEADER));
//
//        swagger.securityDefinition("petstore_auth", new OAuth2Definition()
//                .implicit("http://localhost:8002/oauth/dialog")
//                .scope("email", "Access to your email address")
//                .scope("pets", "Access to your pets"));
//
//        swagger.tag(new Tag()
//                .name("pet")
//                .description("Everything about your Pets")
//                .externalDocs(new ExternalDocs("Find out more", "http://swagger.io")));
//
//        swagger.tag(new Tag()
//                .name("store")
//                .description("Access to Petstore orders"))
//
//        swagger.tag(new Tag()
//                .name("user")
//                .description("Operations about user")
//                .externalDocs(new ExternalDocs("Find out more about our store", "http://swagger.io")));
//
//        new SwaggerContextService().updateSwagger(swagger);
//    }
}
