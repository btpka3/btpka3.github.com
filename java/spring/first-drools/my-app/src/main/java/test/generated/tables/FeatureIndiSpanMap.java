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
import test.generated.tables.records.FeatureIndiSpanMapRecord;


/**
 * 风险特征库-特征推荐-特征与SPAN映射表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FeatureIndiSpanMap extends TableImpl<FeatureIndiSpanMapRecord> {

    private static final long serialVersionUID = -79878940;

    /**
     * The reference instance of <code>SMETA_APP.feature_indi_span_map</code>
     */
    public static final FeatureIndiSpanMap FEATURE_INDI_SPAN_MAP = new FeatureIndiSpanMap();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FeatureIndiSpanMapRecord> getRecordType() {
        return FeatureIndiSpanMapRecord.class;
    }

    /**
     * The column <code>SMETA_APP.feature_indi_span_map.id</code>. 主键
     */
    public final TableField<FeatureIndiSpanMapRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.feature_indi_span_map.gmt_create</code>. 创建时间
     */
    public final TableField<FeatureIndiSpanMapRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.feature_indi_span_map.gmt_modified</code>. 修改时间
     */
    public final TableField<FeatureIndiSpanMapRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.feature_indi_span_map.indi_code</code>. 指标CODE
     */
    public final TableField<FeatureIndiSpanMapRecord, String> INDI_CODE = createField("indi_code", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "指标CODE");

    /**
     * The column <code>SMETA_APP.feature_indi_span_map.span_table_name</code>. 对应链路表
     */
    public final TableField<FeatureIndiSpanMapRecord, String> SPAN_TABLE_NAME = createField("span_table_name", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "对应链路表");

    /**
     * The column <code>SMETA_APP.feature_indi_span_map.span_tag</code>. 对应链标签
     */
    public final TableField<FeatureIndiSpanMapRecord, String> SPAN_TAG = createField("span_tag", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "对应链标签");

    /**
     * The column <code>SMETA_APP.feature_indi_span_map.riskfeature_flg</code>. 风险特征标签1是 0否
     */
    public final TableField<FeatureIndiSpanMapRecord, String> RISKFEATURE_FLG = createField("riskfeature_flg", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "风险特征标签1是 0否");

    /**
     * Create a <code>SMETA_APP.feature_indi_span_map</code> table reference
     */
    public FeatureIndiSpanMap() {
        this(DSL.name("feature_indi_span_map"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.feature_indi_span_map</code> table reference
     */
    public FeatureIndiSpanMap(String alias) {
        this(DSL.name(alias), FEATURE_INDI_SPAN_MAP);
    }

    /**
     * Create an aliased <code>SMETA_APP.feature_indi_span_map</code> table reference
     */
    public FeatureIndiSpanMap(Name alias) {
        this(alias, FEATURE_INDI_SPAN_MAP);
    }

    private FeatureIndiSpanMap(Name alias, Table<FeatureIndiSpanMapRecord> aliased) {
        this(alias, aliased, null);
    }

    private FeatureIndiSpanMap(Name alias, Table<FeatureIndiSpanMapRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "风险特征库-特征推荐-特征与SPAN映射表");
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
        return Arrays.<Index>asList(Indexes.FEATURE_INDI_SPAN_MAP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<FeatureIndiSpanMapRecord, ULong> getIdentity() {
        return Keys.IDENTITY_FEATURE_INDI_SPAN_MAP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<FeatureIndiSpanMapRecord> getPrimaryKey() {
        return Keys.KEY_FEATURE_INDI_SPAN_MAP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<FeatureIndiSpanMapRecord>> getKeys() {
        return Arrays.<UniqueKey<FeatureIndiSpanMapRecord>>asList(Keys.KEY_FEATURE_INDI_SPAN_MAP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FeatureIndiSpanMap as(String alias) {
        return new FeatureIndiSpanMap(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FeatureIndiSpanMap as(Name alias) {
        return new FeatureIndiSpanMap(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FeatureIndiSpanMap rename(String name) {
        return new FeatureIndiSpanMap(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FeatureIndiSpanMap rename(Name name) {
        return new FeatureIndiSpanMap(name, null);
    }
}
