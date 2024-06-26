/*
 * This file is generated by jOOQ.
 */
package com.github.btpka3.first.spring.jooq.domain.tables;


import com.github.btpka3.first.spring.jooq.domain.Keys;
import com.github.btpka3.first.spring.jooq.domain.Sakila;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
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
public class Category extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sakila.category</code>
     */
    public static final Category CATEGORY = new Category();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>sakila.category.category_id</code>.
     */
    public final TableField<Record, Byte> CATEGORY_ID = createField(DSL.name("category_id"), SQLDataType.TINYINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>sakila.category.name</code>.
     */
    public final TableField<Record, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(25).nullable(false), this, "");

    /**
     * The column <code>sakila.category.last_update</code>.
     */
    public final TableField<Record, LocalDateTime> LAST_UPDATE = createField(DSL.name("last_update"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    private Category(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Category(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>sakila.category</code> table reference
     */
    public Category(String alias) {
        this(DSL.name(alias), CATEGORY);
    }

    /**
     * Create an aliased <code>sakila.category</code> table reference
     */
    public Category(Name alias) {
        this(alias, CATEGORY);
    }

    /**
     * Create a <code>sakila.category</code> table reference
     */
    public Category() {
        this(DSL.name("category"), null);
    }

    public <O extends Record> Category(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, CATEGORY);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Sakila.SAKILA;
    }

    @Override
    public Identity<Record, Byte> getIdentity() {
        return (Identity<Record, Byte>) super.getIdentity();
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_CATEGORY_PRIMARY;
    }

    @Override
    public Category as(String alias) {
        return new Category(DSL.name(alias), this);
    }

    @Override
    public Category as(Name alias) {
        return new Category(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Category rename(String name) {
        return new Category(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Category rename(Name name) {
        return new Category(name, null);
    }
}
