package me.test.first.spring.boot.data.mongo.repo

import me.test.first.spring.boot.data.mongo.domain.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 */
interface UserRepository extends MyRepository<User, ObjectId> {

}