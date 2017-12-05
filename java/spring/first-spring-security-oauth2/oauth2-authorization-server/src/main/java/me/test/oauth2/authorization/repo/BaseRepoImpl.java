package me.test.oauth2.authorization.repo;

import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.data.mongodb.repository.query.*;
import org.springframework.data.mongodb.repository.support.*;
import org.springframework.data.querydsl.*;

import java.io.*;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public class BaseRepoImpl<T, ID extends Serializable> extends QueryDslMongoRepository<T, ID> implements MongoRepository<T, ID>, BaseRepo<T, ID> {
    public BaseRepoImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations) {
        super(entityInformation, mongoOperations);

//        EntityPath<T> path = SimpleEntityPathResolver.INSTANCE.createPath(entityInformation.getJavaType());
//
//        this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
//        this.entityInformation = entityInformation;
//        this.mongoOperations = mongoOperations;
    }

    public BaseRepoImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations, EntityPathResolver resolver) {
        super(entityInformation, mongoOperations, resolver);

//        EntityPath<T> path = resolver.createPath(entityInformation.getJavaType());
//
//        this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
//        this.entityInformation = entityInformation;
//        this.mongoOperations = mongoOperations;
    }
}
