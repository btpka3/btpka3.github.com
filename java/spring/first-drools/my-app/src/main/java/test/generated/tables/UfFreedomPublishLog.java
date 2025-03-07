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
import test.generated.tables.records.UfFreedomPublishLogRecord;


/**
 * freedom重启记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfFreedomPublishLog extends TableImpl<UfFreedomPublishLogRecord> {

    private static final long serialVersionUID = 996548080;

    /**
     * The reference instance of <code>SMETA_APP.uf_freedom_publish_log</code>
     */
    public static final UfFreedomPublishLog UF_FREEDOM_PUBLISH_LOG = new UfFreedomPublishLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UfFreedomPublishLogRecord> getRecordType() {
        return UfFreedomPublishLogRecord.class;
    }

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.id</code>. 主键
     */
    public final TableField<UfFreedomPublishLogRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.gmt_create</code>. 创建时间
     */
    public final TableField<UfFreedomPublishLogRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.gmt_modified</code>. 修改时间
     */
    public final TableField<UfFreedomPublishLogRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.operator</code>. 操作人
     */
    public final TableField<UfFreedomPublishLogRecord, String> OPERATOR = createField("operator", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "操作人");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.tenant_code</code>. 租户id
     */
    public final TableField<UfFreedomPublishLogRecord, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "租户id");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.beta_publish_status</code>. beta发布状态
     */
    public final TableField<UfFreedomPublishLogRecord, Integer> BETA_PUBLISH_STATUS = createField("beta_publish_status", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "beta发布状态");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.release_publish_status</code>. 正式发布状态
     */
    public final TableField<UfFreedomPublishLogRecord, Integer> RELEASE_PUBLISH_STATUS = createField("release_publish_status", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "正式发布状态");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.beta_context</code>. beta发布时的环境信息
     */
    public final TableField<UfFreedomPublishLogRecord, String> BETA_CONTEXT = createField("beta_context", org.jooq.impl.SQLDataType.CLOB, this, "beta发布时的环境信息");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.release_context</code>. release发布时的环境信息
     */
    public final TableField<UfFreedomPublishLogRecord, String> RELEASE_CONTEXT = createField("release_context", org.jooq.impl.SQLDataType.CLOB, this, "release发布时的环境信息");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.step</code>. 发布记录当前步骤
     */
    public final TableField<UfFreedomPublishLogRecord, Byte> STEP = createField("step", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "发布记录当前步骤");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.status</code>. 发布记录的状态，一般用于逻辑删除
     */
    public final TableField<UfFreedomPublishLogRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "发布记录的状态，一般用于逻辑删除");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.bundle_property_list</code>. 配置项列表
     */
    public final TableField<UfFreedomPublishLogRecord, String> BUNDLE_PROPERTY_LIST = createField("bundle_property_list", org.jooq.impl.SQLDataType.VARCHAR(2000), this, "配置项列表");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.bundle_list</code>. bundle列表
     */
    public final TableField<UfFreedomPublishLogRecord, String> BUNDLE_LIST = createField("bundle_list", org.jooq.impl.SQLDataType.VARCHAR(2000).nullable(false), this, "bundle列表");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.app_name</code>. 要发布的应用
     */
    public final TableField<UfFreedomPublishLogRecord, String> APP_NAME = createField("app_name", org.jooq.impl.SQLDataType.VARCHAR(20), this, "要发布的应用");

    /**
     * The column <code>SMETA_APP.uf_freedom_publish_log.area</code>. 要发布的区域
     */
    public final TableField<UfFreedomPublishLogRecord, String> AREA = createField("area", org.jooq.impl.SQLDataType.VARCHAR(10), this, "要发布的区域");

    /**
     * Create a <code>SMETA_APP.uf_freedom_publish_log</code> table reference
     */
    public UfFreedomPublishLog() {
        this(DSL.name("uf_freedom_publish_log"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_freedom_publish_log</code> table reference
     */
    public UfFreedomPublishLog(String alias) {
        this(DSL.name(alias), UF_FREEDOM_PUBLISH_LOG);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_freedom_publish_log</code> table reference
     */
    public UfFreedomPublishLog(Name alias) {
        this(alias, UF_FREEDOM_PUBLISH_LOG);
    }

    private UfFreedomPublishLog(Name alias, Table<UfFreedomPublishLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private UfFreedomPublishLog(Name alias, Table<UfFreedomPublishLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "freedom重启记录");
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
        return Arrays.<Index>asList(Indexes.UF_FREEDOM_PUBLISH_LOG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UfFreedomPublishLogRecord, ULong> getIdentity() {
        return Keys.IDENTITY_UF_FREEDOM_PUBLISH_LOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UfFreedomPublishLogRecord> getPrimaryKey() {
        return Keys.KEY_UF_FREEDOM_PUBLISH_LOG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UfFreedomPublishLogRecord>> getKeys() {
        return Arrays.<UniqueKey<UfFreedomPublishLogRecord>>asList(Keys.KEY_UF_FREEDOM_PUBLISH_LOG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfFreedomPublishLog as(String alias) {
        return new UfFreedomPublishLog(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfFreedomPublishLog as(Name alias) {
        return new UfFreedomPublishLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UfFreedomPublishLog rename(String name) {
        return new UfFreedomPublishLog(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UfFreedomPublishLog rename(Name name) {
        return new UfFreedomPublishLog(name, null);
    }
}
