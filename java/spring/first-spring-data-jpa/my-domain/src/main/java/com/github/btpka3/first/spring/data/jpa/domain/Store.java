package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "store")
public class Store {

    public static final String C_STORE_ID = "store_id";
    public static final String C_MANAGER_STAFF_ID = "manager_staff_id";
    public static final String C_ADDRESS_ID = "address_id";
    public static final String C_LAST_UPDATE = "last_update";


    @Id
    @Column(name = C_STORE_ID)
    private Integer storeId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name=C_MANAGER_STAFF_ID)
    private Staff manager;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name=C_ADDRESS_ID)
    private Address address;

    @Column(name = C_LAST_UPDATE)
    private Date lastUpdate;

}
