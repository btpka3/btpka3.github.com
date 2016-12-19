package me.test.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class MyService {

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
}