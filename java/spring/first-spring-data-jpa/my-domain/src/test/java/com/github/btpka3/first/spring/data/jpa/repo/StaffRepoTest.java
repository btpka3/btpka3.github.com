package com.github.btpka3.first.spring.data.jpa.repo;

import com.github.btpka3.first.spring.data.jpa.BaseTest;
import com.github.btpka3.first.spring.data.jpa.domain.Staff;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class StaffRepoTest extends BaseTest {

    @Autowired
    StaffRepo storeRepo;

    @Test
    public void test01() {

        Staff staff = storeRepo.getOne(1);

        assertThat(staff).isNotNull();

        assertThat(staff.getFirstName())
                .isEqualTo("Mike");

        assertThat(staff.getAddress().getPhone())
                .isEqualTo("14033335568");

        assertThat(staff.getPicture())
                .isNotEmpty();

        assertThat(staff.getStore().getStoreId())
                .isEqualTo(1);


        assertThat(staff.getStore().getAddress().getDistrict())
                .isEqualTo("Alberta");
    }


}
