package me.test.first.spring.boot.data.mongo.domain

import me.test.first.spring.boot.data.mongo.core.MyGroovyTypeEnum
import org.bson.types.ObjectId
import org.joda.time.DateTime
import org.springframework.data.annotation.*
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class GroovyUser {


    @Id
//    @QueryType(PropertyType.STRING)
    ObjectId id;

    /**
     * 创建时间
     */
    @CreatedDate
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
    @Version
    Long version;

    /**
     * 是否已经逻辑删除
     */
    boolean delete = false;

    @Indexed
    String name;
    Integer age;
    String memo;

    MyGroovyTypeEnum type = MyGroovyTypeEnum.ONE

}