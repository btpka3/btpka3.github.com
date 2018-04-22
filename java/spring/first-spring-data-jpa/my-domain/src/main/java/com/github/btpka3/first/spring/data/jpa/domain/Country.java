package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "country")
public class Country {

    public static final String C_COUNTRY_ID = "country_id";
    public static final String C_COUNTRY = "country";
    public static final String C_LAST_UPDATE = "last_update";

    public static final String A_COUNTRY_ID = "countryId";
    public static final String A_COUNTRY = "lastUpdate";
    public static final String A_LAST_UPDATE = "last_update";
    public static final String A_CITY_LIST = "cityList";


    @Id
    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "country")
    private String country;

    @Column(name = "last_update")
    private Date lastUpdate;

    // mappedBy 是指在 City 实体类中有个 `country` 字段来指明关联关系
    @OneToMany(mappedBy = "country")
    private List<City> cityList;

}
