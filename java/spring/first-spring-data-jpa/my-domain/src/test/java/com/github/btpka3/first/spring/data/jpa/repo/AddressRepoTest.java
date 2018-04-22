package com.github.btpka3.first.spring.data.jpa.repo;

import com.github.btpka3.first.spring.data.jpa.BaseTest;
import com.github.btpka3.first.spring.data.jpa.domain.Actor;
import com.github.btpka3.first.spring.data.jpa.domain.Address;
import com.github.btpka3.first.spring.data.jpa.domain.QActor;
import com.github.btpka3.first.spring.data.jpa.domain.QAddress;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static org.assertj.core.api.Assertions.assertThat;

public class AddressRepoTest extends BaseTest {

    @Autowired
    AddressRepo addressRepo;

    @Test
    public void test01() {

        Page<Address> page = addressRepo.findAll(
                allOf(QAddress.address1.addressId.lt(50)),
                PageRequest.of(1, 10, Sort.by(Sort.Order.asc(Address.A_ADDRESS_ID)))
        );

        assertThat(page.getTotalElements()).isGreaterThan(0);
        assertThat(page.getTotalPages()).isGreaterThan(1);

        List<Address> list = page.getContent();
        assertThat(list).isNotEmpty();
        System.out.println(page);
        System.out.println(list);

    }

}
