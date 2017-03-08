package me.test.first.spring.boot.data.mongo.controller

import com.querydsl.mongodb.AbstractMongodbQuery
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

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Controller
@RequestMapping("/dsl")
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
}
