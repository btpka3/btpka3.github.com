package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

// 注意：这里不能用自己类的静态常量
@ToString(exclude = {"city"})
@Data
@Entity
@Table(name = "address")
public class Address {

    public static final String C_ADDRESS_ID = "address_id";
    public static final String C_ADDRESS = "address";
    public static final String C_ADDRESS2 = "address2";
    public static final String C_DISTRICT = "district";
    public static final String C_CITY_ID = "city_id";
    public static final String C_POSTAL_CODE = "postal_code";
    public static final String C_PHONE = "phone";
    public static final String C_LAST_UPDATE = "last_update";

    public static final String A_ADDRESS_ID = "addressId";
    public static final String A_ADDRESS = "address";
    public static final String A_ADDRESS2 = "address2";
    public static final String A_DISTRICT = "district";
    public static final String A_CITY = "city";
    public static final String A_POSTAL_CODE = "postalCode";
    public static final String A_PHONE = "phone";
    public static final String A_LAST_UPDATE = "lastUpdate";

    @Id
    @Column(name = C_ADDRESS_ID)
    private Integer addressId;

    @Column(name = C_ADDRESS)
    private String address;

    @Column(name = C_ADDRESS2)
    private String address2;

    @Column(name = C_DISTRICT)
    private String district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = C_CITY_ID)
    private City city;

    @Column(name = C_POSTAL_CODE)
    private String postalCode;

    @Column(name = C_PHONE)
    private String phone;

    @Column(name = C_LAST_UPDATE)
    private Date lastUpdate;


}
