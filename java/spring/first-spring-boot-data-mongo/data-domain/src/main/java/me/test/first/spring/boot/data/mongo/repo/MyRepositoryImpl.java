package me.test.first.spring.boot.data.mongo.repo;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.mongodb.AbstractMongodbQuery;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.mongodb.repository.support.SpringDataMongodbQuery;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public class MyRepositoryImpl<T, ID extends Serializable>
        extends QueryDslMongoRepository<T, ID>
        implements MongoRepository<T, ID>,  MyRepository<T, ID> {

    public MyRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations) {
        super(entityInformation, mongoOperations);

        EntityPath<T> path = SimpleEntityPathResolver.INSTANCE.createPath(entityInformation.getJavaType());

        this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
        this.entityInformation = entityInformation;
        this.mongoOperations = mongoOperations;
    }

    public MyRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations, EntityPathResolver resolver) {
        super(entityInformation, mongoOperations, resolver);

        EntityPath<T> path = resolver.createPath(entityInformation.getJavaType());

        this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
        this.entityInformation = entityInformation;
        this.mongoOperations = mongoOperations;
    }

    public AbstractMongodbQuery<T, SpringDataMongodbQuery<T>> query() {
        return new SpringDataMongodbQuery<T>(mongoOperations, entityInformation.getJavaType());
    }

    private final PathBuilder<T> builder;
    private final EntityInformation<T, ID> entityInformation;
    private final MongoOperations mongoOperations;
}
