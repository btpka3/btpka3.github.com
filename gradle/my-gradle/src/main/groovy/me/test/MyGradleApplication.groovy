package me.test

//import org.grails.datastore.gorm.mongo.bean.factory.MongoDatastoreFactoryBean
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@SpringBootApplication
class MyGradleApplication {

    static void main(String[] args) {
        println "111222"
        ConfigurableApplicationContext appContext = SpringApplication.run MyGradleApplication, args
        //println appContext.getBean(MongoDatastoreFactoryBean).config
        println "223344"
    }

//    @Bean
//    MongoMappingContext springDataMongoMappingContext() {
//        return new MongoMappingContext()
//    }
}
