package me.test.first.spring.boot.data.mongo.controller

import com.mongodb.DBRef
import com.mongodb.DBRefCodec
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.mongodb.AbstractMongodbQuery
import groovy.transform.CompileStatic
import me.test.first.spring.boot.data.mongo.domain.Addr
import me.test.first.spring.boot.data.mongo.domain.QAddr
import me.test.first.spring.boot.data.mongo.domain.QAddr_Street
import me.test.first.spring.boot.data.mongo.domain.QUser
import me.test.first.spring.boot.data.mongo.repo.AddrRepository
import me.test.first.spring.boot.data.mongo.repo.UserRepository
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.repository.support.SpringDataMongodbQuery
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import static com.querydsl.core.types.dsl.Expressions.allOf
import static com.querydsl.core.types.dsl.Expressions.anyOf

/**
 *
 */
@Controller
@RequestMapping("/dsl")
@CompileStatic
class DslController {


    @Autowired
    Morphia morphia

    @Autowired
    Datastore datastore;


    @Autowired
    MongoOperations operations;

    @Autowired
    AddrRepository addrRepo

    @Autowired
    UserRepository userRepo

    @RequestMapping("/list")
    @ResponseBody
    Object list() {

        AbstractMongodbQuery query = userRepo.query()
        AbstractMongodbQuery q = query.where(
                QUser.user.name.isNotNull(),
                QUser.user.age.in([13, 15, 16, 17, 18])
        )
                .offset(1)
                .limit(2)
                .orderBy(QUser.user.age.desc())

        println "--------------- : " + (q == query)

        return userRepo.findAll(allOf(
                QUser.user.name.isNotNull(),
                QUser.user.age.in([13, 15, 16, 17, 18])
        ), new PageRequest(1, 2, new Sort(new Sort.Order(Sort.Direction.DESC, "age"))))
    }

    // https://github.com/querydsl/querydsl/issues/324
    @RequestMapping("/anySimple")
    @ResponseBody
    Object anySimple() {
        return userRepo.findAll(allOf(
                //QUser.user.tags.any().eq("z3")
                QUser.user.tags.any().in("z3", 'b1', 'b2')
        ))
    }

    // https://github.com/querydsl/querydsl/issues/115
    @RequestMapping("/anyEmbedded")
    @ResponseBody
    Object anyEmbedded() {

//        AbstractMongodbQuery<Addr, SpringDataMongodbQuery<Addr>> query = addrRepo.query()
//        query.anyEmbedded(QAddr.addr.streetList, QAddr.addr).on(
//                QAddr_Street.street.name.in("street-2", "street-200")
//        )
//        List<Addr> addrList = query.fetchResults().getResults()
//        return addrList


        return addrRepo.findAll(allOf(
                //QUser.user.tags.any().eq("z3")
                QAddr.addr.streetList.any().name.in('street-1', 'street-99')
        ))
    }


    @RequestMapping("/anyEmbedded2")
    @ResponseBody
    Object anyEmbedded2() {

        AbstractMongodbQuery<Addr, SpringDataMongodbQuery<Addr>> query = addrRepo.query()
        query.anyEmbedded(QAddr.addr.streetList, null).on(
                QAddr_Street.street.name.in("street-2", "street-200")
        )
        List<Addr> addrList = query.fetchResults().getResults()
        return addrList
    }


    @RequestMapping("/anyEntity")
    @ResponseBody
    Object anyEntity() {

        // FIXME: NOT WORK
        return userRepo.findAll(allOf(
                QUser.user.arrList.any().name.eq('addr-1')
                //QUser.user.arrList.any().streetList.any().name.in('street-1', 'street-99')
        ))
    }

