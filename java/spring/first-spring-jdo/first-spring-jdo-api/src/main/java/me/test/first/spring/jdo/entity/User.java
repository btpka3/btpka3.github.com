package me.test.first.spring.jdo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    // auto increase
    private Long id;
    private String name;
    private Integer age;
    private Boolean male;
    // NOTICE: JDO not support Inputream or Blob as field
    // http://db.apache.org/jdo/field_types.html
    // http://docs.oracle.com/javase/1.5.0/docs/guide/jdbc/getstart/mapping.html
    private byte[] imgData;
    private Date birthday;
    private Date version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public byte[] getImgData() {
        return imgData;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

}
