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


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
