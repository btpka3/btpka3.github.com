package com.github.btpka3.first.spring.data.jpa;

import com.github.btpka3.first.spring.data.jpa.domain.Country;
import com.github.btpka3.first.spring.data.jpa.domain.QCountry;
import com.github.btpka3.first.spring.data.jpa.repo.CountryRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@SpringBootApplication
public class MyJpaApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = SpringApplication.run(MyJpaApp.class, args);
        Country c = new Country();
        c.setCountryId(111);
        System.out.println(c);
        CountryRepo countryRepo = appCtx.getBean(CountryRepo.class);
        QCountry qCountry = QCountry.country1;
        System.out.println(countryRepo.findAll(allOf(
                qCountry.country.isNotNull()
        )));
    }

}
