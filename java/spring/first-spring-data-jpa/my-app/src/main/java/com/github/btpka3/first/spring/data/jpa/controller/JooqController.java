package com.github.btpka3.first.spring.data.jpa.controller;


import com.github.btpka3.first.spring.data.jpa.domain.QCountry;
import com.github.btpka3.first.spring.data.jpa.repo.CountryRepo;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.querydsl.core.types.dsl.Expressions.allOf;

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


        return this.dsl.selectFrom(AUTHOR)
                .where(AUTHOR.DATE_OF_BIRTH.greaterThan(new GregorianCalendar(1980, 0, 1)))
                .fetch(AUTHOR.DATE_OF_BIRTH);

    }
}