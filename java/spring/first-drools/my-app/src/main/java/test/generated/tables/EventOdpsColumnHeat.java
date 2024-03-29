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
import test.generated.tables.records.EventOdpsColumnHeatRecord;


/**
 * 数据字典热度表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventOdpsColumnHeat extends TableImpl<EventOdpsColumnHeatRecord> {

    private static final long serialVersionUID = -1562505128;

    /**
     * The reference instance of <code>SMETA_APP.event_odps_column_heat</code>
     */
    public static final EventOdpsColumnHeat EVENT_ODPS_COLUMN_HEAT = new EventOdpsColumnHeat();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EventOdpsColumnHeatRecord> getRecordType() {
        return EventOdpsColumnHeatRecord.class;
    }

    /**
     * The column <code>SMETA_APP.event_odps_column_heat.id</code>. 主键
     */
    public final TableField<EventOdpsColumnHeatRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.event_odps_column_heat.gmt_create</code>. 创建时间
     */
    public final TableField<EventOdpsColumnHeatRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.event_odps_column_heat.gmt_modified</code>. 修改时间
     */
    public final TableField<EventOdpsColumnHeatRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.event_odps_column_heat.event_code</code>. 事件code
     */
    public final TableField<EventOdpsColumnHeatRecord, String> EVENT_CODE = createField("event_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "事件code");

    /**
     * The column <code>SMETA_APP.event_odps_column_heat.odps_table</code>. odps表名
     */
    public final TableField<EventOdpsColumnHeatRecord, String> ODPS_TABLE = createField("odps_table", org.jooq.impl.SQLDataType.VARCHAR(128), this, "odps表名");

    /**
     * The column <code>SMETA_APP.event_odps_column_heat.event_column_code</code>. 字段code
     */
    public final TableField<EventOdpsColumnHeatRecord, String> EVENT_COLUMN_CODE = createField("event_column_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "字段code");

    /**
     * The column <code>SMETA_APP.event_odps_column_heat.odps_column_rate</code>. 表字段查询频次，字段查询次数/表查询次数
     */
    public final TableField<EventOdpsColumnHeatRecord, Double> ODPS_COLUMN_RATE = createField("odps_column_rate", org.jooq.impl.SQLDataType.FLOAT, this, "表字段查询频次，字段查询次数/表查询次数");

    /**
     * The column <code>SMETA_APP.event_odps_column_heat.mtee_column_rate</code>. 属性引用频次，属性被该事件规则引用次数/该事件规则数
     */
    public final TableField<EventOdpsColumnHeatRecord, Double> MTEE_COLUMN_RATE = createField("mtee_column_rate", org.jooq.impl.SQLDataType.FLOAT, this, "属性引用频次，属性被该事件规则引用次数/该事件规则数");

    /**
     * The column <code>SMETA_APP.event_odps_column_heat.column_heat_rate</code>. 属性热度值
     */
    public final TableField<EventOdpsColumnHeatRecord, Double> COLUMN_HEAT_RATE = createField("column_heat_rate", org.jooq.impl.SQLDataType.FLOAT, this, "属性热度值");

    /**
     * The column <code>SMETA_APP.event_odps_column_heat.is_risk_action_col</code>. 是否高危风险规则字段，1-是，0-不是
     */
    public final TableField<EventOdpsColumnHeatRecord, String> IS_RISK_ACTION_COL = createField("is_risk_action_col", org.jooq.impl.SQLDataType.VARCHAR(10), this, "是否高危风险规则字段，1-是，0-不是");

    /**
     * Create a <code>SMETA_APP.event_odps_column_heat</code> table reference
     */
    public EventOdpsColumnHeat() {
        this(DSL.name("event_odps_column_heat"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.event_odps_column_heat</code> table reference
     */
    public EventOdpsColumnHeat(String alias) {
        this(DSL.name(alias), EVENT_ODPS_COLUMN_HEAT);
    }

    /**
     * Create an aliased <code>SMETA_APP.event_odps_column_heat</code> table reference
     */
    public EventOdpsColumnHeat(Name alias) {
        this(alias, EVENT_ODPS_COLUMN_HEAT);
    }

    private EventOdpsColumnHeat(Name alias, Table<EventOdpsColumnHeatRecord> aliased) {
        this(alias, aliased, null);
    }

    private EventOdpsColumnHeat(Name alias, Table<EventOdpsColumnHeatRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "数据字典热度表");
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
        return Arrays.<Index>asList(Indexes.EVENT_ODPS_COLUMN_HEAT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<EventOdpsColumnHeatRecord, ULong> getIdentity() {
        return Keys.IDENTITY_EVENT_ODPS_COLUMN_HEAT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<EventOdpsColumnHeatRecord> getPrimaryKey() {
        return Keys.KEY_EVENT_ODPS_COLUMN_HEAT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<EventOdpsColumnHeatRecord>> getKeys() {
        return Arrays.<UniqueKey<EventOdpsColumnHeatRecord>>asList(Keys.KEY_EVENT_ODPS_COLUMN_HEAT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOdpsColumnHeat as(String alias) {
        return new EventOdpsColumnHeat(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOdpsColumnHeat as(Name alias) {
        return new EventOdpsColumnHeat(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public EventOdpsColumnHeat rename(String name) {
        return new EventOdpsColumnHeat(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public EventOdpsColumnHeat rename(Name name) {
        return new EventOdpsColumnHeat(name, null);
    }
}
