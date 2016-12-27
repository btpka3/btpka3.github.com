package me.test.db.dao

import me.test.db.domain.City
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 测试 spring-data-jpa
 */
interface City2Dao extends JpaRepository<City, Long> {

}