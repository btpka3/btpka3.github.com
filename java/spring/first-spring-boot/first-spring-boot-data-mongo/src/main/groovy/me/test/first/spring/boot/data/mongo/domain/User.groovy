package me.test.first.spring.boot.data.mongo.domain

import me.test.first.spring.boot.data.mongo.core.MyTypeEnum
import org.bson.types.ObjectId
import org.joda.time.DateTime
import org.springframework.data.annotation.*
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User {

    //============================ 共通字段
    @Id
    ObjectId id //= new ObjectId();

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

    @Version
    Long version;

    /** 是否已经逻辑删除 */
    boolean delete = false

    //============================ 业务字段

    // @Indexed 只针对启用了 @Document 的domain类有效
    @Indexed
    String name
    Integer age
    String memo
    MyTypeEnum type = MyTypeEnum.ONE

    // 使用外部独立表存储，不内嵌，延迟加载，防止为大量加载数据
    // {$ref:"addr", $id:ObjectId("abcdef0123...")}
    @DBRef(lazy = true)
    List<Addr> arrList = []
}
