package com.github.btpka3.first.spring.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.github.btpka3.first.spring.jooq.domain.Tables.ADDRESS;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.VARCHAR;

/**
 * @author dangqian.zll
 * @date 2020-02-02
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class JooqTest {

    @Autowired
    private DSLContext dsl;

    @Test
    public void select01() {

        List<Result<Record>> list = this.dsl.selectFrom(ADDRESS)
                .where(ADDRESS.CITY_ID.eq((short) 300))
                .offset(0)
                .limit(10)
                .fetchMany();
        System.out.println(list);
    }

    /**
     * 只查询给定字段。
     */
    @Test
    public void select02() {

        List<Result<Record>> list = this.dsl.select(
                        ADDRESS.CITY_ID,
                        ADDRESS.ADDRESS_ID
                )
                .from(ADDRESS)
                .where(ADDRESS.CITY_ID.eq((short) 300))
                .offset(0)
                .limit(10)
                .fetchMany();
        System.out.println(list);
    }

    /**
     * 各种函数，case...when
     */
    @Test
    public void select03() {

        List<Result<Record>> list = this.dsl
                .select(
                        concat(trim(ADDRESS.CITY_ID.cast(VARCHAR(100))), trim(ADDRESS.ADDRESS_ID.cast(VARCHAR(100)))),
                        ADDRESS.CITY_ID,
                        ADDRESS.ADDRESS_ID
                )
                .from(ADDRESS)
                .where(ADDRESS.CITY_ID.eq((short) 300))
                .offset(0)
                .limit(10)
                .fetchMany();
        System.out.println("111111");
        System.out.println(list);
    }


    @Test
    public void selectGroupBy01() {

        List<Result<Record>> list = this.dsl.select(ADDRESS.CITY_ID, count())
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

        List<Result<Record>> list = this.dsl.selectFrom(ADDRESS)
                .where(ADDRESS.CITY_ID.eq((short) 300))
                .unionAll(
                        this.dsl.selectFrom(ADDRESS)
                                .where(ADDRESS.CITY_ID.eq((short) 301))
                )
                .fetchMany();
        System.out.println(list);
    }

    @Test
    public void printSql() {
        String sql = this.dsl.selectFrom(ADDRESS)
                .where(ADDRESS.CITY_ID.eq((short) 300))
                .unionAll(
                        this.dsl.selectFrom(ADDRESS)
                                .where(ADDRESS.CITY_ID.eq((short) 301))
                )
                .getSQL();
        System.out.println("sql = " + sql);
    }


    // join
    // TODO 大数据量读取
    // TODO 打印拼接好的 sql
    // TODO 注册自定义函数
    // TODO Record 转换为 Domain bean 或者自定义POJO
    @Test
    public void selectPojo() {
        List<Result<Record>> list = this.dsl
                .select(
                        ADDRESS.ADDRESS_ID.as("aid"),
                        ADDRESS.CITY_ID.as("cid"),
                        ADDRESS.PHONE.as("p"),
                        concat(concat(ADDRESS.ADDRESS_ID.cast(VARCHAR(100)), "|"), ADDRESS.PHONE).as("concat"),
                        when(ADDRESS.PHONE.isNull(), "nill")
                                .otherwise(ADDRESS.PHONE)
                                .as("case"),
                        jsonArray(ADDRESS.ADDRESS_ID, ADDRESS.CITY_ID).as("jsonArray")
                )
                .from(ADDRESS)
                .where(ADDRESS.CITY_ID.eq((short) 300))
                .fetchMany();
        System.out.println(list);

    }

}
