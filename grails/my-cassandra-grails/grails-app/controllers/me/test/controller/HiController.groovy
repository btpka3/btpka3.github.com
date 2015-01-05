package me.test.controller

import me.test.domain.User


class HiController {

    def index() {
        render "OK"
    }

    def insert(){

        def num = 0

        def u1 = new User()
        u1.id = "4"
        u1.sid= "sid${num}".toString()
        u1.name = "name${num}".toString()
        u1.tags=[
            "tag${num}.3".toString() ,
            "tag${num}.4".toString()
        ].toSet()
        u1.addrs = [
            "addr${num}.3" as String,
            "addr${num}.4" as String
        ]

        // 不能使用 GString
        u1.extra=[
            ("key"+num+".3") : "value"+num+".3",
            ("key${num}.4".toString())  : "value${num}.4".toString()
        ]
        u1.memo = "memo${num}".toString()
        u1.save()
        render "inserted"
    }

    def select(){
        def list= User.createCriteria().list { eq( "id","1") }
        println list[0].addrs

        render "select "+list
    }
}
