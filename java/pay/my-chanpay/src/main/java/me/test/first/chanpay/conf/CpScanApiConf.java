package me.test.first.chanpay.conf;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.module.jaxb.*;
import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.impl.*;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.*;
import org.springframework.web.client.*;

import java.time.*;

/**
 *
 */
@Configuration
@ComponentScan(basePackages="me.test.first.chanpay.api.scan.impl")
public class CpScanApiConf {

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
    DtoUtils dtoUtils(
            ObjectMapper tradeApiObjectMapper
    ) {
        DtoUtils u = new DtoUtils();
        u.setObjectMapper(tradeApiObjectMapper);
        return u;
    }

    @Bean
    SignUtils cpScanApiUtils() {
        SignUtils u = new SignUtils();
        return u;
    }

    @Bean
    ZoneId zoneId() {
        return ZoneId.of("Asia/Shanghai");
    }

    @Bean
    public CpScanApi cpScanApi(
            RestTemplate chanpayRestTemplate,
            ObjectMapper tradeApiObjectMapper,
            SignUtils cpScanApiUtils,
            DtoUtils dtoUtils
    ) {

        CpScanApiImpl impl = new CpScanApiImpl();
        impl.setRestTemplate(chanpayRestTemplate);
        impl.setObjectMapper(tradeApiObjectMapper);
//        impl.setCpScanApiUtils(cpScanApiUtils);
//        impl.setDtoUtils(dtoUtils);

        // test
        impl.setGatewayUrl("https://tpay.chanpay.com/mag-unify/gateway/receiveOrder.do");

        // prod
//        impl.setGatewayUrl("https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do");

        // test
        String merchantPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz/+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx/AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6+nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE+ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=";//t环境
        impl.setMerchantPrivateKey(merchantPrivateKey);

        String merchantPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDONXNe9xgdWykFwTLRLKWKmGQC6ZLp66tqLRoUlvjUJnwoej8aD+KUuimcOXpIh9XuTDEO0YYh/D5xtnEN+q2wvZzK3G2l+xEirCowE7CM388t/yplGdJMw81CSaUQUeAz/5NCwbXA8i8OTv8/h0kLIdO/omMD8aJKgpmtyJ3IEQIDAQAB";//t环境
        //private static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";// 生产环境
        impl.setMerchantPublicKey(merchantPublicKey);

        return impl;
    }

}
