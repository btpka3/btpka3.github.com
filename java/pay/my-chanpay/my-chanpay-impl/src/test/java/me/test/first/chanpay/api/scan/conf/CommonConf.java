package me.test.first.chanpay.api.scan.conf;

import org.springframework.context.annotation.*;
import org.springframework.core.convert.converter.*;
import org.springframework.format.support.*;

import java.util.*;

/**
 *
 */
@Configuration
public class CommonConf {

//    @Bean
////    @Lazy
//    FormattingConversionService conversionService() {
//        FormattingConversionService conversionService = new DefaultFormattingConversionService();
//
//        //converters.forEach(c -> conversionService.addConverter(c));
//        return conversionService;
//    }

    @Bean
    FormattingConversionServiceFactoryBean FormattingConversionServiceFactoryBean(
            Set<Converter> converters
    ) {
        FormattingConversionServiceFactoryBean fac = new FormattingConversionServiceFactoryBean();
        fac.setConverters(converters);
        return fac;
    }


//    @Bean
//    UtProps utProps() {
//        return new UtProps();
//    }

}
