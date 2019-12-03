package com.github.btpka3.first.spring.data.jpa.repo;

import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.querydsl.EntityPathResolver;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public class BaseRepoImpl<T, ID extends Serializable>
        extends QuerydslJpaPredicateExecutor<T>
        implements BaseRepo<T, ID> {

    public BaseRepoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager, EntityPathResolver resolver, CrudMethodMetadata metadata) {
        super(entityInformation, entityManager, resolver, metadata);
    }

    // TODO add custom method impl
}
