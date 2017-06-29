package com.github.btpka3.first.spring.boot.data.elasticsearch.domain

import org.joda.time.DateTime
import org.springframework.data.annotation.*
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(
        indexName = "addr"
)

class Addr {

    @Id
    String id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Field(type = FieldType.Date, store = true)
    DateTime dateCreated;

    /**
     * 创建者的ID
     */
    @CreatedBy
    String createdBy;

    @LastModifiedDate
    Date lastUpdated;

    /**
     * 最后更新者的ID
     */
    @LastModifiedBy
    String lastModifiedBy;

    /**
     * 是否已经逻辑删除
     */
    boolean delete = false;

    String name;

    /**
     * 测试内嵌
     */
    List<Street> streetList = new ArrayList<Street>();

    static class Street {

        /**
         * 原则上，内嵌的列表对象也应当有个id，以方便更新
         */
        String id;
        String name;
        String memo;
    }
}
