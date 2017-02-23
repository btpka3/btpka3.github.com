package me.test.first.spring.boot.gorm.mongo.controller

import me.test.first.spring.boot.gorm.mongo.domain.City
import me.test.first.spring.boot.gorm.mongo.domain.Street
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

//1. @org.springframework.transaction.annotation.Transactional  不管用
//2. @grails.transaction.Transactional  抛出异常 ： IllegalStateException: No transactionManager was specified
//3. `static transactional = 'mongo'` 不起作用
//4. `City.withTransaction {status -> ...}`  OK.

//import com.mongodb.rx.client.MongoClient
import org.springframework.web.bind.annotation.ResponseBody

//import org.grails.datastore.rx.mongodb.RxMongoDatastoreClient
@Controller
class MyTestController {


    static transactional = 'mongo'

//    @Autowired
//    MongoDatastore mongoDatastore

    @Autowired
    ApplicationContext applicationContext
//    @Autowired
//    MongoClient mongoClient

    /** 测试最基本情形 */
    @RequestMapping("/")
    @ResponseBody
    String home() {

//        new RxMongoDatastoreClient(
//                mongoClient,
//                "test",
//                applicationContext.getEnvironment(),
//                City
//        )
        return "home " + new Date();
    }

    @RequestMapping("/clear")
    @ResponseBody
    String clear() {
        City.withTransaction { status ->
            City.all*.delete();
        }

        return "clear " + new Date()
    }

    //@Transactional
    @RequestMapping("/add")
    @ResponseBody
    String add() {


        City city1 = new City();
        city1.username = "zhang3";
        city1.age = 12;

        city1.save(flush: true)


        City city2 = new City(username: "li4", age: 22, memo: "test");
        City.withTransaction { status ->
            city2.save()
        }

        City city3 = new City(username: "wang5", age: 33);
        city3.save()

        return "add " + new Date()
    }


    @RequestMapping("/add2")
    @ResponseBody
    String add2() {


        City city1 = new City();
        city1.username = "zhao6";
        city1.age = 11;
        city1.streetList = [
                new Street(name: "aaa"),
                new Street(name: "bbb")
        ]

        city1.save(flush: true)


        return "add2 " + new Date()
    }


    @RequestMapping("/err")
    @ResponseBody
    String err() {


        City city1 = new City();
        city1.age = 12;
        //city1.save(flush: true)
        city1.save(flush: true)

        return "err " + new Date()
    }


    @RequestMapping("/list")
    @ResponseBody
    String list() {
        return "list " + new Date() + City.all;
    }

}
