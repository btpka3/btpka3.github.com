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
import test.generated.tables.records.MetaInfoRecord;


/**
 * 元数据信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MetaInfo extends TableImpl<MetaInfoRecord> {

    private static final long serialVersionUID = -1735963139;

    /**
     * The reference instance of <code>SMETA_APP.meta_info</code>
     */
    public static final MetaInfo META_INFO = new MetaInfo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MetaInfoRecord> getRecordType() {
        return MetaInfoRecord.class;
    }

    /**
     * The column <code>SMETA_APP.meta_info.id</code>. 主键
     */
    public final TableField<MetaInfoRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.meta_info.gmt_create</code>. 创建时间
     */
    public final TableField<MetaInfoRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.meta_info.gmt_modified</code>. 修改时间
     */
    public final TableField<MetaInfoRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.meta_info.code</code>. 元数据code
     */
    public final TableField<MetaInfoRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "元数据code");

    /**
     * The column <code>SMETA_APP.meta_info.type_code</code>. 元数据类型的code
     */
    public final TableField<MetaInfoRecord, String> TYPE_CODE = createField("type_code", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "元数据类型的code");

    /**
     * The column <code>SMETA_APP.meta_info.type_name</code>. 元数据类型的名称
     */
    public final TableField<MetaInfoRecord, String> TYPE_NAME = createField("type_name", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "元数据类型的名称");

    /**
     * The column <code>SMETA_APP.meta_info.name</code>. 元数据名称
     */
    public final TableField<MetaInfoRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "元数据名称");

    /**
     * The column <code>SMETA_APP.meta_info.uniq_code</code>. 唯一code
     */
    public final TableField<MetaInfoRecord, String> UNIQ_CODE = createField("uniq_code", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false), this, "唯一code");

    /**
     * Create a <code>SMETA_APP.meta_info</code> table reference
     */
    public MetaInfo() {
        this(DSL.name("meta_info"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.meta_info</code> table reference
     */
    public MetaInfo(String alias) {
        this(DSL.name(alias), META_INFO);
    }

    /**
     * Create an aliased <code>SMETA_APP.meta_info</code> table reference
     */
    public MetaInfo(Name alias) {
        this(alias, META_INFO);
    }

    private MetaInfo(Name alias, Table<MetaInfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private MetaInfo(Name alias, Table<MetaInfoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "元数据信息表");
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
        return Arrays.<Index>asList(Indexes.META_INFO_IDX_CODE_TYPE_CODE, Indexes.META_INFO_IDX_TYPE_CODE, Indexes.META_INFO_PRIMARY, Indexes.META_INFO_UK_UNIQ_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MetaInfoRecord, ULong> getIdentity() {
        return Keys.IDENTITY_META_INFO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MetaInfoRecord> getPrimaryKey() {
        return Keys.KEY_META_INFO_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MetaInfoRecord>> getKeys() {
        return Arrays.<UniqueKey<MetaInfoRecord>>asList(Keys.KEY_META_INFO_PRIMARY, Keys.KEY_META_INFO_UK_UNIQ_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MetaInfo as(String alias) {
        return new MetaInfo(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MetaInfo as(Name alias) {
        return new MetaInfo(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MetaInfo rename(String name) {
        return new MetaInfo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MetaInfo rename(Name name) {
        return new MetaInfo(name, null);
    }
}