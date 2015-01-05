package me.test.domain

class Cart {

    static embedded = ['addresses']
    static hasMany = [items : Item]
    static mapping = {
        userName index:true
    }

    String id
    String userName
    List addresses

}
