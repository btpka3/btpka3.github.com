package com.github.btpka3.first.spring.data.jpa.repo;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public class MyRepoImpl<T, ID extends Serializable>
        extends QuerydslJpaRepository<T, ID>
        implements MyRepo<T, ID> {

    public MyRepoImpl(
            JpaEntityInformation<T, ID> entityInformation,
            EntityManager entityManager
    ) {
        super(entityInformation, entityManager);
    }

    public MyRepoImpl(
            JpaEntityInformation<T, ID> entityInformation,
            EntityManager entityManager,
            EntityPathResolver resolver
    ) {
        super(entityInformation, entityManager, resolver);
    }
}
