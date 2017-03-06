package me.test.first.spring.boot.cxf.db.dao

import me.test.first.spring.boot.cxf.db.domain.City
import org.springframework.stereotype.Repository

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Query

/**
 *
 */
@Repository
class CityDaoImpl implements CityDao {

    @PersistenceContext
    private EntityManager entityManager;

    City findById(Long id) {

        return entityManager.find(City, id)

    }

    List<City> all() {

        Query query = entityManager.createQuery('select c from City c')
        return query.getResultList()

    }

    void insert(City city){
        entityManager.persist(city);
    }

    void deleteAll(){
        Query query = entityManager.createNativeQuery("DELETE FROM City");
        query.executeUpdate()
    }
}
