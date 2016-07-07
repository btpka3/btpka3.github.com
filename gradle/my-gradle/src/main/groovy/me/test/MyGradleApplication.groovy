package me.test

//import org.grails.datastore.gorm.mongo.bean.factory.MongoDatastoreFactoryBean
//import grails.mongodb.bootstrap.MongoDbDataStoreSpringInitializer
import grails.orm.bootstrap.HibernateDatastoreSpringInitializer
import me.test.domain.User;

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
		
//		def initializer1 = new HibernateDatastoreSpringInitializer(User)
//		initializer1.configureForBeanDefinitionRegistry(appContext)
		
//		def initializer2 = new MongoDbDataStoreSpringInitializer(appContext.getEnvironment(), User)
//		initializer2.configureForBeanDefinitionRegistry(appContext)

        //println appContext.getBean(MongoDatastoreFactoryBean).config
        println "223344"
    }

//    @Bean
//    MongoMappingContext springDataMongoMappingContext() {
//        return new MongoMappingContext()
//    }
}
