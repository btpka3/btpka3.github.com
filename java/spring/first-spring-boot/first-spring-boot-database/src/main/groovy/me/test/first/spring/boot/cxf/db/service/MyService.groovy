package me.test.first.spring.boot.cxf.db.service

import me.test.first.spring.boot.cxf.db.dao.City2Dao
import me.test.first.spring.boot.cxf.db.dao.CityDao
import me.test.first.spring.boot.cxf.db.domain.City
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class MyService {

    // ------------------------------- JdbcTemplate

    @Autowired
    JdbcTemplate jdbcTemplate;

    List<Map<String, Object>> testJdbcTemplate() {

        jdbcTemplate.execute("""
drop table IF EXISTS MY_USER
""")

        jdbcTemplate.execute("""
create table MY_USER(
    ID          IDENTITY,
    NAME        VARCHAR(255),
    BIRTHDAY    TIMESTAMP
)""")


        jdbcTemplate.batchUpdate("insert into MY_USER (NAME, BIRTHDAY) values (?, ?)", [
                ["zhang3", '2000-01-02 03:04:05.06'].toArray(),
                ["li4", '2001-01-02 03:04:05.06'].toArray(),
                ["wang5", new Date()].toArray()
        ])

        List<Map<String, Object>> recList = jdbcTemplate.queryForList("select * from MY_USER")
        return recList
    }

    // ------------------------------- JPA

    @Autowired
    CityDao cityDao

    @Transactional
    List<City> testJpa() {

        cityDao.deleteAll()

        City c1 = new City();
        c1.name = '郑州'
        c1.state = "河南省"
        cityDao.insert(c1)

        City c2 = new City();
        c2.name = '杭州'
        c2.state = "浙江省"
        cityDao.insert(c2)

        return cityDao.all()
    }


    // ------------------------------- spring-data-jpa

    @Autowired
    City2Dao city2Dao

    @Transactional
    List<City> testDataJpa() {

        city2Dao.deleteAll()

        City c1 = new City();
        c1.name = '威海'
        c1.state = "山东省"
        city2Dao.save(c1)

        City c2 = new City();
        c2.name = '广州'
        c2.state = "广东省"
        city2Dao.save(c2)

        return city2Dao.findAll()
    }

}