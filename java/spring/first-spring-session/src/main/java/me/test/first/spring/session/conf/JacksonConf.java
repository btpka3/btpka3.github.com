package me.test.first.spring.session.conf;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.dataformat.xml.*;
import com.fasterxml.jackson.module.jaxb.*;
import org.springframework.boot.autoconfigure.jackson.*;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.*;

/**
 *
 */
@Configuration
public class JacksonConf {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {

            @Override
            public void customize(Jackson2ObjectMapperBuilder builder) {
                AnnotationIntrospector secondary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
                AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
                AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
                builder.annotationIntrospector(pair);
            }
        };
    }

    @Bean
    public XmlMapper xmlMapper(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        return (XmlMapper) jackson2ObjectMapperBuilder.createXmlMapper(true).build();
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        return jackson2ObjectMapperBuilder.createXmlMapper(false).build();
    }

}
