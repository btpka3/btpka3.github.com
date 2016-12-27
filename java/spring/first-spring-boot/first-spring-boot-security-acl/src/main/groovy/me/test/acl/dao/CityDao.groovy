package me.test.acl.dao

import me.test.acl.domain.Area
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 */
interface CityDao extends JpaRepository<Area, Long> {

}