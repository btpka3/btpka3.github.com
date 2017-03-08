package me.test.first.spring.boot.data.mongo.repo

import me.test.first.spring.boot.data.mongo.domain.User
import org.bson.types.ObjectId
import org.mongodb.morphia.query.Sort
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


/**
 *
 */
interface UserRepository extends MyRepository<User, ObjectId> {

    //Page<User> findByAgeIn(Collection<Integer> ages, Pageable pageable, Sort sort)
    Page<User> findByAgeIn(Collection<Integer> ages, Pageable pageable)
}