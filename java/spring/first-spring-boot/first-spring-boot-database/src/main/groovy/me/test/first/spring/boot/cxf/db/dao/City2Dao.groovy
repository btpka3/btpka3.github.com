package me.test.first.spring.boot.cxf.db.dao

import me.test.first.spring.boot.cxf.db.domain.City
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 测试 spring-data-jpa
 */
interface City2Dao extends JpaRepository<City, Long> {

}