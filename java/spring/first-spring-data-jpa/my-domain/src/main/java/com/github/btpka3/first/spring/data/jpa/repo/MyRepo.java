package com.github.btpka3.first.spring.data.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 保留该接口，方法统一追加自定义方法
 */
@NoRepositoryBean
public interface MyRepo<T, ID extends Serializable>
        extends
        JpaRepository<T, ID>,
        JpaSpecificationExecutor<T>,
        QuerydslPredicateExecutor<T> {

}
