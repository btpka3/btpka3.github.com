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
import test.generated.tables.records.UfRuleCostStatShtRecord;


/**
 * 规则成本统计小时表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfRuleCostStatSht extends TableImpl<UfRuleCostStatShtRecord> {

    private static final long serialVersionUID = -1229772837;

    /**
     * The reference instance of <code>SMETA_APP.uf_rule_cost_stat_sht</code>
     */
    public static final UfRuleCostStatSht UF_RULE_COST_STAT_SHT = new UfRuleCostStatSht();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UfRuleCostStatShtRecord> getRecordType() {
        return UfRuleCostStatShtRecord.class;
    }

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.id</code>. 主键
     */
    public final TableField<UfRuleCostStatShtRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.gmt_create</code>. 创建时间
     */
    public final TableField<UfRuleCostStatShtRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.gmt_modified</code>. 修改时间
     */
    public final TableField<UfRuleCostStatShtRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.start_time</code>. 数据统计开始时间
     */
    public final TableField<UfRuleCostStatShtRecord, Timestamp> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "数据统计开始时间");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.end_time</code>. 数据统计结束时间
     */
    public final TableField<UfRuleCostStatShtRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "数据统计结束时间");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.event_code</code>. 事件Code
     */
    public final TableField<UfRuleCostStatShtRecord, String> EVENT_CODE = createField("event_code", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "事件Code");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.rule_id</code>. 规则ID
     */
    public final TableField<UfRuleCostStatShtRecord, String> RULE_ID = createField("rule_id", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "规则ID");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.rule_version</code>. 规则版本
     */
    public final TableField<UfRuleCostStatShtRecord, String> RULE_VERSION = createField("rule_version", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "规则版本");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.rule_total_cost</code>. 规则总成本
     */
    public final TableField<UfRuleCostStatShtRecord, Long> RULE_TOTAL_COST = createField("rule_total_cost", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "规则总成本");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.rule_avr_cost</code>. 规则每次成本
     */
    public final TableField<UfRuleCostStatShtRecord, Long> RULE_AVR_COST = createField("rule_avr_cost", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "规则每次成本");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.rule_total</code>. 规则总执行次数
     */
    public final TableField<UfRuleCostStatShtRecord, Long> RULE_TOTAL = createField("rule_total", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "规则总执行次数");

    /**
     * The column <code>SMETA_APP.uf_rule_cost_stat_sht.rule_hit_cnt</code>. 规则总命中次数
     */
    public final TableField<UfRuleCostStatShtRecord, Long> RULE_HIT_CNT = createField("rule_hit_cnt", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "规则总命中次数");

    /**
     * Create a <code>SMETA_APP.uf_rule_cost_stat_sht</code> table reference
     */
    public UfRuleCostStatSht() {
        this(DSL.name("uf_rule_cost_stat_sht"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_rule_cost_stat_sht</code> table reference
     */
    public UfRuleCostStatSht(String alias) {
        this(DSL.name(alias), UF_RULE_COST_STAT_SHT);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_rule_cost_stat_sht</code> table reference
     */
    public UfRuleCostStatSht(Name alias) {
        this(alias, UF_RULE_COST_STAT_SHT);
    }

    private UfRuleCostStatSht(Name alias, Table<UfRuleCostStatShtRecord> aliased) {
        this(alias, aliased, null);
    }

    private UfRuleCostStatSht(Name alias, Table<UfRuleCostStatShtRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "规则成本统计小时表");
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
        return Arrays.<Index>asList(Indexes.UF_RULE_COST_STAT_SHT_IDX_ID_VERSION, Indexes.UF_RULE_COST_STAT_SHT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UfRuleCostStatShtRecord, ULong> getIdentity() {
        return Keys.IDENTITY_UF_RULE_COST_STAT_SHT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UfRuleCostStatShtRecord> getPrimaryKey() {
        return Keys.KEY_UF_RULE_COST_STAT_SHT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UfRuleCostStatShtRecord>> getKeys() {
        return Arrays.<UniqueKey<UfRuleCostStatShtRecord>>asList(Keys.KEY_UF_RULE_COST_STAT_SHT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRuleCostStatSht as(String alias) {
        return new UfRuleCostStatSht(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRuleCostStatSht as(Name alias) {
        return new UfRuleCostStatSht(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UfRuleCostStatSht rename(String name) {
        return new UfRuleCostStatSht(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UfRuleCostStatSht rename(Name name) {
        return new UfRuleCostStatSht(name, null);
    }
}
