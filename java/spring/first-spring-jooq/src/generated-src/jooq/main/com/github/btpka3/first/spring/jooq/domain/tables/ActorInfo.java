/*
 * This file is generated by jOOQ.
 */
package com.github.btpka3.first.spring.jooq.domain.tables;


import com.github.btpka3.first.spring.jooq.domain.Sakila;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * VIEW
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActorInfo extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sakila.actor_info</code>
     */
    public static final ActorInfo ACTOR_INFO = new ActorInfo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>sakila.actor_info.actor_id</code>.
     */
    public final TableField<Record, Short> ACTOR_ID = createField(DSL.name("actor_id"), SQLDataType.SMALLINT.nullable(false).defaultValue(DSL.inline("0", SQLDataType.SMALLINT)), this, "");

    /**
     * The column <code>sakila.actor_info.first_name</code>.
     */
    public final TableField<Record, String> FIRST_NAME = createField(DSL.name("first_name"), SQLDataType.VARCHAR(45).nullable(false), this, "");

    /**
     * The column <code>sakila.actor_info.last_name</code>.
     */
    public final TableField<Record, String> LAST_NAME = createField(DSL.name("last_name"), SQLDataType.VARCHAR(45).nullable(false), this, "");

    /**
     * The column <code>sakila.actor_info.film_info</code>.
     */
    public final TableField<Record, String> FILM_INFO = createField(DSL.name("film_info"), SQLDataType.CLOB, this, "");

    private ActorInfo(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private ActorInfo(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("VIEW"), TableOptions.view("create view `actor_info` as select `a`.`actor_id` AS `actor_id`,`a`.`first_name` AS `first_name`,`a`.`last_name` AS `last_name`,group_concat(distinct concat(`c`.`name`,': ',(select group_concat(`f`.`title` order by `f`.`title` ASC separator ', ') from ((`sakila`.`film` `f` join `sakila`.`film_category` `fc` on((`f`.`film_id` = `fc`.`film_id`))) join `sakila`.`film_actor` `fa` on((`f`.`film_id` = `fa`.`film_id`))) where ((`fc`.`category_id` = `c`.`category_id`) and (`fa`.`actor_id` = `a`.`actor_id`)))) order by `c`.`name` ASC separator '; ') AS `film_info` from (((`sakila`.`actor` `a` left join `sakila`.`film_actor` `fa` on((`a`.`actor_id` = `fa`.`actor_id`))) left join `sakila`.`film_category` `fc` on((`fa`.`film_id` = `fc`.`film_id`))) left join `sakila`.`category` `c` on((`fc`.`category_id` = `c`.`category_id`))) group by `a`.`actor_id`,`a`.`first_name`,`a`.`last_name`"));
    }

    /**
     * Create an aliased <code>sakila.actor_info</code> table reference
     */
    public ActorInfo(String alias) {
        this(DSL.name(alias), ACTOR_INFO);
    }

    /**
     * Create an aliased <code>sakila.actor_info</code> table reference
     */
    public ActorInfo(Name alias) {
        this(alias, ACTOR_INFO);
    }

    /**
     * Create a <code>sakila.actor_info</code> table reference
     */
    public ActorInfo() {
        this(DSL.name("actor_info"), null);
    }

    public <O extends Record> ActorInfo(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, ACTOR_INFO);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Sakila.SAKILA;
    }

    @Override
    public ActorInfo as(String alias) {
        return new ActorInfo(DSL.name(alias), this);
    }

    @Override
    public ActorInfo as(Name alias) {
        return new ActorInfo(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ActorInfo rename(String name) {
        return new ActorInfo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ActorInfo rename(Name name) {
        return new ActorInfo(name, null);
    }
}