package me.test.first.spring.boot.data.mongo.controller

import com.querydsl.mongodb.AbstractMongodbQuery
import groovy.transform.CompileStatic
import me.test.first.spring.boot.data.mongo.domain.QAddr
import me.test.first.spring.boot.data.mongo.domain.QUser
import me.test.first.spring.boot.data.mongo.repo.AddrRepository
import me.test.first.spring.boot.data.mongo.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import static com.querydsl.core.types.dsl.Expressions.allOf

/**
 *
 */
@Controller
@RequestMapping("/dsl")
@CompileStatic
class DslController {

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

//    // https://github.com/querydsl/querydsl/issues/115
//    @RequestMapping("/anyEntity")
//    @ResponseBody
//    Object anyEntity() {
//
//        AbstractMongodbQuery query = userRepo.query()
//
//
//        return userRepo.findAll(allOf(
//                QUser.user.tags.any().eq("a3")
//        ))
//    }
}




