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
public class Language extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sakila.language</code>
     */
    public static final Language LANGUAGE = new Language();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>sakila.language.language_id</code>.
     */
    public final TableField<Record, Byte> LANGUAGE_ID = createField(DSL.name("language_id"), SQLDataType.TINYINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>sakila.language.name</code>.
     */
    public final TableField<Record, String> NAME = createField(DSL.name("name"), SQLDataType.CHAR(20).nullable(false), this, "");

    /**
     * The column <code>sakila.language.last_update</code>.
     */
    public final TableField<Record, LocalDateTime> LAST_UPDATE = createField(DSL.name("last_update"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    private Language(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Language(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>sakila.language</code> table reference
     */
    public Language(String alias) {
        this(DSL.name(alias), LANGUAGE);
    }

    /**
     * Create an aliased <code>sakila.language</code> table reference
     */
    public Language(Name alias) {
        this(alias, LANGUAGE);
    }

    /**
     * Create a <code>sakila.language</code> table reference
     */
    public Language() {
        this(DSL.name("language"), null);
    }

    public <O extends Record> Language(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, LANGUAGE);
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
        return Keys.KEY_LANGUAGE_PRIMARY;
    }

    @Override
    public Language as(String alias) {
        return new Language(DSL.name(alias), this);
    }

    @Override
    public Language as(Name alias) {
        return new Language(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Language rename(String name) {
        return new Language(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Language rename(Name name) {
        return new Language(name, null);
    }
}
