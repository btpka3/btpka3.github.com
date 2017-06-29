package com.github.btpka3.first.spring.boot.data.elasticsearch.repo

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.data.repository.NoRepositoryBean

/**
 */
@NoRepositoryBean
interface MyRepository<T, ID extends Serializable>
        extends ElasticsearchRepository<T, ID> {

}