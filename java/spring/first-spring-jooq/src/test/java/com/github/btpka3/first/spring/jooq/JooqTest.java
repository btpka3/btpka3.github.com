package com.github.btpka3.first.spring.jooq;

import com.github.btpka3.first.spring.jooq.domain.tables.pojos.Address;
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

    /**
     * 选择全部字段
     *
     * @see <a href="https://github.com/jOOQ/jOOQ/issues/16426">jOOQ does not use custom converter/binding for spatial types in dynamic queries</a>
     * @see <a href="https://blog.jooq.org/why-you-should-use-jooq-with-code-generation/">Why You Should Use jOOQ With Code Generation</a>
     */
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

        List<Result<Record>> list = this.dsl.select(ADDRESS.ADDRESS_ID).from(ADDRESS)
                .where(ADDRESS.CITY_ID.eq((short) 300))
                .unionAll(
                        this.dsl.select(ADDRESS.ADDRESS_ID).from(ADDRESS)
                                .where(ADDRESS.CITY_ID.eq((short) 301))
                )
                .fetchMany();
        System.out.println(list);
    }

    static class MyRec1 {
        private Short cityId;
        private int count;

        public Short getCityId() {
            return cityId;
        }

        public void setCityId(Short cityId) {
            this.cityId = cityId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    @Test
    public void testGroup() {
        List<MyRec1> list = this.dsl.select(
                        ADDRESS.ADDRESS_ID,
                        count()
                )
                .from(ADDRESS)
                .where(ADDRESS.CITY_ID.gt((short) 300))
                .groupBy(
                        ADDRESS.CITY_ID
                )
                .orderBy(ADDRESS.CITY_ID)
                .fetchInto(MyRec1.class);
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

    /**
     * @see <a href="https://www.jooq.org/doc/3.17/manual-single-page/#pojos">5.3.6. POJOs</a>
     */
    @Test
    public void selectIntoPojo() {
        Address result = this.dsl
                .select(
                        ADDRESS.ADDRESS_ID,
                        ADDRESS.ADDRESS_,
                        ADDRESS.ADDRESS2,
                        ADDRESS.DISTRICT,
                        ADDRESS.CITY_ID,
                        ADDRESS.POSTAL_CODE,
                        ADDRESS.PHONE,
                        ADDRESS.LOCATION,
                        ADDRESS.LAST_UPDATE
                )
                .from(ADDRESS)
                .limit(1)
                .fetchAny()
                .into(Address.class);
        System.out.println(result);

    }

}
