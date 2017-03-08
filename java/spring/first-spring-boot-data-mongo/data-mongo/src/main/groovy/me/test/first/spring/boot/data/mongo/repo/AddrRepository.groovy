package me.test.first.spring.boot.data.mongo.repo

import me.test.first.spring.boot.data.mongo.domain.Addr
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 */
interface AddrRepository extends MyRepository<Addr, String> {

}