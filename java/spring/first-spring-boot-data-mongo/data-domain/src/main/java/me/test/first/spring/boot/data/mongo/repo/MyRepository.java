package me.test.first.spring.boot.data.mongo.repo;

import com.querydsl.mongodb.AbstractMongodbQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.SpringDataMongodbQuery;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 保留该接口，方法统一追加自定义方法
 */
@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable>
        extends MongoRepository<T, ID>, QueryDslPredicateExecutor<T> {

    public abstract AbstractMongodbQuery<T, SpringDataMongodbQuery<T>> query();
}
