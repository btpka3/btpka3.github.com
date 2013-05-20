package me.test.first.spring.jdo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
    private Date version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

}
