package me.test

class SkillSet {

    static constraints = {
    }
    static hasMany = [
            skills: Skill
    ]
    static mappedBy = [
            skills: null
    ]
    static embedded = [
            'skills'
    ]

    String id
    Date dateCreated
    Date lastUpdated


    String versionName


    static class Calc {
        SkillGrade grade
        Boolean required

    }
}
