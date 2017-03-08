package me.test.first.spring.boot.data.mongo.conf

import me.test.first.spring.boot.data.mongo.audit.MyAuditorAware
import me.test.first.spring.boot.data.mongo.repo.MyRepository
import me.test.first.spring.boot.data.mongo.repo.MyRepositoryImpl
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
}
