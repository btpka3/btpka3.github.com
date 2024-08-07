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
import test.generated.tables.records.MoreJobReleaseRecord;


/**
 * 近线任务版本表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MoreJobRelease extends TableImpl<MoreJobReleaseRecord> {

    private static final long serialVersionUID = -1259039494;

    /**
     * The reference instance of <code>SMETA_APP.more_job_release</code>
     */
    public static final MoreJobRelease MORE_JOB_RELEASE = new MoreJobRelease();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MoreJobReleaseRecord> getRecordType() {
        return MoreJobReleaseRecord.class;
    }

    /**
     * The column <code>SMETA_APP.more_job_release.id</code>. 主键
     */
    public final TableField<MoreJobReleaseRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.more_job_release.gmt_create</code>. 创建时间
     */
    public final TableField<MoreJobReleaseRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.more_job_release.gmt_modified</code>. 修改时间
     */
    public final TableField<MoreJobReleaseRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.more_job_release.job_id</code>. 任务id
     */
    public final TableField<MoreJobReleaseRecord, ULong> JOB_ID = createField("job_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "任务id");

    /**
     * The column <code>SMETA_APP.more_job_release.name</code>. 任务名称
     */
    public final TableField<MoreJobReleaseRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false), this, "任务名称");

    /**
     * The column <code>SMETA_APP.more_job_release.creator_id</code>. 创建者id
     */
    public final TableField<MoreJobReleaseRecord, Long> CREATOR_ID = createField("creator_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "创建者id");

    /**
     * The column <code>SMETA_APP.more_job_release.operator_id</code>. 操作者id
     */
    public final TableField<MoreJobReleaseRecord, Long> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "操作者id");

    /**
     * The column <code>SMETA_APP.more_job_release.creator_name</code>. 创建者名称
     */
    public final TableField<MoreJobReleaseRecord, String> CREATOR_NAME = createField("creator_name", org.jooq.impl.SQLDataType.VARCHAR(512).nullable(false), this, "创建者名称");

    /**
     * The column <code>SMETA_APP.more_job_release.operator_name</code>. 操作者名称
     */
    public final TableField<MoreJobReleaseRecord, String> OPERATOR_NAME = createField("operator_name", org.jooq.impl.SQLDataType.VARCHAR(512).nullable(false), this, "操作者名称");

    /**
     * The column <code>SMETA_APP.more_job_release.risk_type_id</code>. 风险类型id
     */
    public final TableField<MoreJobReleaseRecord, Long> RISK_TYPE_ID = createField("risk_type_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "风险类型id");

    /**
     * The column <code>SMETA_APP.more_job_release.risk_type_path</code>. 风险类型路径
     */
    public final TableField<MoreJobReleaseRecord, String> RISK_TYPE_PATH = createField("risk_type_path", org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "风险类型路径");

    /**
     * The column <code>SMETA_APP.more_job_release.ssql</code>. ssql
     */
    public final TableField<MoreJobReleaseRecord, String> SSQL = createField("ssql", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "ssql");

    /**
     * The column <code>SMETA_APP.more_job_release.bsql</code>. bsql
     */
    public final TableField<MoreJobReleaseRecord, String> BSQL = createField("bsql", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "bsql");

    /**
     * The column <code>SMETA_APP.more_job_release.engine_version</code>. 引擎版本
     */
    public final TableField<MoreJobReleaseRecord, String> ENGINE_VERSION = createField("engine_version", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "引擎版本");

    /**
     * The column <code>SMETA_APP.more_job_release.job_version</code>. 任务版本
     */
    public final TableField<MoreJobReleaseRecord, Integer> JOB_VERSION = createField("job_version", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "任务版本");

    /**
     * The column <code>SMETA_APP.more_job_release.job_sub_version</code>. 任务子版本
     */
    public final TableField<MoreJobReleaseRecord, Integer> JOB_SUB_VERSION = createField("job_sub_version", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "任务子版本");

    /**
     * The column <code>SMETA_APP.more_job_release.tenant_code</code>. 租户code
     */
    public final TableField<MoreJobReleaseRecord, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(512).nullable(false), this, "租户code");

    /**
     * The column <code>SMETA_APP.more_job_release.comment</code>. 备注
     */
    public final TableField<MoreJobReleaseRecord, String> COMMENT = createField("comment", org.jooq.impl.SQLDataType.CLOB, this, "备注");

    /**
     * The column <code>SMETA_APP.more_job_release.config</code>. 任务配置
     */
    public final TableField<MoreJobReleaseRecord, String> CONFIG = createField("config", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "任务配置");

    /**
     * Create a <code>SMETA_APP.more_job_release</code> table reference
     */
    public MoreJobRelease() {
        this(DSL.name("more_job_release"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.more_job_release</code> table reference
     */
    public MoreJobRelease(String alias) {
        this(DSL.name(alias), MORE_JOB_RELEASE);
    }

    /**
     * Create an aliased <code>SMETA_APP.more_job_release</code> table reference
     */
    public MoreJobRelease(Name alias) {
        this(alias, MORE_JOB_RELEASE);
    }

    private MoreJobRelease(Name alias, Table<MoreJobReleaseRecord> aliased) {
        this(alias, aliased, null);
    }

    private MoreJobRelease(Name alias, Table<MoreJobReleaseRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "近线任务版本表");
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
        return Arrays.<Index>asList(Indexes.MORE_JOB_RELEASE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MoreJobReleaseRecord, ULong> getIdentity() {
        return Keys.IDENTITY_MORE_JOB_RELEASE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MoreJobReleaseRecord> getPrimaryKey() {
        return Keys.KEY_MORE_JOB_RELEASE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MoreJobReleaseRecord>> getKeys() {
        return Arrays.<UniqueKey<MoreJobReleaseRecord>>asList(Keys.KEY_MORE_JOB_RELEASE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MoreJobRelease as(String alias) {
        return new MoreJobRelease(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MoreJobRelease as(Name alias) {
        return new MoreJobRelease(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MoreJobRelease rename(String name) {
        return new MoreJobRelease(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MoreJobRelease rename(Name name) {
        return new MoreJobRelease(name, null);
    }
}
