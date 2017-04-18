package me.test.first.spring.boot.data.mongo.conf

import com.mongodb.MongoClient
import me.test.first.spring.boot.data.mongo.audit.MyAuditorAware
import me.test.first.spring.boot.data.mongo.repo.MyRepository
import me.test.first.spring.boot.data.mongo.repo.MyRepositoryImpl
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 *
 */
@Configuration
@EnableMongoRepositories(repositoryBaseClass = MyRepositoryImpl, basePackageClasses = MyRepository)
@EnableMongoAuditing
class MongoConf {

    @Bean
    MyAuditorAware myAuditorAware() {
        return new MyAuditorAware()
    }

    @Bean
    Morphia morphia() {
        Morphia morphia = new Morphia()
        morphia.mapPackage("me.test.first.spring.boot.data.mongo.domain")
        return morphia
    }

    @Bean
    Datastore datastore(
            Morphia morphia,
            MongoClient mongoClient
    ) {
        return morphia.createDatastore(mongoClient, "test")
    }
    // SpringDataMongodbQuery
    // QuerydslRepositorySupport
}
