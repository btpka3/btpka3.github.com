package me.test.dao

import me.test.domain.City
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 测试 spring-data-jpa
 */
interface City2Dao extends JpaRepository<City, Long> {

}