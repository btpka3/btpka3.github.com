package com.github.btpka3.first.spring.data.jpa.repo;

import com.github.btpka3.first.spring.data.jpa.BaseTest;
import com.github.btpka3.first.spring.data.jpa.domain.Address;
import com.github.btpka3.first.spring.data.jpa.domain.City;
import com.github.btpka3.first.spring.data.jpa.domain.Country;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CityRepoTest extends BaseTest {

    @Autowired
    CityRepo cityRepo;

    @Test
    public void test01() {
        Country country = new Country();
        country.setCountryId(23);

        City city = new City();
        city.setCityId(999);
        city.setCity("CITY_999");
        city.setCountry(country);
        city.setLastUpdate(new Date(3 * 1000));
        cityRepo.save(city);

        City queriedCity = cityRepo.getOne(999);

        //assertThat(queriedCity).isEqualTo(city);
        assertThat(queriedCity.getCity())
                .isEqualTo(city.getCity());

        assertThat(queriedCity.getCityId())
                .isEqualTo(city.getCityId());

        assertThat(queriedCity.getLastUpdate().getTime())
                .isEqualTo(city.getLastUpdate().getTime());

        assertThat(queriedCity.getCountry())
                .isNotNull();

        assertThat(queriedCity.getCountry().getCountry())
                .isNotBlank();
        assertThat(queriedCity.getCountry().getLastUpdate())
                .isNotNull();
        assertThat(queriedCity.getCountry().getCityList())
                .isNotEmpty();
    }


    @Test
    public void test02() {

        City queriedCity = cityRepo.getOne(362);

        //assertThat(queriedCity).isEqualTo(city);
        assertThat(queriedCity.getCity())
                .isEqualTo("Nanyang");

        assertThat(queriedCity.getCountryId())
                .isEqualTo(23);

        Country country = queriedCity.getCountry();
        assertThat(country)
                .isNotNull();

        assertThat(country.getCountry())
                .isEqualTo("China");


        // FIXME: 既然既有简单列，又有对象列的映射，则对象列不能更新。
        // 那么，简单列更新且未保存时，会有不一致情况。 44 是印度。
        queriedCity.setCountryId(44);
        assertThat(queriedCity.getCountry().getCountry())
                .isEqualTo("China");

        try {
            Country c = new Country();
            // 日本
            c.setCountryId(50);
            queriedCity.setCountry(c);

            // 注意：这个字段不会用来更新，但不会抛错。
            cityRepo.save(queriedCity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test03() {

        City queriedCity = cityRepo.getOne(362);


        assertThat(queriedCity)
                .isNotNull();

        List<Address> addressList = queriedCity.getAddressList();

        assertThat(addressList)
                .isNotEmpty();

        Address address = addressList.get(0);

        assertThat(address.getDistrict())
                .isEqualTo("Henan");

    }

}
