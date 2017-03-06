package me.test.first.spring.boot.cxf.acl.domain

import groovy.transform.ToString

import javax.persistence.*

/**
 *
 */
@Entity
@ToString
class Area {

    @Id
    Long id;

    @Column(nullable = false)
    String name;

    @Version
    Date version;

    Date created;
    Date updated;

    @PrePersist
    protected void onCreate() {
        updated = created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }


}
