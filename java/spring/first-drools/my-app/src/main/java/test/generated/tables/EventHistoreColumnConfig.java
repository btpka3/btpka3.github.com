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
import test.generated.tables.records.EventHistoreColumnConfigRecord;


/**
 * histore存储字段配置
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventHistoreColumnConfig extends TableImpl<EventHistoreColumnConfigRecord> {

    private static final long serialVersionUID = 2088206139;

    /**
     * The reference instance of <code>SMETA_APP.event_histore_column_config</code>
     */
    public static final EventHistoreColumnConfig EVENT_HISTORE_COLUMN_CONFIG = new EventHistoreColumnConfig();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EventHistoreColumnConfigRecord> getRecordType() {
        return EventHistoreColumnConfigRecord.class;
    }

    /**
     * The column <code>SMETA_APP.event_histore_column_config.id</code>. 主键
     */
    public final TableField<EventHistoreColumnConfigRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.gmt_create</code>. 创建时间
     */
    public final TableField<EventHistoreColumnConfigRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.gmt_modified</code>. 修改时间
     */
    public final TableField<EventHistoreColumnConfigRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.event_code</code>. 事件code
     */
    public final TableField<EventHistoreColumnConfigRecord, String> EVENT_CODE = createField("event_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "事件code");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.event_column_code</code>. 列名
     */
    public final TableField<EventHistoreColumnConfigRecord, String> EVENT_COLUMN_CODE = createField("event_column_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "列名");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.type</code>. 字段类型
     */
    public final TableField<EventHistoreColumnConfigRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(64), this, "字段类型");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.global_column</code>. 全局字段，用来标记全局搜索字段
     */
    public final TableField<EventHistoreColumnConfigRecord, String> GLOBAL_COLUMN = createField("global_column", org.jooq.impl.SQLDataType.VARCHAR(64), this, "全局字段，用来标记全局搜索字段");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.enumerate</code>. 是否枚举字段,0-否，1-是
     */
    public final TableField<EventHistoreColumnConfigRecord, Integer> ENUMERATE = createField("enumerate", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否枚举字段,0-否，1-是");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.level</code>. 安全等级
     */
    public final TableField<EventHistoreColumnConfigRecord, Integer> LEVEL = createField("level", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "安全等级");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.lifecycle</code>. 列生命周期
     */
    public final TableField<EventHistoreColumnConfigRecord, Integer> LIFECYCLE = createField("lifecycle", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "列生命周期");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.status</code>. 状态，1-有效，0-废弃
     */
    public final TableField<EventHistoreColumnConfigRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "状态，1-有效，0-废弃");

    /**
     * The column <code>SMETA_APP.event_histore_column_config.historeDbStatus</code>. 状态，1-上云，0-未上云
     */
    public final TableField<EventHistoreColumnConfigRecord, Integer> HISTOREDBSTATUS = createField("historeDbStatus", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "状态，1-上云，0-未上云");

    /**
     * Create a <code>SMETA_APP.event_histore_column_config</code> table reference
     */
    public EventHistoreColumnConfig() {
        this(DSL.name("event_histore_column_config"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.event_histore_column_config</code> table reference
     */
    public EventHistoreColumnConfig(String alias) {
        this(DSL.name(alias), EVENT_HISTORE_COLUMN_CONFIG);
    }

    /**
     * Create an aliased <code>SMETA_APP.event_histore_column_config</code> table reference
     */
    public EventHistoreColumnConfig(Name alias) {
        this(alias, EVENT_HISTORE_COLUMN_CONFIG);
    }

    private EventHistoreColumnConfig(Name alias, Table<EventHistoreColumnConfigRecord> aliased) {
        this(alias, aliased, null);
    }

    private EventHistoreColumnConfig(Name alias, Table<EventHistoreColumnConfigRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "histore存储字段配置");
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
        return Arrays.<Index>asList(Indexes.EVENT_HISTORE_COLUMN_CONFIG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<EventHistoreColumnConfigRecord, ULong> getIdentity() {
        return Keys.IDENTITY_EVENT_HISTORE_COLUMN_CONFIG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<EventHistoreColumnConfigRecord> getPrimaryKey() {
        return Keys.KEY_EVENT_HISTORE_COLUMN_CONFIG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<EventHistoreColumnConfigRecord>> getKeys() {
        return Arrays.<UniqueKey<EventHistoreColumnConfigRecord>>asList(Keys.KEY_EVENT_HISTORE_COLUMN_CONFIG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventHistoreColumnConfig as(String alias) {
        return new EventHistoreColumnConfig(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventHistoreColumnConfig as(Name alias) {
        return new EventHistoreColumnConfig(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public EventHistoreColumnConfig rename(String name) {
        return new EventHistoreColumnConfig(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public EventHistoreColumnConfig rename(Name name) {
        return new EventHistoreColumnConfig(name, null);
    }
}
