package me.test

class SkillPoint {

    static constraints = {
    }

    static belongsTo = [
            skill: Skill
    ]

    String id
    Date dateCreated
    Date lastUpdated

    String name


}
