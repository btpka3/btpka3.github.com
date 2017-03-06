package me.test.first.spring.boot.cxf.acl.dao

import me.test.first.spring.boot.cxf.acl.domain.Area
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 */
interface CityDao extends JpaRepository<Area, Long> {

}