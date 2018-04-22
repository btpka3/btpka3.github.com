package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 演员
 */
@Data
@Entity
@Table(name = "actor")
public class Actor {

    public static final String C_ACTOR_ID = "actor_id";
    public static final String C_FIRST_NAME = "first_name";
    public static final String C_LAST_NAME = "last_name";
    public static final String C_LAST_UPDATE = "last_update";

    public static final String A_ACTOR_ID = "actorId";
    public static final String A_FIRST_NAME = "firstName";
    public static final String A_LAST_NAME = "lastName";
    public static final String A_LAST_UPDATE = "lastUpdate";


    @Id
    @Column(name = C_ACTOR_ID)
    private Integer actorId;

    @Column(name = C_FIRST_NAME)
    private String firstName;

    @Column(name = C_LAST_NAME)
    private String lastName;

    @Column(name = C_LAST_UPDATE)
    private Date lastUpdate;

}
