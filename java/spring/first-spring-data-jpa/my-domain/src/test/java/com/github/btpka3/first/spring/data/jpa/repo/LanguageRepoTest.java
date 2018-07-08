package com.github.btpka3.first.spring.data.jpa.repo;

import com.github.btpka3.first.spring.data.jpa.BaseTest;
import com.github.btpka3.first.spring.data.jpa.domain.Address;
import com.github.btpka3.first.spring.data.jpa.domain.City;
import com.github.btpka3.first.spring.data.jpa.domain.Country;
import com.github.btpka3.first.spring.data.jpa.domain.Language;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LanguageRepoTest extends BaseTest {

    @Autowired
    LanguageRepo languageRepo;

    @Test
    public void test01() {

        Language language = languageRepo.getOne(1);

        assertThat(language).isNotNull();
        assertThat(language.getName())
                .isEqualTo("English");

    }



}
