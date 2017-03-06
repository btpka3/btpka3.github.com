package me.test.first.spring.boot.data.mongo.repo

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.query.MongoEntityInformation
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository
import org.springframework.data.querydsl.EntityPathResolver

/**
 * 保留该接口，方法统一追加自定义方法
 *
 */
class MyRepositoryImpl<T, ID extends Serializable>
        extends QueryDslMongoRepository<T, ID>
        implements MongoRepository<T, ID> {

    MyRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations) {
        super(entityInformation, mongoOperations)
    }

    MyRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations, EntityPathResolver resolver) {
        super(entityInformation, mongoOperations, resolver)
    }
}