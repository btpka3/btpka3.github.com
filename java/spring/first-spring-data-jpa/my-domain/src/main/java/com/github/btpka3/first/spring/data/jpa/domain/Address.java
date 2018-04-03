package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@ToString
//@Entity(name="address")
public class Address {

    @Id
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "address")
    private String address;

    @Column(name = "address2")
    private String address2;

    @Column(name = "district")
    private String district;

    // TODO
    @Column(name = "city_id")
    private Integer cityId;


    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "last_update")
    private Date lastUpdate;

}
