package com.github.btpka3.first.spring.data.jpa.controller;


import com.github.btpka3.first.spring.data.jpa.repo.CountryRepo;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.github.btpka3.first.spring.data.jpa.jooq.Tables.FILM;
import static org.jooq.impl.DSL.trueCondition;

/**
 *
 */
@Controller
@RequestMapping("/jooq")
public class JooqController {


    private DSLContext dsl;

    @Autowired
    private CountryRepo countryRepo;

    @RequestMapping("/list")
    @ResponseBody
    Object list() {
        return this.dsl.selectFrom(FILM)
                .where(FILM.TITLE.like("%A%"))
                .and(FILM.DESCRIPTION.like("%Touch%")
                        .or(FILM.DESCRIPTION.like("%Doc%"))
                )
                .orderBy(FILM.TITLE.asc())
                .limit(10)
                .offset(10 * 1)
                .fetch();
    }

    // https://www.jooq.org/doc/3.0/manual/sql-building/dynamic-sql/
    Condition dynamicSql() {

        Condition result = trueCondition();
        if (1 < 2) {
            result = result.and(FILM.DESCRIPTION.like("%Touch%"));
        }
        return result;
    }

    String toSql() {
        return this.dsl.selectFrom(FILM)
                .where(FILM.TITLE.like("%A%"))
                .and(FILM.DESCRIPTION.like("%Touch%")
                        .or(FILM.DESCRIPTION.like("%Doc%"))
                )
                .orderBy(FILM.TITLE.asc())
                .limit(10)
                .offset(10 * 1)
                .getSQL();
    }

}