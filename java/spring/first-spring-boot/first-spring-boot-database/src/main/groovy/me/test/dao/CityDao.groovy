package me.test.dao

import me.test.domain.City

/**
 *
 */
interface CityDao {

    City findById(Long id)

    List<City> all()

    void insert(City city)

    void deleteAll()
}