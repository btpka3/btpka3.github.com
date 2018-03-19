package io.github.btpka3.font.crawler.conf;


import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 *
 */
@Configuration
public class JacksonConf {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
        return (Jackson2ObjectMapperBuilder builder) -> {
            AnnotationIntrospector secondary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
            AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
            AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
            builder.annotationIntrospector(pair);
        };
    }

    @Bean
    public XmlMapper xmlMapper(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        return (XmlMapper) jackson2ObjectMapperBuilder.createXmlMapper(true)
                .build();
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        return jackson2ObjectMapperBuilder.createXmlMapper(false)
                .build();
    }

}
