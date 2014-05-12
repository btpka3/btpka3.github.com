package me.test.first.cache;

import javax.annotation.Generated;

/**
 * User is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class User {

    private Boolean gender;

    private Long id;

    private String name;

    private String signature;

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}

