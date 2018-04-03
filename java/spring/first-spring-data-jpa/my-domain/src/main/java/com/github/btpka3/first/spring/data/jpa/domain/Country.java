package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name="country")
public class Country {

    @Id
    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "country")
    @Deprecated
    private String country;

    @Column(name = "last_update")
    private Date lastUpdate;

}
