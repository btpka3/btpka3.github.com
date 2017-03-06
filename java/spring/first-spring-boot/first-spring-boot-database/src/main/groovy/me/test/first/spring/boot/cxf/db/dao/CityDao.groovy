package me.test.first.spring.boot.cxf.db.dao

import me.test.first.spring.boot.cxf.db.domain.City

/**
 *
 */
interface CityDao {

    City findById(Long id)

    List<City> all()

    void insert(City city)

    void deleteAll()
}