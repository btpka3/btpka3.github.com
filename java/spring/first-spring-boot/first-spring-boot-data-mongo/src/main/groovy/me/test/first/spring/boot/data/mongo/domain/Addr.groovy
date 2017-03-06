package me.test.first.spring.boot.data.mongo.domain

import org.bson.types.ObjectId
import org.joda.time.DateTime
import org.springframework.data.annotation.*
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Addr {

    //============================ 共通字段
    @Id
    String id //= new ObjectId();

    /** 创建时间 */
    // 注意：可以使用 joda-time
    @CreatedDate
    DateTime dateCreated

    /** 创建者的ID */
    @CreatedBy
    String createdBy

    @LastModifiedDate
    Date lastUpdated

    /** 最后更新者的ID */
    @LastModifiedBy
    String lastModifiedBy

    /** 是否已经逻辑删除 */
    boolean delete = false

    //============================ 业务字段
    String name

    /** 测试内嵌 */
    List<Street> streetList = []

    // 内嵌文档，个人建议（1）使用用inner class （2）不要加上 @Document 注解（3）必要时加上 @Indexed 注解。
    //@Document
    static class Street {

        /** 原则上，内嵌的列表对象也应当有个id，以方便更新 */
        ObjectId id = new ObjectId();

        @Indexed
        String name
        String memo
    }

}
