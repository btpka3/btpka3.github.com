/*
 * This file is generated by jOOQ.
 */
package com.github.btpka3.first.spring.jooq.domain.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    private Short         cityId;
    private String        city;
    private Short         countryId;
    private LocalDateTime lastUpdate;

    public City() {}

    public City(City value) {
        this.cityId = value.cityId;
        this.city = value.city;
        this.countryId = value.countryId;
        this.lastUpdate = value.lastUpdate;
    }

    public City(
        Short         cityId,
        String        city,
        Short         countryId,
        LocalDateTime lastUpdate
    ) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for <code>sakila.city.city_id</code>.
     */
    public Short getCityId() {
        return this.cityId;
    }

    /**
     * Setter for <code>sakila.city.city_id</code>.
     */
    public City setCityId(Short cityId) {
        this.cityId = cityId;
        return this;
    }

    /**
     * Getter for <code>sakila.city.city</code>.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Setter for <code>sakila.city.city</code>.
     */
    public City setCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * Getter for <code>sakila.city.country_id</code>.
     */
    public Short getCountryId() {
        return this.countryId;
    }

    /**
     * Setter for <code>sakila.city.country_id</code>.
     */
    public City setCountryId(Short countryId) {
        this.countryId = countryId;
        return this;
    }

    /**
     * Getter for <code>sakila.city.last_update</code>.
     */
    public LocalDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    /**
     * Setter for <code>sakila.city.last_update</code>.
     */
    public City setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("City (");

        sb.append(cityId);
        sb.append(", ").append(city);
        sb.append(", ").append(countryId);
        sb.append(", ").append(lastUpdate);

        sb.append(")");
        return sb.toString();
    }
}