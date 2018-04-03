package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@ToString
//@Entity(name="customer")
public class Customer {

    @Id
    @Column(name = "customer_id")
    private Integer customerId;

    // TODO
    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    // TODO
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_update")
    private Date lastUpdate;

}
