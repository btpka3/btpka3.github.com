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
import test.generated.tables.records.UfDailyRiskActionRecord;


/**
 * 日常风险处理表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfDailyRiskAction extends TableImpl<UfDailyRiskActionRecord> {

    private static final long serialVersionUID = 1515154699;

    /**
     * The reference instance of <code>SMETA_APP.uf_daily_risk_action</code>
     */
    public static final UfDailyRiskAction UF_DAILY_RISK_ACTION = new UfDailyRiskAction();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UfDailyRiskActionRecord> getRecordType() {
        return UfDailyRiskActionRecord.class;
    }

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.id</code>. 主键
     */
    public final TableField<UfDailyRiskActionRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.gmt_create</code>. 创建时间
     */
    public final TableField<UfDailyRiskActionRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.gmt_modified</code>. 修改时间
     */
    public final TableField<UfDailyRiskActionRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.creator</code>. 创建人UserId
     */
    public final TableField<UfDailyRiskActionRecord, ULong> CREATOR = createField("creator", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "创建人UserId");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.last_operator</code>. 最后修改人UserId
     */
    public final TableField<UfDailyRiskActionRecord, ULong> LAST_OPERATOR = createField("last_operator", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "最后修改人UserId");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.tenant_code</code>. 租户code
     */
    public final TableField<UfDailyRiskActionRecord, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "租户code");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.status</code>. 0-未处理，1-处理中，2-已处理
     */
    public final TableField<UfDailyRiskActionRecord, Short> STATUS = createField("status", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "0-未处理，1-处理中，2-已处理");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.operator_ids</code>. 处理人ID列表，逗号间隔
     */
    public final TableField<UfDailyRiskActionRecord, String> OPERATOR_IDS = createField("operator_ids", org.jooq.impl.SQLDataType.VARCHAR(512).nullable(false), this, "处理人ID列表，逗号间隔");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.expected_action</code>. 期望的处理动作
     */
    public final TableField<UfDailyRiskActionRecord, String> EXPECTED_ACTION = createField("expected_action", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "期望的处理动作");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.actual_action</code>. 实际的处理动作，或拒绝处理的原因
     */
    public final TableField<UfDailyRiskActionRecord, String> ACTUAL_ACTION = createField("actual_action", org.jooq.impl.SQLDataType.CLOB, this, "实际的处理动作，或拒绝处理的原因");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.actual_operator</code>. 实际处理人员的ID
     */
    public final TableField<UfDailyRiskActionRecord, ULong> ACTUAL_OPERATOR = createField("actual_operator", org.jooq.impl.SQLDataType.BIGINTUNSIGNED, this, "实际处理人员的ID");

    /**
     * The column <code>SMETA_APP.uf_daily_risk_action.actual_operate_time</code>. 实际处理的时间
     */
    public final TableField<UfDailyRiskActionRecord, Timestamp> ACTUAL_OPERATE_TIME = createField("actual_operate_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "实际处理的时间");

    /**
     * Create a <code>SMETA_APP.uf_daily_risk_action</code> table reference
     */
    public UfDailyRiskAction() {
        this(DSL.name("uf_daily_risk_action"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_daily_risk_action</code> table reference
     */
    public UfDailyRiskAction(String alias) {
        this(DSL.name(alias), UF_DAILY_RISK_ACTION);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_daily_risk_action</code> table reference
     */
    public UfDailyRiskAction(Name alias) {
        this(alias, UF_DAILY_RISK_ACTION);
    }

    private UfDailyRiskAction(Name alias, Table<UfDailyRiskActionRecord> aliased) {
        this(alias, aliased, null);
    }

    private UfDailyRiskAction(Name alias, Table<UfDailyRiskActionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "日常风险处理表");
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
        return Arrays.<Index>asList(Indexes.UF_DAILY_RISK_ACTION_IDX_OPERATOR_IDS, Indexes.UF_DAILY_RISK_ACTION_IDX_TENANT_CODE, Indexes.UF_DAILY_RISK_ACTION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UfDailyRiskActionRecord, ULong> getIdentity() {
        return Keys.IDENTITY_UF_DAILY_RISK_ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UfDailyRiskActionRecord> getPrimaryKey() {
        return Keys.KEY_UF_DAILY_RISK_ACTION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UfDailyRiskActionRecord>> getKeys() {
        return Arrays.<UniqueKey<UfDailyRiskActionRecord>>asList(Keys.KEY_UF_DAILY_RISK_ACTION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfDailyRiskAction as(String alias) {
        return new UfDailyRiskAction(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfDailyRiskAction as(Name alias) {
        return new UfDailyRiskAction(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UfDailyRiskAction rename(String name) {
        return new UfDailyRiskAction(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UfDailyRiskAction rename(Name name) {
        return new UfDailyRiskAction(name, null);
    }
}
