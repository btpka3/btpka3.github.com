/*
 * This file is generated by jOOQ.
 */
package com.github.btpka3.first.spring.jooq.domain.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Short         paymentId;
    private Short         customerId;
    private Byte          staffId;
    private Integer       rentalId;
    private BigDecimal    amount;
    private LocalDateTime paymentDate;
    private LocalDateTime lastUpdate;

    public Payment() {}

    public Payment(Payment value) {
        this.paymentId = value.paymentId;
        this.customerId = value.customerId;
        this.staffId = value.staffId;
        this.rentalId = value.rentalId;
        this.amount = value.amount;
        this.paymentDate = value.paymentDate;
        this.lastUpdate = value.lastUpdate;
    }

    public Payment(
        Short         paymentId,
        Short         customerId,
        Byte          staffId,
        Integer       rentalId,
        BigDecimal    amount,
        LocalDateTime paymentDate,
        LocalDateTime lastUpdate
    ) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.rentalId = rentalId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for <code>sakila.payment.payment_id</code>.
     */
    public Short getPaymentId() {
        return this.paymentId;
    }

    /**
     * Setter for <code>sakila.payment.payment_id</code>.
     */
    public Payment setPaymentId(Short paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    /**
     * Getter for <code>sakila.payment.customer_id</code>.
     */
    public Short getCustomerId() {
        return this.customerId;
    }

    /**
     * Setter for <code>sakila.payment.customer_id</code>.
     */
    public Payment setCustomerId(Short customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * Getter for <code>sakila.payment.staff_id</code>.
     */
    public Byte getStaffId() {
        return this.staffId;
    }

    /**
     * Setter for <code>sakila.payment.staff_id</code>.
     */
    public Payment setStaffId(Byte staffId) {
        this.staffId = staffId;
        return this;
    }

    /**
     * Getter for <code>sakila.payment.rental_id</code>.
     */
    public Integer getRentalId() {
        return this.rentalId;
    }

    /**
     * Setter for <code>sakila.payment.rental_id</code>.
     */
    public Payment setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
        return this;
    }

    /**
     * Getter for <code>sakila.payment.amount</code>.
     */
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * Setter for <code>sakila.payment.amount</code>.
     */
    public Payment setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Getter for <code>sakila.payment.payment_date</code>.
     */
    public LocalDateTime getPaymentDate() {
        return this.paymentDate;
    }

    /**
     * Setter for <code>sakila.payment.payment_date</code>.
     */
    public Payment setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    /**
     * Getter for <code>sakila.payment.last_update</code>.
     */
    public LocalDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    /**
     * Setter for <code>sakila.payment.last_update</code>.
     */
    public Payment setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Payment (");

        sb.append(paymentId);
        sb.append(", ").append(customerId);
        sb.append(", ").append(staffId);
        sb.append(", ").append(rentalId);
        sb.append(", ").append(amount);
        sb.append(", ").append(paymentDate);
        sb.append(", ").append(lastUpdate);

        sb.append(")");
        return sb.toString();
    }
}
