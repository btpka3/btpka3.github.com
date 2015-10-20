package me.test

// 能力
class SkillGrade {

    static belongsTo = [
            skill: Skill
    ]

    static constraints = {
    }
    static embedded = ['skillPoints']
    static hasMany = [
            skillPoints: SkillPoint
    ]
    static mappedBy = [
            skillPoints: null
    ]

    String id
    Date dateCreated
    Date lastUpdated

    String name
    SkillLevel skillLevel

}
