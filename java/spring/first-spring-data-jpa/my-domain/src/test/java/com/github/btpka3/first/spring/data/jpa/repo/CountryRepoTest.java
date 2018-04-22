package com.github.btpka3.first.spring.data.jpa.repo;

import com.github.btpka3.first.spring.data.jpa.BaseTest;
import com.github.btpka3.first.spring.data.jpa.domain.City;
import com.github.btpka3.first.spring.data.jpa.domain.Country;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryRepoTest extends BaseTest {

    @Autowired
    CountryRepo countryRepo;

    @Test
    public void test01() {

        Country country = new Country();
        country.setCountryId(999);
        country.setCountry("ccc");

        // 数据库中 TIMESTAMP 最小粒度是 秒
        country.setLastUpdate(new Date(3 * 1000));

        Country savedCountry = countryRepo.save(country);
        assertThat(savedCountry == country).isFalse();
        assertThat(savedCountry).isEqualTo(country);

        // queriedCountry 是一个代理类
        Country queriedCountry = countryRepo.getOne(999);
        assertThat(queriedCountry).isEqualTo(country);
        assertThat(queriedCountry.getCountryId()).isEqualTo(country.getCountryId());
        assertThat(queriedCountry.getCountry()).isEqualTo(country.getCountry());
        assertThat(queriedCountry.getLastUpdate().getTime()).isEqualTo(country.getLastUpdate().getTime());
    }


    /**
     * 一对多查询
     */
    @Test
    public void test02() {

        Country country = countryRepo.getOne(23);

        assertThat(country.getCityList()).isNotEmpty();
        List<City> cityList = country.getCityList();

        assertThat(cityList.stream()
                .anyMatch(city -> Objects.equals("Sanya", city.getCity())))
                .isTrue();

    }
}
