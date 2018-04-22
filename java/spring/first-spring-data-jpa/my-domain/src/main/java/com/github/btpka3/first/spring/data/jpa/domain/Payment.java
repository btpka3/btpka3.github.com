package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private Integer paymentId;

    // TODO
    @Column(name = "customer_id")
    private Integer customerId;

    // TODO
    @Column(name = "staff_id")
    private Integer staffId;

    // TODO
    @Column(name = "rental_id")
    private Integer rentalId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_date")
    private Date paymentDate;


    @Column(name = "last_update")
    private Date lastUpdate;
}
