package com.github.btpka3.first.spring.data.jpa.repo;

import com.github.btpka3.first.spring.data.jpa.BaseTest;
import com.github.btpka3.first.spring.data.jpa.domain.Language;
import com.github.btpka3.first.spring.data.jpa.domain.Store;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreRepoTest extends BaseTest {

    @Autowired
    StoreRepo storeRepo;

    @Test
    public void test01() {

        Store store = storeRepo.getOne(1);

        assertThat(store).isNotNull();

        assertThat(store.getManager().getFirstName())
                .isEqualTo("Mike");

        assertThat(store.getAddress().getDistrict())
                .isEqualTo("Alberta");

    }



}
