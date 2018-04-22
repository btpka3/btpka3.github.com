package com.github.btpka3.first.spring.data.jpa.repo;

import com.github.btpka3.first.spring.data.jpa.BaseTest;
import com.github.btpka3.first.spring.data.jpa.domain.Actor;
import com.github.btpka3.first.spring.data.jpa.domain.Film;
import com.github.btpka3.first.spring.data.jpa.domain.QActor;
import com.github.btpka3.first.spring.data.jpa.domain.QFilm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static org.assertj.core.api.Assertions.assertThat;

public class FilmRepoTest extends BaseTest {

    @Autowired
    FilmRepo filmRepo;

    @Test
    public void test01() {

        Page<Film> filmPage = filmRepo.findAll(
                allOf(QFilm.film.filmId.lt(100)),
                PageRequest.of(1, 10, Sort.by(Sort.Order.asc(Film.A_FILM_ID)))
        );

        assertThat(filmPage.getTotalElements()).isGreaterThan(0);
        assertThat(filmPage.getTotalPages()).isGreaterThan(1);

        List<Film> filmList = filmPage.getContent();
        assertThat(filmList).isNotEmpty();
        System.out.println(filmPage);
        System.out.println(filmList);

    }

}
