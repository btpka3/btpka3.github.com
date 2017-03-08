package me.test.first.spring.boot.data.mongo.domain;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Addr {
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Street> getStreetList() {
        return streetList;
    }

    public void setStreetList(List<Street> streetList) {
        this.streetList = streetList;
    }

    @Id
    private String id;
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
    /**
     * 是否已经逻辑删除
     */
    private boolean delete = false;
    private String name;
    /**
     * 测试内嵌
     */
    private List<Street> streetList = new ArrayList<Street>();

    public static class Street {
        public ObjectId getId() {
            return id;
        }

        public void setId(ObjectId id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        /**
         * 原则上，内嵌的列表对象也应当有个id，以方便更新
         */
        private ObjectId id = new ObjectId();
        @Indexed
        private String name;
        private String memo;
    }
}
