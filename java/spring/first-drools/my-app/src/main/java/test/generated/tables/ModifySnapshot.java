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
import test.generated.tables.records.ModifySnapshotRecord;


/**
 * 有效属性或实体修改快照表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ModifySnapshot extends TableImpl<ModifySnapshotRecord> {

    private static final long serialVersionUID = 1525381883;

    /**
     * The reference instance of <code>SMETA_APP.modify_snapshot</code>
     */
    public static final ModifySnapshot MODIFY_SNAPSHOT = new ModifySnapshot();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ModifySnapshotRecord> getRecordType() {
        return ModifySnapshotRecord.class;
    }

    /**
     * The column <code>SMETA_APP.modify_snapshot.id</code>. 主键
     */
    public final TableField<ModifySnapshotRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.modify_snapshot.gmt_create</code>. 创建时间
     */
    public final TableField<ModifySnapshotRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.modify_snapshot.gmt_modified</code>. 修改时间
     */
    public final TableField<ModifySnapshotRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.modify_snapshot.operator_id</code>. 操作人ID
     */
    public final TableField<ModifySnapshotRecord, String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人ID");

    /**
     * The column <code>SMETA_APP.modify_snapshot.operator</code>. 操作人
     */
    public final TableField<ModifySnapshotRecord, String> OPERATOR = createField("operator", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人");

    /**
     * The column <code>SMETA_APP.modify_snapshot.event_code</code>. 事件code
     */
    public final TableField<ModifySnapshotRecord, String> EVENT_CODE = createField("event_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "事件code");

    /**
     * The column <code>SMETA_APP.modify_snapshot.entity_code</code>. 实体code
     */
    public final TableField<ModifySnapshotRecord, String> ENTITY_CODE = createField("entity_code", org.jooq.impl.SQLDataType.VARCHAR(128), this, "实体code");

    /**
     * The column <code>SMETA_APP.modify_snapshot.column_code</code>. 属性code
     */
    public final TableField<ModifySnapshotRecord, String> COLUMN_CODE = createField("column_code", org.jooq.impl.SQLDataType.VARCHAR(128), this, "属性code");

    /**
     * The column <code>SMETA_APP.modify_snapshot.snapshot</code>. 属性或者实体快照
     */
    public final TableField<ModifySnapshotRecord, byte[]> SNAPSHOT = createField("snapshot", org.jooq.impl.SQLDataType.BLOB.nullable(false), this, "属性或者实体快照");

    /**
     * The column <code>SMETA_APP.modify_snapshot.status</code>. 快照四种状态：0 不通过 1 通过 2 修改中 3 审核中
     */
    public final TableField<ModifySnapshotRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "快照四种状态：0 不通过 1 通过 2 修改中 3 审核中");

    /**
     * Create a <code>SMETA_APP.modify_snapshot</code> table reference
     */
    public ModifySnapshot() {
        this(DSL.name("modify_snapshot"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.modify_snapshot</code> table reference
     */
    public ModifySnapshot(String alias) {
        this(DSL.name(alias), MODIFY_SNAPSHOT);
    }

    /**
     * Create an aliased <code>SMETA_APP.modify_snapshot</code> table reference
     */
    public ModifySnapshot(Name alias) {
        this(alias, MODIFY_SNAPSHOT);
    }

    private ModifySnapshot(Name alias, Table<ModifySnapshotRecord> aliased) {
        this(alias, aliased, null);
    }

    private ModifySnapshot(Name alias, Table<ModifySnapshotRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "有效属性或实体修改快照表");
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
        return Arrays.<Index>asList(Indexes.MODIFY_SNAPSHOT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ModifySnapshotRecord, ULong> getIdentity() {
        return Keys.IDENTITY_MODIFY_SNAPSHOT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ModifySnapshotRecord> getPrimaryKey() {
        return Keys.KEY_MODIFY_SNAPSHOT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ModifySnapshotRecord>> getKeys() {
        return Arrays.<UniqueKey<ModifySnapshotRecord>>asList(Keys.KEY_MODIFY_SNAPSHOT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModifySnapshot as(String alias) {
        return new ModifySnapshot(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModifySnapshot as(Name alias) {
        return new ModifySnapshot(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ModifySnapshot rename(String name) {
        return new ModifySnapshot(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ModifySnapshot rename(Name name) {
        return new ModifySnapshot(name, null);
    }
}