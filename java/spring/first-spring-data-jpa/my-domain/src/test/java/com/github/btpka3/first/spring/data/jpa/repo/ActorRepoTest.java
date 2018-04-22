package com.github.btpka3.first.spring.data.jpa.repo;

import com.github.btpka3.first.spring.data.jpa.BaseTest;
import com.github.btpka3.first.spring.data.jpa.domain.Actor;
import com.github.btpka3.first.spring.data.jpa.domain.QActor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static org.assertj.core.api.Assertions.assertThat;

public class ActorRepoTest extends BaseTest {

    @Autowired
    ActorRepo actorRepo;

    @Test
    public void test01() {

        Page<Actor> actorPage = actorRepo.findAll(
                allOf(QActor.actor.actorId.lt(50)),
                PageRequest.of(1, 10, Sort.by(Sort.Order.asc(Actor.A_ACTOR_ID)))
        );

        assertThat(actorPage.getTotalElements()).isGreaterThan(0);
        assertThat(actorPage.getTotalPages()).isGreaterThan(1);

        List<Actor> actorList = actorPage.getContent();
        assertThat(actorList).isNotEmpty();
        System.out.println(actorPage);
        System.out.println(actorList);

    }

}
