package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @Column(name = "inventory_id")
    private Integer inventoryId;

    // TODO
    @Column(name = "film_id")
    private Integer film_id;

    @Column(name = "last_update")
    private Date lastUpdate;
}
