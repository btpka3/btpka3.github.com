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
import test.generated.tables.records.ProjectColumnsBackupRecord;


/**
 * 应用字段关系表_备份表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProjectColumnsBackup extends TableImpl<ProjectColumnsBackupRecord> {

    private static final long serialVersionUID = 460933442;

    /**
     * The reference instance of <code>SMETA_APP.project_columns_backup</code>
     */
    public static final ProjectColumnsBackup PROJECT_COLUMNS_BACKUP = new ProjectColumnsBackup();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectColumnsBackupRecord> getRecordType() {
        return ProjectColumnsBackupRecord.class;
    }

    /**
     * The column <code>SMETA_APP.project_columns_backup.id</code>. 主键
     */
    public final TableField<ProjectColumnsBackupRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.project_columns_backup.gmt_create</code>. 创建时间
     */
    public final TableField<ProjectColumnsBackupRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.project_columns_backup.gmt_modified</code>. 修改时间
     */
    public final TableField<ProjectColumnsBackupRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.project_columns_backup.project_code</code>. 应用code
     */
    public final TableField<ProjectColumnsBackupRecord, String> PROJECT_CODE = createField("project_code", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "应用code");

    /**
     * The column <code>SMETA_APP.project_columns_backup.event_column_code</code>. 事件下的唯一属性编码
     */
    public final TableField<ProjectColumnsBackupRecord, String> EVENT_COLUMN_CODE = createField("event_column_code", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "事件下的唯一属性编码");

    /**
     * The column <code>SMETA_APP.project_columns_backup.event_code</code>. 事件编码
     */
    public final TableField<ProjectColumnsBackupRecord, String> EVENT_CODE = createField("event_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "事件编码");

    /**
     * The column <code>SMETA_APP.project_columns_backup.entity_code</code>. 实体编码
     */
    public final TableField<ProjectColumnsBackupRecord, String> ENTITY_CODE = createField("entity_code", org.jooq.impl.SQLDataType.VARCHAR(64), this, "实体编码");

    /**
     * The column <code>SMETA_APP.project_columns_backup.column_code</code>. 属性编码
     */
    public final TableField<ProjectColumnsBackupRecord, String> COLUMN_CODE = createField("column_code", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "属性编码");

    /**
     * The column <code>SMETA_APP.project_columns_backup.operator_id</code>. 操作人工号
     */
    public final TableField<ProjectColumnsBackupRecord, String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人工号");

    /**
     * The column <code>SMETA_APP.project_columns_backup.operator</code>. 操作人
     */
    public final TableField<ProjectColumnsBackupRecord, String> OPERATOR = createField("operator", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人");

    /**
     * The column <code>SMETA_APP.project_columns_backup.re_column_code</code>. 重命名code
     */
    public final TableField<ProjectColumnsBackupRecord, String> RE_COLUMN_CODE = createField("re_column_code", org.jooq.impl.SQLDataType.VARCHAR(64), this, "重命名code");

    /**
     * The column <code>SMETA_APP.project_columns_backup.status</code>. 无效：0，有效：1，2：草稿，3：审核中
     */
    public final TableField<ProjectColumnsBackupRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "无效：0，有效：1，2：草稿，3：审核中");

    /**
     * The column <code>SMETA_APP.project_columns_backup.distributed</code>. 默认为1，下发属性，无需下发为0
     */
    public final TableField<ProjectColumnsBackupRecord, Byte> DISTRIBUTED = createField("distributed", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "默认为1，下发属性，无需下发为0");

    /**
     * The column <code>SMETA_APP.project_columns_backup.tenant_code</code>. 租户code
     */
    public final TableField<ProjectColumnsBackupRecord, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("ali_taobao", org.jooq.impl.SQLDataType.VARCHAR)), this, "租户code");

    /**
     * Create a <code>SMETA_APP.project_columns_backup</code> table reference
     */
    public ProjectColumnsBackup() {
        this(DSL.name("project_columns_backup"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.project_columns_backup</code> table reference
     */
    public ProjectColumnsBackup(String alias) {
        this(DSL.name(alias), PROJECT_COLUMNS_BACKUP);
    }

    /**
     * Create an aliased <code>SMETA_APP.project_columns_backup</code> table reference
     */
    public ProjectColumnsBackup(Name alias) {
        this(alias, PROJECT_COLUMNS_BACKUP);
    }

    private ProjectColumnsBackup(Name alias, Table<ProjectColumnsBackupRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProjectColumnsBackup(Name alias, Table<ProjectColumnsBackupRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "应用字段关系表_备份表");
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
        return Arrays.<Index>asList(Indexes.PROJECT_COLUMNS_BACKUP_IDX_COLUMNCODE_EVENTCODE_PROJECTCODE, Indexes.PROJECT_COLUMNS_BACKUP_IDX_COLUMN_CODE, Indexes.PROJECT_COLUMNS_BACKUP_IDX_ENTITY_CODE, Indexes.PROJECT_COLUMNS_BACKUP_IDX_EVENTCODE_ENTITYCODE_PROJECTCODE, Indexes.PROJECT_COLUMNS_BACKUP_IDX_EVENT_CODE, Indexes.PROJECT_COLUMNS_BACKUP_IDX_EVENT_COLUMN_CODE, Indexes.PROJECT_COLUMNS_BACKUP_IDX_STATUS_PROJECT_EVENT_CODE, Indexes.PROJECT_COLUMNS_BACKUP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ProjectColumnsBackupRecord, ULong> getIdentity() {
        return Keys.IDENTITY_PROJECT_COLUMNS_BACKUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProjectColumnsBackupRecord> getPrimaryKey() {
        return Keys.KEY_PROJECT_COLUMNS_BACKUP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectColumnsBackupRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectColumnsBackupRecord>>asList(Keys.KEY_PROJECT_COLUMNS_BACKUP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectColumnsBackup as(String alias) {
        return new ProjectColumnsBackup(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectColumnsBackup as(Name alias) {
        return new ProjectColumnsBackup(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProjectColumnsBackup rename(String name) {
        return new ProjectColumnsBackup(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProjectColumnsBackup rename(Name name) {
        return new ProjectColumnsBackup(name, null);
    }
}
