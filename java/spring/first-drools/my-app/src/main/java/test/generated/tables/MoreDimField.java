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
import test.generated.tables.records.MoreDimFieldRecord;


/**
 * 近线引擎维表字段信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MoreDimField extends TableImpl<MoreDimFieldRecord> {

    private static final long serialVersionUID = 1784539116;

    /**
     * The reference instance of <code>SMETA_APP.more_dim_field</code>
     */
    public static final MoreDimField MORE_DIM_FIELD = new MoreDimField();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MoreDimFieldRecord> getRecordType() {
        return MoreDimFieldRecord.class;
    }

    /**
     * The column <code>SMETA_APP.more_dim_field.id</code>. 主键
     */
    public final TableField<MoreDimFieldRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.more_dim_field.gmt_create</code>. 创建时间
     */
    public final TableField<MoreDimFieldRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.more_dim_field.gmt_modified</code>. 修改时间
     */
    public final TableField<MoreDimFieldRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.more_dim_field.dim_id</code>. 关联维表的id
     */
    public final TableField<MoreDimFieldRecord, Long> DIM_ID = createField("dim_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "关联维表的id");

    /**
     * The column <code>SMETA_APP.more_dim_field.dim_name</code>. 关联维表的名称
     */
    public final TableField<MoreDimFieldRecord, String> DIM_NAME = createField("dim_name", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "关联维表的名称");

    /**
     * The column <code>SMETA_APP.more_dim_field.column_name</code>. 字段名称
     */
    public final TableField<MoreDimFieldRecord, String> COLUMN_NAME = createField("column_name", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "字段名称");

    /**
     * The column <code>SMETA_APP.more_dim_field.column_type</code>. 字段类型
     */
    public final TableField<MoreDimFieldRecord, String> COLUMN_TYPE = createField("column_type", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "字段类型");

    /**
     * The column <code>SMETA_APP.more_dim_field.column_desc</code>. 字段描述
     */
    public final TableField<MoreDimFieldRecord, String> COLUMN_DESC = createField("column_desc", org.jooq.impl.SQLDataType.VARCHAR(256), this, "字段描述");

    /**
     * The column <code>SMETA_APP.more_dim_field.operator_id</code>. 操作人id
     */
    public final TableField<MoreDimFieldRecord, String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人id");

    /**
     * The column <code>SMETA_APP.more_dim_field.operator</code>. 操作人
     */
    public final TableField<MoreDimFieldRecord, String> OPERATOR = createField("operator", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人");

    /**
     * Create a <code>SMETA_APP.more_dim_field</code> table reference
     */
    public MoreDimField() {
        this(DSL.name("more_dim_field"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.more_dim_field</code> table reference
     */
    public MoreDimField(String alias) {
        this(DSL.name(alias), MORE_DIM_FIELD);
    }

    /**
     * Create an aliased <code>SMETA_APP.more_dim_field</code> table reference
     */
    public MoreDimField(Name alias) {
        this(alias, MORE_DIM_FIELD);
    }

    private MoreDimField(Name alias, Table<MoreDimFieldRecord> aliased) {
        this(alias, aliased, null);
    }

    private MoreDimField(Name alias, Table<MoreDimFieldRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "近线引擎维表字段信息");
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
        return Arrays.<Index>asList(Indexes.MORE_DIM_FIELD_IDX_DIM_ID, Indexes.MORE_DIM_FIELD_IDX_DIM_NAME, Indexes.MORE_DIM_FIELD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MoreDimFieldRecord, ULong> getIdentity() {
        return Keys.IDENTITY_MORE_DIM_FIELD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MoreDimFieldRecord> getPrimaryKey() {
        return Keys.KEY_MORE_DIM_FIELD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MoreDimFieldRecord>> getKeys() {
        return Arrays.<UniqueKey<MoreDimFieldRecord>>asList(Keys.KEY_MORE_DIM_FIELD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MoreDimField as(String alias) {
        return new MoreDimField(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MoreDimField as(Name alias) {
        return new MoreDimField(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MoreDimField rename(String name) {
        return new MoreDimField(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MoreDimField rename(Name name) {
        return new MoreDimField(name, null);
    }
}
