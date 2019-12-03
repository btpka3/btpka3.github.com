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
import test.generated.tables.records.DictColumnRecord;


/**
 * 数据字典属性表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictColumn extends TableImpl<DictColumnRecord> {

    private static final long serialVersionUID = -290954390;

    /**
     * The reference instance of <code>SMETA_APP.dict_column</code>
     */
    public static final DictColumn DICT_COLUMN = new DictColumn();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictColumnRecord> getRecordType() {
        return DictColumnRecord.class;
    }

    /**
     * The column <code>SMETA_APP.dict_column.id</code>. 主键
     */
    public final TableField<DictColumnRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.dict_column.gmt_create</code>. 创建时间
     */
    public final TableField<DictColumnRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.dict_column.gmt_modified</code>. 修改时间
     */
    public final TableField<DictColumnRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.dict_column.cn_name</code>. 属性中文名称
     */
    public final TableField<DictColumnRecord, String> CN_NAME = createField("cn_name", org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "属性中文名称");

    /**
     * The column <code>SMETA_APP.dict_column.en_name</code>. 属性英文名称
     */
    public final TableField<DictColumnRecord, String> EN_NAME = createField("en_name", org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "属性英文名称");

    /**
     * The column <code>SMETA_APP.dict_column.code</code>. 属性code
     */
    public final TableField<DictColumnRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "属性code");

    /**
     * The column <code>SMETA_APP.dict_column.type</code>. 属性种类
     */
    public final TableField<DictColumnRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "属性种类");

    /**
     * The column <code>SMETA_APP.dict_column.operator_id</code>. 操作者工号
     */
    public final TableField<DictColumnRecord, String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作者工号");

    /**
     * The column <code>SMETA_APP.dict_column.operator</code>. 操作者
     */
    public final TableField<DictColumnRecord, String> OPERATOR = createField("operator", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作者");

    /**
     * The column <code>SMETA_APP.dict_column.status</code>. 属性审核状态 0  删除  1 有效 2 草稿 3 审核中
     */
    public final TableField<DictColumnRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "属性审核状态 0  删除  1 有效 2 草稿 3 审核中");

    /**
     * The column <code>SMETA_APP.dict_column.tenant_code</code>. 租户code
     */
    public final TableField<DictColumnRecord, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(64).defaultValue(org.jooq.impl.DSL.inline("ali_taobao", org.jooq.impl.SQLDataType.VARCHAR)), this, "租户code");

    /**
     * Create a <code>SMETA_APP.dict_column</code> table reference
     */
    public DictColumn() {
        this(DSL.name("dict_column"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.dict_column</code> table reference
     */
    public DictColumn(String alias) {
        this(DSL.name(alias), DICT_COLUMN);
    }

    /**
     * Create an aliased <code>SMETA_APP.dict_column</code> table reference
     */
    public DictColumn(Name alias) {
        this(alias, DICT_COLUMN);
    }

    private DictColumn(Name alias, Table<DictColumnRecord> aliased) {
        this(alias, aliased, null);
    }

    private DictColumn(Name alias, Table<DictColumnRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "数据字典属性表");
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
        return Arrays.<Index>asList(Indexes.DICT_COLUMN_IDX_CODE, Indexes.DICT_COLUMN_PRIMARY, Indexes.DICT_COLUMN_UK_CNNAME_TENANT, Indexes.DICT_COLUMN_UK_CODE_TENANT, Indexes.DICT_COLUMN_UK_ENNAME_TENANT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DictColumnRecord, ULong> getIdentity() {
        return Keys.IDENTITY_DICT_COLUMN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DictColumnRecord> getPrimaryKey() {
        return Keys.KEY_DICT_COLUMN_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictColumnRecord>> getKeys() {
        return Arrays.<UniqueKey<DictColumnRecord>>asList(Keys.KEY_DICT_COLUMN_PRIMARY, Keys.KEY_DICT_COLUMN_UK_CNNAME_TENANT, Keys.KEY_DICT_COLUMN_UK_ENNAME_TENANT, Keys.KEY_DICT_COLUMN_UK_CODE_TENANT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumn as(String alias) {
        return new DictColumn(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumn as(Name alias) {
        return new DictColumn(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictColumn rename(String name) {
        return new DictColumn(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DictColumn rename(Name name) {
        return new DictColumn(name, null);
    }
}