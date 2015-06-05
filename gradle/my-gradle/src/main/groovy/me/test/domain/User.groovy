package me.test.domain

import grails.persistence.Entity
import groovy.transform.ToString

@Entity
//@ToString(includeNames = true, includeFields = true)
class User {
    static constraints = {
        name blank: false, unique: true
        age nullable: true
    }

    String name
    Integer age
    Date dateCreated
    Date lastUpdated

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", version=" + version +
                '}';
    }
}
