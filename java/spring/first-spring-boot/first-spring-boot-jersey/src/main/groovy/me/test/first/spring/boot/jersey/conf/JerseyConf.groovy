package me.test.first.spring.boot.jersey.conf

import io.swagger.jaxrs.config.BeanConfig
import me.test.first.spring.boot.jersey.resource.MyExceptionMapper
import me.test.first.spring.boot.jersey.resource.Rest
import me.test.first.spring.boot.jersey.resource.err.ErrResource
import me.test.first.spring.boot.jersey.resource.file.FileResource
import me.test.first.spring.boot.jersey.resource.form.FormResource
import me.test.first.spring.boot.jersey.resource.user.UserResource
import org.glassfish.jersey.media.multipart.MultiPartFeature
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.ServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.core.MediaType

// 参考： JerseyAutoConfiguration
@Configuration
class JerseyConf {

    // https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5#hooking-up-swagger-core-in-your-application

    @Bean()
    ResourceConfig resourceConfig(BeanConfig swaggerBeanConfig, Rest rest) {

        println "---------------999999999999999"


        ResourceConfig resourceConfig = new ResourceConfig()

        // 强制 响应的contentType中追加 charset
        // http://stackoverflow.com/questions/5514087/jersey-rest-default-character-encoding/20569571
        resourceConfig.register(new ContainerResponseFilter() {
            @Override
            public void filter(ContainerRequestContext request, ContainerResponseContext response) {
                MediaType type = response.getMediaType();
                if (type != null) {
                    String contentType = type.toString();
                    if (!contentType.contains("charset")) {
                        contentType = contentType + ";charset=utf-8";
                        response.getHeaders().putSingle("Content-Type", contentType);
                    }
                }
            }
        })

        resourceConfig.register(MultiPartFeature)
        resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource)

        // TODO 根据文档，需要手动一一将RESTful controller 添加至此。
        // http://docs.spring.io/spring-boot/docs/1.5.1.RELEASE/reference/htmlsingle/#boot-features-jersey
//        resourceConfig.packages("me.test.first.spring.boot.jersey.resource")
        resourceConfig.register(Rest)
        resourceConfig.register(UserResource.class)
        resourceConfig.register(FormResource.class)
        resourceConfig.register(FileResource.class)
        resourceConfig.register(ErrResource.class)
        resourceConfig.register(MyExceptionMapper.class)

        resourceConfig.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        resourceConfig.property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
    }


}



