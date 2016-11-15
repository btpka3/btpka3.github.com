package my.gorm.hibernate

import groovy.transform.ToString

/** 用户 */
@ToString
class User {

    static constraints = {
    }


    static hasMany = [
            addresses: Address
    ]

    static mapping = {
//        id generator: 'uuid'
    }

//    UUID id
    Long version
    Date dateCreated
    Date lastUpdated

    /** 姓名 */
    String name

    /** 年龄 */
    Integer age // 个人建议使用封装类型，而不是 int

    /** 上次访问日期 */
    Date lastVisit

    /** 性别 */
    UserGenderEnum gender


}
