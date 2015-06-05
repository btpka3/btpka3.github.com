package my.grails3

import grails.converters.JSON

class HelloController {

    def mongo

    def myCalcService

    def index() {
        render "aaa " + System.currentTimeMillis()
    }

    def add() {
        render "myCalcService : " + myCalcService.add(1, 2) + ", " + System.currentTimeMillis()
    }


    def gsp() {
        println("hello" + System.currentTimeMillis())
        render(view: "gsp", model: [a: "aaa", b: "bbb", c: "ccc"])
    }

    def insert() {

        10.times {
            def u = new User(username: "zhang_" + it, age: 10 + it)
            u.save(flush: true)
        }
        render "insert OK " + System.currentTimeMillis()
    }


    def list() {

        def list = User.createCriteria().list {
            ge("age", 15)
        }
        render(list as JSON)
    }

    def insertMongo() {
        print "mongo = " + mongo

        10.times {
            def u = new User(username: "li_" + it, age: 20 + it)
            u.mongo.save(flush: true)
        }
        render "insert mongo OK " + System.currentTimeMillis()
    }

    def listMongo() {

        def list = User.mongo.createCriteria().list {
            ge("age", 25)
        }
        render(list as JSON)
    }

    def testMongo() {
        print mongo.getDB("test").aa.find()
        render "aaaaaaaaaa " + System.currentTimeMillis()
    }
}
