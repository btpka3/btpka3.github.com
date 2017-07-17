package me.test.first.chanpay.conf;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.module.jaxb.*;
import me.test.first.chanpay.api.*;
import me.test.first.chanpay.api.impl.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.*;
import org.springframework.web.client.*;

/**
 *
 */
@Configuration
public class ChanpayApiConf {


    @Bean
    public RestTemplate chanpayRestTemplate(RestTemplateBuilder restTemplateBuilder) {

        RestTemplate restTemplate = restTemplateBuilder.build();


        ChanpayResponseErrorHandler errorHandler = new ChanpayResponseErrorHandler();
        if (restTemplate.getErrorHandler() != null) {
            errorHandler.setDelegate(restTemplate.getErrorHandler());
        }

        restTemplate.setErrorHandler(errorHandler);
        return restTemplate;
    }

    @Bean
    public ObjectMapper tradeApiObjectMapper(Jackson2ObjectMapperBuilder builder) {
        AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
        AnnotationIntrospector secondary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);

        return builder
                .createXmlMapper(false)
                .annotationIntrospector(pair)
                .build();
    }


    @Bean
    public TradeApi tradeApi(
            RestTemplate chanpayRestTemplate,
            ObjectMapper tradeApiObjectMapper
    ) {

        TradeApiImpl impl = new TradeApiImpl();
        impl.setRestTemplate(chanpayRestTemplate);
        impl.setObjectMapper(tradeApiObjectMapper);
        return impl;
    }

}
