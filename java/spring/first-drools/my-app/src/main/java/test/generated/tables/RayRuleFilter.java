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
import test.generated.tables.records.RayRuleFilterRecord;


/**
 * 过滤规则
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RayRuleFilter extends TableImpl<RayRuleFilterRecord> {

    private static final long serialVersionUID = 216551651;

    /**
     * The reference instance of <code>SMETA_APP.ray_rule_filter</code>
     */
    public static final RayRuleFilter RAY_RULE_FILTER = new RayRuleFilter();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RayRuleFilterRecord> getRecordType() {
        return RayRuleFilterRecord.class;
    }

    /**
     * The column <code>SMETA_APP.ray_rule_filter.id</code>. 主键
     */
    public final TableField<RayRuleFilterRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.ray_rule_filter.gmt_create</code>. 创建时间
     */
    public final TableField<RayRuleFilterRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.ray_rule_filter.gmt_modified</code>. 修改时间
     */
    public final TableField<RayRuleFilterRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.ray_rule_filter.preheat_id</code>. 预热id
     */
    public final TableField<RayRuleFilterRecord, ULong> PREHEAT_ID = createField("preheat_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "预热id");

    /**
     * The column <code>SMETA_APP.ray_rule_filter.preview_id</code>. 预览id
     */
    public final TableField<RayRuleFilterRecord, ULong> PREVIEW_ID = createField("preview_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "预览id");

    /**
     * The column <code>SMETA_APP.ray_rule_filter.rule_version</code>. 规则版本
     */
    public final TableField<RayRuleFilterRecord, Integer> RULE_VERSION = createField("rule_version", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "规则版本");

    /**
     * The column <code>SMETA_APP.ray_rule_filter.rule_info</code>. 规则信息
     */
    public final TableField<RayRuleFilterRecord, String> RULE_INFO = createField("rule_info", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "规则信息");

    /**
     * The column <code>SMETA_APP.ray_rule_filter.biz_id</code>. 创建人id
     */
    public final TableField<RayRuleFilterRecord, String> BIZ_ID = createField("biz_id", org.jooq.impl.SQLDataType.VARCHAR(50), this, "创建人id");

    /**
     * The column <code>SMETA_APP.ray_rule_filter.tenant_code</code>. 租户code
     */
    public final TableField<RayRuleFilterRecord, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(50), this, "租户code");

    /**
     * The column <code>SMETA_APP.ray_rule_filter.rule_info_md5</code>. 规则信息md5值
     */
    public final TableField<RayRuleFilterRecord, String> RULE_INFO_MD5 = createField("rule_info_md5", org.jooq.impl.SQLDataType.VARCHAR(128), this, "规则信息md5值");

    /**
     * Create a <code>SMETA_APP.ray_rule_filter</code> table reference
     */
    public RayRuleFilter() {
        this(DSL.name("ray_rule_filter"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.ray_rule_filter</code> table reference
     */
    public RayRuleFilter(String alias) {
        this(DSL.name(alias), RAY_RULE_FILTER);
    }

    /**
     * Create an aliased <code>SMETA_APP.ray_rule_filter</code> table reference
     */
    public RayRuleFilter(Name alias) {
        this(alias, RAY_RULE_FILTER);
    }

    private RayRuleFilter(Name alias, Table<RayRuleFilterRecord> aliased) {
        this(alias, aliased, null);
    }

    private RayRuleFilter(Name alias, Table<RayRuleFilterRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "过滤规则");
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
        return Arrays.<Index>asList(Indexes.RAY_RULE_FILTER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<RayRuleFilterRecord, ULong> getIdentity() {
        return Keys.IDENTITY_RAY_RULE_FILTER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RayRuleFilterRecord> getPrimaryKey() {
        return Keys.KEY_RAY_RULE_FILTER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RayRuleFilterRecord>> getKeys() {
        return Arrays.<UniqueKey<RayRuleFilterRecord>>asList(Keys.KEY_RAY_RULE_FILTER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayRuleFilter as(String alias) {
        return new RayRuleFilter(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayRuleFilter as(Name alias) {
        return new RayRuleFilter(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RayRuleFilter rename(String name) {
        return new RayRuleFilter(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RayRuleFilter rename(Name name) {
        return new RayRuleFilter(name, null);
    }
}