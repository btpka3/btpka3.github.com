package com.tc.his.api.model;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {
    private Long id;

    private String name;

    private Date birtyday;

    private static final long serialVersionUID = 1L;

    public Person(Long id, String name, Date birtyday) {
        this.id = id;
        this.name = name;
        this.birtyday = birtyday;
    }

    public Person() {
        super();
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
        this.name = name == null ? null : name.trim();
    }

    public Date getBirtyday() {
        return birtyday;
    }

    public void setBirtyday(Date birtyday) {
        this.birtyday = birtyday;
    }
}