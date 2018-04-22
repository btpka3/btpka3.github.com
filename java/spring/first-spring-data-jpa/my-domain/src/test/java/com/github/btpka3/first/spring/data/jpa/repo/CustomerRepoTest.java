package com.github.btpka3.first.spring.data.jpa.repo;

import com.github.btpka3.first.spring.data.jpa.BaseTest;
import com.github.btpka3.first.spring.data.jpa.domain.Customer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerRepoTest extends BaseTest {

    @Autowired
    CustomerRepo customerRepo;

    @Test
    public void test01() {


        Customer customer = customerRepo.getOne(37);

        assertThat(customer).isNotNull();


        assertThat(customer.getCustomerId())
                .isEqualTo(37);

        assertThat(customer.getStore().getStoreId())
                .isEqualTo(1);

        assertThat(customer.getFirstName())
                .isEqualTo("PAMELA");

        assertThat(customer.getLastName())
                .isEqualTo("BAKER");

        assertThat(customer.getEmail())
                .isEqualTo("PAMELA.BAKER@sakilacustomer.org");

        assertThat(customer.getAddress().getAddressId())
                .isEqualTo(41);

        assertThat(customer.getActive())
                .isEqualTo(true);

        assertThat(customer.getCreateDate())
                .isNotNull();

        assertThat(customer.getLastUpdate())
                .isNotNull();
    }


}
