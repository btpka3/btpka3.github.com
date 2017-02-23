package me.test.first.spring.boot.gorm.mongo.domain

import grails.gorm.annotation.Entity

//import grails.gorm.rx.mongodb.RxMongoEntity

@Entity
class City  //implements RxMongoEntity<City>
{

    // 限定该Domain智能适用MongoDB进行保存
    static mapWith = "mongo"
    static constraints = {
        memo nullable: true
    }
    static embedded = ['streetList']

    String id
    Date dateCreated
    Date lastUpdated

    String username
    int age
    String memo

    List<Street> streetList = []
}
