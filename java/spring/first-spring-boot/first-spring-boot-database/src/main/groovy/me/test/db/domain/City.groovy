package me.test.db.domain

import groovy.transform.ToString

import javax.persistence.*

/**
 *
 */
@Entity
@ToString
class City {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String state;

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
