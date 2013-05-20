package me.test.first.spring.jdo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class UserGroupMember implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long uid;
    private Long gid;
    private Long addedUid;
    private Date addedDate;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Long getAddedUid() {
        return addedUid;
    }

    public void setAddedUid(Long addedUid) {
        this.addedUid = addedUid;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

}
