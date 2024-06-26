/*
 * This file is generated by jOOQ.
 */
package com.github.btpka3.first.spring.jooq.domain.tables;


import com.github.btpka3.first.spring.jooq.domain.Indexes;
import com.github.btpka3.first.spring.jooq.domain.Keys;
import com.github.btpka3.first.spring.jooq.domain.Sakila;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Actor extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sakila.actor</code>
     */
    public static final Actor ACTOR = new Actor();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>sakila.actor.actor_id</code>.
     */
    public final TableField<Record, Short> ACTOR_ID = createField(DSL.name("actor_id"), SQLDataType.SMALLINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>sakila.actor.first_name</code>.
     */
    public final TableField<Record, String> FIRST_NAME = createField(DSL.name("first_name"), SQLDataType.VARCHAR(45).nullable(false), this, "");

    /**
     * The column <code>sakila.actor.last_name</code>.
     */
    public final TableField<Record, String> LAST_NAME = createField(DSL.name("last_name"), SQLDataType.VARCHAR(45).nullable(false), this, "");

    /**
     * The column <code>sakila.actor.last_update</code>.
     */
    public final TableField<Record, LocalDateTime> LAST_UPDATE = createField(DSL.name("last_update"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    private Actor(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Actor(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>sakila.actor</code> table reference
     */
    public Actor(String alias) {
        this(DSL.name(alias), ACTOR);
    }

    /**
     * Create an aliased <code>sakila.actor</code> table reference
     */
    public Actor(Name alias) {
        this(alias, ACTOR);
    }

    /**
     * Create a <code>sakila.actor</code> table reference
     */
    public Actor() {
        this(DSL.name("actor"), null);
    }

    public <O extends Record> Actor(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, ACTOR);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Sakila.SAKILA;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.ACTOR_IDX_ACTOR_LAST_NAME);
    }

    @Override
    public Identity<Record, Short> getIdentity() {
        return (Identity<Record, Short>) super.getIdentity();
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_ACTOR_PRIMARY;
    }

    @Override
    public Actor as(String alias) {
        return new Actor(DSL.name(alias), this);
    }

    @Override
    public Actor as(Name alias) {
        return new Actor(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Actor rename(String name) {
        return new Actor(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Actor rename(Name name) {
        return new Actor(name, null);
    }
}
