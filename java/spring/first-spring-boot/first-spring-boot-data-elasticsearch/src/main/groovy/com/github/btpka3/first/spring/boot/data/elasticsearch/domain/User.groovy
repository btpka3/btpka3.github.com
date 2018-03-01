package com.github.btpka3.first.spring.boot.data.elasticsearch.domain

import org.joda.time.DateTime
import org.springframework.data.annotation.*
import org.springframework.data.elasticsearch.annotations.*

@Document(
        indexName = "user"
)
public class User {

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

    @Version
    Long version;

    /**
     * 是否已经逻辑删除
     */
    boolean delete = false;

    @Field(type = FieldType.text, store = true)
    String name;

    Integer age;
    String memo;


    MyTypeEnum type = MyTypeEnum.ONE;

    List<Addr> arrList = new ArrayList<Addr>();

    Addr addr;

    Set<String> tags;

}
