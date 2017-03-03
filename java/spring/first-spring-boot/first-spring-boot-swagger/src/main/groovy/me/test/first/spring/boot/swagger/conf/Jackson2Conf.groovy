package me.test.first.spring.boot.swagger.conf

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.FilterProvider
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import groovy.transform.CompileStatic
import me.test.first.spring.boot.swagger.model.MyFormData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.schema.configuration.ObjectMapperConfigured

/**
 *
 */
@Configuration
@CompileStatic
class Jackson2Conf {

//
//
//    @Autowired
//    void tett(FilterProvider filterProvider) {
//
//        MyFormData myForm = new MyFormData()
//        myForm.birthday = new Date()
//        myForm.height = 111
//        myForm.name = "zhang3"
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        println "================================ test json 1"
//        println objectMapper.writeValueAsString(myForm)
//
//
//        objectMapper.setFilterProvider(filterProvider);
//        println "================================ test json 2"
//        println objectMapper.writeValueAsString(myForm)
//
//    }

//    @Bean
//    Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(FilterProvider filterProvider) {
//        return new Jackson2ObjectMapperBuilderCustomizer() {
//            void customize(Jackson2ObjectMapperBuilder builder) {
//                builder.filters(filterProvider)
//            }
//        }
//    }


}
