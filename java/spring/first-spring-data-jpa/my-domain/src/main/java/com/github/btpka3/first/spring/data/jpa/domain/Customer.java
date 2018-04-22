package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "customer")
public class Customer {


    public static final String C_CUSTOMER_ID = "customer_id";
    public static final String C_STORE_ID = "store_id";
    public static final String C_FIRST_NAME = "first_name";
    public static final String C_LAST_NAME = "last_name";
    public static final String C_EMAIL = "email";
    public static final String C_ADDRESS_ID = "address_id";
    public static final String C_ACTIVE = "active";
    public static final String C_CREATE_DATE = "create_date";
    public static final String C_LAST_UPDATE = "last_update";


    public static final String A_CUSTOMER_ID = "customerId";
    public static final String A_STORE = "store";
    public static final String A_FIRST_NAME = "firstName";
    public static final String A_LAST_NAME = "lastName";
    public static final String A_EMAIL = "email";
    public static final String A_ADDRESS = "address";
    public static final String A_ACTIVE = "active";
    public static final String A_CREATE_DATE = "createDate";
    public static final String A_LAST_UPDATE = "lastUpdate";


    @Id
    @Column(name = C_CUSTOMER_ID)
    private Integer customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = C_STORE_ID)
    private Store store;

    @Column(name = C_FIRST_NAME)
    private String firstName;

    @Column(name = C_LAST_NAME)
    private String lastName;

    @Column(name = C_EMAIL)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = C_ADDRESS_ID)
    private Address address;

    @Column(name = C_ACTIVE)
    private Boolean active;

    @Column(name = C_CREATE_DATE)
    private Date createDate;

    @Column(name = C_LAST_UPDATE)
    private Date lastUpdate;

}
