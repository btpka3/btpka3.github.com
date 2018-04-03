package com.github.btpka3.first.spring.data.jpa.controller;


import com.github.btpka3.first.spring.data.jpa.domain.QCountry;
import com.github.btpka3.first.spring.data.jpa.repo.CountryRepo;
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
@RequestMapping("/dsl")
public class DslController {


    @Autowired
    private CountryRepo countryRepo;

    @RequestMapping("/list")
    @ResponseBody
    Object list() {

        return countryRepo.findAll(allOf(
                QCountry.country1.isNotNull()
        ), PageRequest.of(1, 2, Sort.by(new Sort.Order(Sort.Direction.DESC, "age"))));

    }
}