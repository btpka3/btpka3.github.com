/*
 * This file is generated by jOOQ.
 */
package com.github.btpka3.first.spring.jooq.domain.tables.pojos;


import java.io.Serializable;


/**
 * VIEW
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CustomerList implements Serializable {

    private static final long serialVersionUID = 1L;

    private Short  id;
    private String name;
    private String address;
    private String zipCode;
    private String phone;
    private String city;
    private String country;
    private String notes;
    private Byte   sid;

    public CustomerList() {}

    public CustomerList(CustomerList value) {
        this.id = value.id;
        this.name = value.name;
        this.address = value.address;
        this.zipCode = value.zipCode;
        this.phone = value.phone;
        this.city = value.city;
        this.country = value.country;
        this.notes = value.notes;
        this.sid = value.sid;
    }

    public CustomerList(
        Short  id,
        String name,
        String address,
        String zipCode,
        String phone,
        String city,
        String country,
        String notes,
        Byte   sid
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.notes = notes;
        this.sid = sid;
    }

    /**
     * Getter for <code>sakila.customer_list.ID</code>.
     */
    public Short getId() {
        return this.id;
    }

    /**
     * Setter for <code>sakila.customer_list.ID</code>.
     */
    public CustomerList setId(Short id) {
        this.id = id;
        return this;
    }

    /**
     * Getter for <code>sakila.customer_list.name</code>.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>sakila.customer_list.name</code>.
     */
    public CustomerList setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Getter for <code>sakila.customer_list.address</code>.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Setter for <code>sakila.customer_list.address</code>.
     */
    public CustomerList setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Getter for <code>sakila.customer_list.zip code</code>.
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * Setter for <code>sakila.customer_list.zip code</code>.
     */
    public CustomerList setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    /**
     * Getter for <code>sakila.customer_list.phone</code>.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Setter for <code>sakila.customer_list.phone</code>.
     */
    public CustomerList setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Getter for <code>sakila.customer_list.city</code>.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Setter for <code>sakila.customer_list.city</code>.
     */
    public CustomerList setCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * Getter for <code>sakila.customer_list.country</code>.
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Setter for <code>sakila.customer_list.country</code>.
     */
    public CustomerList setCountry(String country) {
        this.country = country;
        return this;
    }

    /**
     * Getter for <code>sakila.customer_list.notes</code>.
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Setter for <code>sakila.customer_list.notes</code>.
     */
    public CustomerList setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Getter for <code>sakila.customer_list.SID</code>.
     */
    public Byte getSid() {
        return this.sid;
    }

    /**
     * Setter for <code>sakila.customer_list.SID</code>.
     */
    public CustomerList setSid(Byte sid) {
        this.sid = sid;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CustomerList (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(address);
        sb.append(", ").append(zipCode);
        sb.append(", ").append(phone);
        sb.append(", ").append(city);
        sb.append(", ").append(country);
        sb.append(", ").append(notes);
        sb.append(", ").append(sid);

        sb.append(")");
        return sb.toString();
    }
}