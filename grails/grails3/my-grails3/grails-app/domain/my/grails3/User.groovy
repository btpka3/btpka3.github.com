package my.grails3

class User {

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
