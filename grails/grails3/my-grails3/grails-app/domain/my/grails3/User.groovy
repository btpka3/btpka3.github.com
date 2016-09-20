package my.grails3

class User {

    // 限定该Domain智能适用MongoDB进行保存
    static mapWith = "mongo"
    static constraints = {
        memo nullable: true
    }

    String id
    Date dateCreated
    Date lastUpdated


    String username
    int age
    String memo

}
