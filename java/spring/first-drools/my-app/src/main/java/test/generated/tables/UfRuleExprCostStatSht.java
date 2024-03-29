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
import test.generated.tables.records.UfRuleExprCostStatShtRecord;


/**
 * 规则表达式统计数据
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfRuleExprCostStatSht extends TableImpl<UfRuleExprCostStatShtRecord> {

    private static final long serialVersionUID = 1144791533;

    /**
     * The reference instance of <code>SMETA_APP.uf_rule_expr_cost_stat_sht</code>
     */
    public static final UfRuleExprCostStatSht UF_RULE_EXPR_COST_STAT_SHT = new UfRuleExprCostStatSht();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UfRuleExprCostStatShtRecord> getRecordType() {
        return UfRuleExprCostStatShtRecord.class;
    }

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.id</code>. 主键
     */
    public final TableField<UfRuleExprCostStatShtRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.gmt_create</code>. 创建时间
     */
    public final TableField<UfRuleExprCostStatShtRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.gmt_modified</code>. 修改时间
     */
    public final TableField<UfRuleExprCostStatShtRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.start_time</code>. 数据统计开始时间
     */
    public final TableField<UfRuleExprCostStatShtRecord, Timestamp> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "数据统计开始时间");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.end_time</code>. 数据统计结束时间
     */
    public final TableField<UfRuleExprCostStatShtRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "数据统计结束时间");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.event_code</code>. 事件Code
     */
    public final TableField<UfRuleExprCostStatShtRecord, String> EVENT_CODE = createField("event_code", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "事件Code");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.rule_id</code>. 规则ID
     */
    public final TableField<UfRuleExprCostStatShtRecord, String> RULE_ID = createField("rule_id", org.jooq.impl.SQLDataType.VARCHAR(15).nullable(false), this, "规则ID");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.rule_version</code>. 规则版本
     */
    public final TableField<UfRuleExprCostStatShtRecord, String> RULE_VERSION = createField("rule_version", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false), this, "规则版本");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.expr_id</code>. 表达式ID
     */
    public final TableField<UfRuleExprCostStatShtRecord, Integer> EXPR_ID = createField("expr_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "表达式ID");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.left_dict_code</code>. 左变量数据字典
     */
    public final TableField<UfRuleExprCostStatShtRecord, String> LEFT_DICT_CODE = createField("left_dict_code", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "左变量数据字典");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.left_dict_avr_cost</code>. 左变量平均成本
     */
    public final TableField<UfRuleExprCostStatShtRecord, Long> LEFT_DICT_AVR_COST = createField("left_dict_avr_cost", org.jooq.impl.SQLDataType.BIGINT, this, "左变量平均成本");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.operator_code</code>. 操作符
     */
    public final TableField<UfRuleExprCostStatShtRecord, String> OPERATOR_CODE = createField("operator_code", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "操作符");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.operator_avr_cost</code>. 操作符平均成本
     */
    public final TableField<UfRuleExprCostStatShtRecord, Long> OPERATOR_AVR_COST = createField("operator_avr_cost", org.jooq.impl.SQLDataType.BIGINT, this, "操作符平均成本");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.right_dict_code</code>. 右变量数据字典
     */
    public final TableField<UfRuleExprCostStatShtRecord, String> RIGHT_DICT_CODE = createField("right_dict_code", org.jooq.impl.SQLDataType.VARCHAR(100), this, "右变量数据字典");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.right_dict_avr_cost</code>. 右变量平均成本
     */
    public final TableField<UfRuleExprCostStatShtRecord, Long> RIGHT_DICT_AVR_COST = createField("right_dict_avr_cost", org.jooq.impl.SQLDataType.BIGINT, this, "右变量平均成本");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.rule_total</code>. 规则总执行次数
     */
    public final TableField<UfRuleExprCostStatShtRecord, Long> RULE_TOTAL = createField("rule_total", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "规则总执行次数");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.rule_hit_cnt</code>. 规则总命中次数
     */
    public final TableField<UfRuleExprCostStatShtRecord, Long> RULE_HIT_CNT = createField("rule_hit_cnt", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "规则总命中次数");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.expr_total</code>. 表达式总执行次数
     */
    public final TableField<UfRuleExprCostStatShtRecord, Long> EXPR_TOTAL = createField("expr_total", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "表达式总执行次数");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.expr_hit_cnt</code>. 表达式命中次数
     */
    public final TableField<UfRuleExprCostStatShtRecord, Long> EXPR_HIT_CNT = createField("expr_hit_cnt", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "表达式命中次数");

    /**
     * The column <code>SMETA_APP.uf_rule_expr_cost_stat_sht.eval_type</code>. 执行类型
     */
    public final TableField<UfRuleExprCostStatShtRecord, String> EVAL_TYPE = createField("eval_type", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false), this, "执行类型");

    /**
     * Create a <code>SMETA_APP.uf_rule_expr_cost_stat_sht</code> table reference
     */
    public UfRuleExprCostStatSht() {
        this(DSL.name("uf_rule_expr_cost_stat_sht"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_rule_expr_cost_stat_sht</code> table reference
     */
    public UfRuleExprCostStatSht(String alias) {
        this(DSL.name(alias), UF_RULE_EXPR_COST_STAT_SHT);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_rule_expr_cost_stat_sht</code> table reference
     */
    public UfRuleExprCostStatSht(Name alias) {
        this(alias, UF_RULE_EXPR_COST_STAT_SHT);
    }

    private UfRuleExprCostStatSht(Name alias, Table<UfRuleExprCostStatShtRecord> aliased) {
        this(alias, aliased, null);
    }

    private UfRuleExprCostStatSht(Name alias, Table<UfRuleExprCostStatShtRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "规则表达式统计数据");
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
        return Arrays.<Index>asList(Indexes.UF_RULE_EXPR_COST_STAT_SHT_IDX_ID_VERSION_TIME, Indexes.UF_RULE_EXPR_COST_STAT_SHT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UfRuleExprCostStatShtRecord, ULong> getIdentity() {
        return Keys.IDENTITY_UF_RULE_EXPR_COST_STAT_SHT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UfRuleExprCostStatShtRecord> getPrimaryKey() {
        return Keys.KEY_UF_RULE_EXPR_COST_STAT_SHT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UfRuleExprCostStatShtRecord>> getKeys() {
        return Arrays.<UniqueKey<UfRuleExprCostStatShtRecord>>asList(Keys.KEY_UF_RULE_EXPR_COST_STAT_SHT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRuleExprCostStatSht as(String alias) {
        return new UfRuleExprCostStatSht(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRuleExprCostStatSht as(Name alias) {
        return new UfRuleExprCostStatSht(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UfRuleExprCostStatSht rename(String name) {
        return new UfRuleExprCostStatSht(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UfRuleExprCostStatSht rename(Name name) {
        return new UfRuleExprCostStatSht(name, null);
    }
}