    @RequestMapping("/notIn")
    @ResponseBody
    Object notIn() {

        /*
        2017-04-17T16:33:50.030+0000 D COMMAND  [conn217] run command test.$cmd { count: "user", query: {
        addr: { $nin: [
            { _id: ObjectId('58f4589a5c11837273f4b117'),
                dateCreated: new Date(1492408474496),
                createdBy: "xxx",
                lastUpdated: new Date(1492408474496),
                lastModifiedBy: "xxx",
                delete: false, name: "addr-1",
                streetList: [
                    { _id: ObjectId('58f4589a5c11837273f4b114'), name: "street-1" },
                    { _id: ObjectId('58f4589a5c11837273f4b115'), name: "street-2" },
                    { _id: ObjectId('58f4589a5c11837273f4b116'), name: "street-3" } ] },
            { _id: ObjectId('58f4589a5c11837273f4b11c'), dateCreated: new Date(1492408474600), createdBy: "xxx", lastUpdated: new Date(1492408474600), lastModifiedBy: "xxx", delete: false, name: "addr-2", streetList: [ { _id: ObjectId('58f4589a5c11837273f4b119'), name: "street-21" }, { _id: ObjectId('58f4589a5c11837273f4b11a'), name: "street-22" }, { _id: ObjectId('58f4589a5c11837273f4b11b'), name: "street-23" } ] } ] } } }
         */

        List<Addr> addrList = addrRepo.findAll(allOf(
                QAddr.addr.name.in(['addr-1', 'addr-2'])
        )).toList()

        return userRepo.findAll(allOf(
                QUser.user.addr.notIn(addrList)
        ))
    }

    @RequestMapping("/notIn1")
    @ResponseBody
    Object notIn1() {

        /*
        2017-04-17T16:37:49.213+0000 I COMMAND  [conn219] command test.user command: count { count: "user", query: {
            addr: { $ne: { $ref: "addr", $id: ObjectId('58f4589a5c11837273f4b117') } } } } planSummary: COLLSCAN keyUpdates:0 writeConflicts:0 numYields:0 reslen:62 locks:{ Global: { acquireCount: { r: 2 } }, Database: { acquireCount: { r: 1 } }, Collection: { acquireCount: { r: 1 } } } protocol:op_query 0ms
         */

        List<Addr> addrList = addrRepo.findAll(allOf(
                QAddr.addr.name.in(['addr-1'])
        )).toList()

        return userRepo.findAll(allOf(
                QUser.user.addr.notIn(addrList)
        ))
    }

    // https://github.com/querydsl/querydsl/issues/2133
    @RequestMapping("/notIn2")
    @ResponseBody
    Object notIn2() {

        /*
       run command test.$cmd { count: "user", query: { $and: [ { addr: { $ne: { $ref: "addr", $id: ObjectId('58f4589a5c11837273f4b117') } } }, { addr: { $ne: { $ref: "addr", $id: ObjectId('58f4589a5c11837273f4b11c') } } } ] } }
         */

        List<Addr> addrList = addrRepo.findAll(allOf(
                QAddr.addr.name.in(['addr-1', 'addr-2'])
        )).toList()

        List<BooleanExpression> neAddrs = addrList.findResults { QUser.user.addr.ne(it) }.toList()

        return userRepo.findAll(allOf(
                allOf((BooleanExpression[]) neAddrs.toArray())
        ))

    }



//    @RequestMapping("/notIn2")
//    @ResponseBody
//    Object notIn2() {
//
//        MorphiaQuery<Addr> addrQuery = new MorphiaQuery<Addr>(
//                morphia, datastore, QAddr.addr);
//
//        println addrQuery.where(QAddr.addr.id.isNotNull()).fetchCount()
//
//        List<Addr> addrList = addrQuery
//                .where(QAddr.addr.name.in(['addr-1', 'addr-2']))
//                .fetch()
//        MorphiaQuery<User> query2 = new MorphiaQuery<User>(morphia, datastore, QUser.user);
//
//        List<User> userList = query2
//                .where(QUser.user.addr.notIn(addrList))
//                .fetch()
//        return userList
//    }
//
//    @RequestMapping("/notIn3")
//    @ResponseBody
//    Object notIn3() {
//
//        // FIMXE querydsl-mongodb 依赖的 morphia 版本太低了。
//        MongoClient mongoClient = new MongoClient()
//        //Mongo mongo = new Mongo();
//        Morphia morphia = new Morphia()
//        morphia.map(Addr);
//        morphia.map(User);
//        Datastore datastore = morphia.createDatastore(mongoClient, "test");
//
//        println "Add Count = " + datastore.getCount(Addr)
//
//        MorphiaQuery<Addr> addrQuery = new MorphiaQuery<Addr>(
//                morphia, datastore, QAddr.addr);
//        return addrQuery.fetch()
//    }
}




