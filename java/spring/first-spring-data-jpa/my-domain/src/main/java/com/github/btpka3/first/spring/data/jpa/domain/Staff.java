package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "staff")
public class Staff {

    public static final String C_STAFF_ID = "staff_id";
    public static final String C_FIRST_NAME = "first_name";
    public static final String C_LAST_NAME = "last_name";
    public static final String C_ADDRESS_ID = "address_id";
    public static final String C_PICTURE = "picture";
    public static final String C_EMAIL = "email";
    public static final String C_STORE_ID = "store_id";
    public static final String C_ACTIVE = "active";
    public static final String C_USERNAME = "username";
    public static final String C_PASSWORD = "password";

    public static final String C_LAST_UPDATE = "last_update";


    @Id
    @Column(name = C_STAFF_ID)
    private Integer staffId;

    @Column(name = C_FIRST_NAME)
    private String firstName;

    @Column(name = C_LAST_NAME)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = C_ADDRESS_ID)
    private Address address;

    @Column(name = C_PICTURE)
    private byte[] picture;

    @Column(name = C_EMAIL)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = C_STORE_ID)
    private Store store;

    @Column(name = C_ACTIVE)
    private Boolean active;

    @Column(name = C_USERNAME)
    private String username;

    @Column(name = C_PASSWORD)
    private String password;

    @Column(name = C_LAST_UPDATE)
    private Date lastUpdate;

}
