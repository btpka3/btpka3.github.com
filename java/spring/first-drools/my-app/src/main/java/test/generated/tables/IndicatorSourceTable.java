/*
 * This file is generated by jOOQ.
*/
package test.generated.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.ULong;

import test.generated.Indexes;
import test.generated.Keys;
import test.generated.SmetaApp;
import test.generated.tables.records.IndicatorSourceTableRecord;


/**
 * 指标源表关系
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IndicatorSourceTable extends TableImpl<IndicatorSourceTableRecord> {

    private static final long serialVersionUID = 144311749;

    /**
     * The reference instance of <code>SMETA_APP.indicator_source_table</code>
     */
    public static final IndicatorSourceTable INDICATOR_SOURCE_TABLE = new IndicatorSourceTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IndicatorSourceTableRecord> getRecordType() {
        return IndicatorSourceTableRecord.class;
    }

    /**
     * The column <code>SMETA_APP.indicator_source_table.id</code>. 主键
     */
    public final TableField<IndicatorSourceTableRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.indicator_source_table.gmt_create</code>. 创建时间
     */
    public final TableField<IndicatorSourceTableRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.indicator_source_table.gmt_modified</code>. 修改时间
     */
    public final TableField<IndicatorSourceTableRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.indicator_source_table.source_table_id</code>. 源表主键
     */
    public final TableField<IndicatorSourceTableRecord, ULong> SOURCE_TABLE_ID = createField("source_table_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "源表主键");

    /**
     * The column <code>SMETA_APP.indicator_source_table.indicator_code</code>. 指标Code
     */
    public final TableField<IndicatorSourceTableRecord, String> INDICATOR_CODE = createField("indicator_code", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "指标Code");

    /**
     * The column <code>SMETA_APP.indicator_source_table.source_table_field</code>. 源表字段
     */
    public final TableField<IndicatorSourceTableRecord, String> SOURCE_TABLE_FIELD = createField("source_table_field", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "源表字段");

    /**
     * The column <code>SMETA_APP.indicator_source_table.field_type</code>. 字段类型
     */
    public final TableField<IndicatorSourceTableRecord, String> FIELD_TYPE = createField("field_type", org.jooq.impl.SQLDataType.VARCHAR(64), this, "字段类型");

    /**
     * The column <code>SMETA_APP.indicator_source_table.source_table_field_script</code>. 源表字段处理脚本
     */
    public final TableField<IndicatorSourceTableRecord, String> SOURCE_TABLE_FIELD_SCRIPT = createField("source_table_field_script", org.jooq.impl.SQLDataType.CLOB, this, "源表字段处理脚本");

    /**
     * The column <code>SMETA_APP.indicator_source_table.status</code>. 状态 0无效 1有效
     */
    public final TableField<IndicatorSourceTableRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "状态 0无效 1有效");

    /**
     * Create a <code>SMETA_APP.indicator_source_table</code> table reference
     */
    public IndicatorSourceTable() {
        this(DSL.name("indicator_source_table"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.indicator_source_table</code> table reference
     */
    public IndicatorSourceTable(String alias) {
        this(DSL.name(alias), INDICATOR_SOURCE_TABLE);
    }

    /**
     * Create an aliased <code>SMETA_APP.indicator_source_table</code> table reference
     */
    public IndicatorSourceTable(Name alias) {
        this(alias, INDICATOR_SOURCE_TABLE);
    }

    private IndicatorSourceTable(Name alias, Table<IndicatorSourceTableRecord> aliased) {
        this(alias, aliased, null);
    }

    private IndicatorSourceTable(Name alias, Table<IndicatorSourceTableRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "指标源表关系");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return SmetaApp.SMETA_APP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.INDICATOR_SOURCE_TABLE_IDX_INDICATOR_CODE, Indexes.INDICATOR_SOURCE_TABLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<IndicatorSourceTableRecord, ULong> getIdentity() {
        return Keys.IDENTITY_INDICATOR_SOURCE_TABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<IndicatorSourceTableRecord> getPrimaryKey() {
        return Keys.KEY_INDICATOR_SOURCE_TABLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<IndicatorSourceTableRecord>> getKeys() {
        return Arrays.<UniqueKey<IndicatorSourceTableRecord>>asList(Keys.KEY_INDICATOR_SOURCE_TABLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTable as(String alias) {
        return new IndicatorSourceTable(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTable as(Name alias) {
        return new IndicatorSourceTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public IndicatorSourceTable rename(String name) {
        return new IndicatorSourceTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public IndicatorSourceTable rename(Name name) {
        return new IndicatorSourceTable(name, null);
    }
}