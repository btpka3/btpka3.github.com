package my.gorm.hibernate

import groovy.transform.ToString

/** 收货地址 */
@ToString(excludes=['user'])
class Address {

    static belongsTo = [
            user: User
    ]
    static constraints = {

    }


    static mapping = {
        //id generator: 'uuid'
    }

//    UUID id
    Long version
    Date dateCreated
    Date lastUpdated

    String province
    String city
    String street
    String userName
    String phone
}
