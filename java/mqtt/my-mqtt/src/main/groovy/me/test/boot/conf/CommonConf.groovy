package me.test.boot.conf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.support.FormattingConversionServiceFactoryBean

/**
 *
 */
@Configuration
class CommonConf {

    // WebMvcAutoConfiguration$EnableWebMvcConfiguration#mvcConversionService() 已经提供了
    @Bean
    FormattingConversionServiceFactoryBean rabbitMqCs(
            Set<Converter> converters
    ) {
        FormattingConversionServiceFactoryBean fac = new FormattingConversionServiceFactoryBean();
        fac.setConverters(converters);
        return fac;
    }


}
