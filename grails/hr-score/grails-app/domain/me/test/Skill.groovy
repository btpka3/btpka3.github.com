package me.test

class Skill {

    static constraints = {
    }
    static hasMany = [
            skillPoints: SkillPoint
    ]

    String id
    Date dateCreated
    Date lastUpdated

    String name

    /*

    {
        skillId


    }
    *
    * */

}
