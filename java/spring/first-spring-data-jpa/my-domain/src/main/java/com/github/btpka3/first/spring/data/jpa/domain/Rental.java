package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @Column(name = "rental_id")
    private Integer rentalId;

    @Column(name = "rental_date")
    private Date rentalDate;

    // TODO
    @Column(name = "inventory_id")
    private Integer inventoryId;

    // TODO
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "return_date")
    private Date returnDate;

    // TODO
    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "last_update")
    private Date lastUpdate;
}
