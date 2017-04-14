package me.test.first.spring.boot.data.mongo.domain;

import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;
import me.test.first.spring.boot.data.mongo.core.MyTypeEnum;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Document
public class User {
//    @QueryType(PropertyType.STRING)
    public ObjectId getId() {
        return id;
    }

//    @QueryType(PropertyType.STRING)
    public void setId(ObjectId id) {
        this.id = id;
    }

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean getDelete() {
        return delete;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public MyTypeEnum getType() {
        return type;
    }

    public void setType(MyTypeEnum type) {
        this.type = type;
    }

    public List<Addr> getArrList() {
        return arrList;
    }

    public void setArrList(List<Addr> arrList) {
        this.arrList = arrList;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Id
//    @QueryType(PropertyType.STRING)
    private ObjectId id;

    /**
     * 创建时间
     */
    @CreatedDate
    private DateTime dateCreated;

    /**
     * 创建者的ID
     */
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private Date lastUpdated;
    /**
     * 最后更新者的ID
     */
    @LastModifiedBy
    private String lastModifiedBy;
    @Version
    private Long version;
    /**
     * 是否已经逻辑删除
     */
    private boolean delete = false;
    @Indexed
    private String name;
    private Integer age;
    private String memo;

    //@QueryType(PropertyType.ENUM)
    private MyTypeEnum type = MyTypeEnum.ONE;

    @DBRef(lazy = true)
    private List<Addr> arrList = new ArrayList<Addr>();

    private Set<String> tags;

}
