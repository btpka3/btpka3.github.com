package com.github.btpka3.first.spring.data.jpa.repo;

import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.querydsl.EntityPathResolver;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public class MyRepoImpl<T, ID extends Serializable>
        extends QuerydslJpaPredicateExecutor<T>
        implements MyRepo<T, ID> {

    public MyRepoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager, EntityPathResolver resolver, CrudMethodMetadata metadata) {
        super(entityInformation, entityManager, resolver, metadata);
    }

    @Override
    public List<T> findAll() {
        return null;
    }
}
