package me.test.oauth2.authorization.repo;

import org.springframework.data.mongodb.repository.*;
import org.springframework.data.querydsl.*;
import org.springframework.data.repository.*;

import java.io.*;

/**
 * 保留该接口，方法统一追加自定义方法
 */
@NoRepositoryBean
public interface BaseRepo<T, ID extends Serializable>
        extends MongoRepository<T, ID>, QueryDslPredicateExecutor<T> {
}
