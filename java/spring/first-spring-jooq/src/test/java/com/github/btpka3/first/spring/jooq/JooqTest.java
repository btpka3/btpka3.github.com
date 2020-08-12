package com.github.btpka3.first.spring.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

import static com.github.btpka3.first.spring.jooq.domain.Tables.ADDRESS;
import static org.jooq.impl.DSL.count;

/**
 * @author dangqian.zll
 * @date 2020-02-02
 */
@SpringBootTest(
        classes = JooqTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class JooqTest {

    @SpringBootApplication
    @EnableCaching
    @EnableScheduling
    public static class Conf {


    }

    @Autowired
    private DSLContext create;


    @Test
    public void select01() {

        List<Result<Record>> list = this.create.selectFrom(ADDRESS)
                .where(ADDRESS.CITY_ID.eq((short) 300))
                .offset(0)
                .limit(10)
                .fetchMany();
        System.out.println(list);


    }


    @Test
    public void selectGroupBy01() {

        List<Result<Record>> list = this.create.select(ADDRESS.CITY_ID, count())
                .from(ADDRESS)
                .where(ADDRESS.CITY_ID.ge((short) 300))
                .groupBy(ADDRESS.CITY_ID)
                .offset(0)
                .limit(10)
                .fetchMany();
        System.out.println(list);
    }

    @Test
    public void testUnion01() {

        List<Result<Record>> list = this.create.selectFrom(ADDRESS)
                .where(ADDRESS.CITY_ID.eq((short) 300))
                .unionAll(
                        this.create.selectFrom(ADDRESS)
                                .where(ADDRESS.CITY_ID.eq((short) 301))
                )
                .fetchMany();
        System.out.println(list);


    }
    // TODO 大数据量读取
    // TODO 打印拼接好的 sql
    // TODO 注册自定义函数
    // TODO Record 转换为 Domain bean 或者自定义POJO
}
