package me.test.db.dao

import me.test.db.domain.City

/**
 *
 */
interface CityDao {

    City findById(Long id)

    List<City> all()

    void insert(City city)

    void deleteAll()
}