package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@ToString
//@Entity(name="city")
public class City {

    @Id
    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "city")
    private String city;

    // TODO
    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "last_update")
    private Date lastUpdate;

}
