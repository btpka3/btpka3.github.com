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
import test.generated.tables.records.DictEventEntitiesRecord;


/**
 * 事件实体关系
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictEventEntities extends TableImpl<DictEventEntitiesRecord> {

    private static final long serialVersionUID = -1679190849;

    /**
     * The reference instance of <code>SMETA_APP.dict_event_entities</code>
     */
    public static final DictEventEntities DICT_EVENT_ENTITIES = new DictEventEntities();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictEventEntitiesRecord> getRecordType() {
        return DictEventEntitiesRecord.class;
    }

    /**
     * The column <code>SMETA_APP.dict_event_entities.id</code>. 主键
     */
    public final TableField<DictEventEntitiesRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.dict_event_entities.gmt_create</code>. 创建时间
     */
    public final TableField<DictEventEntitiesRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.dict_event_entities.gmt_modified</code>. 修改时间
     */
    public final TableField<DictEventEntitiesRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.dict_event_entities.event_code</code>. 事件编码
     */
    public final TableField<DictEventEntitiesRecord, String> EVENT_CODE = createField("event_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "事件编码");

    /**
     * The column <code>SMETA_APP.dict_event_entities.entity_code</code>. 实体编码
     */
    public final TableField<DictEventEntitiesRecord, String> ENTITY_CODE = createField("entity_code", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "实体编码");

    /**
     * The column <code>SMETA_APP.dict_event_entities.status</code>. 状态：1有效，2草稿，3审批中
     */
    public final TableField<DictEventEntitiesRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "状态：1有效，2草稿，3审批中");

    /**
     * The column <code>SMETA_APP.dict_event_entities.operator_id</code>. 操作人ID
     */
    public final TableField<DictEventEntitiesRecord, String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人ID");

    /**
     * The column <code>SMETA_APP.dict_event_entities.operator</code>. 操作人
     */
    public final TableField<DictEventEntitiesRecord, String> OPERATOR = createField("operator", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人");

    /**
     * The column <code>SMETA_APP.dict_event_entities.script</code>. 实体补全脚本
     */
    public final TableField<DictEventEntitiesRecord, String> SCRIPT = createField("script", org.jooq.impl.SQLDataType.CLOB, this, "实体补全脚本");

    /**
     * The column <code>SMETA_APP.dict_event_entities.tenant_code</code>. 租户code
     */
    public final TableField<DictEventEntitiesRecord, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(64).defaultValue(org.jooq.impl.DSL.inline("ali_taobao", org.jooq.impl.SQLDataType.VARCHAR)), this, "租户code");

    /**
     * The column <code>SMETA_APP.dict_event_entities.offline_para</code>. 离线入参
     */
    public final TableField<DictEventEntitiesRecord, String> OFFLINE_PARA = createField("offline_para", org.jooq.impl.SQLDataType.VARCHAR(64), this, "离线入参");

    /**
     * The column <code>SMETA_APP.dict_event_entities.func_script</code>. 前端调用的函数脚本
     */
    public final TableField<DictEventEntitiesRecord, String> FUNC_SCRIPT = createField("func_script", org.jooq.impl.SQLDataType.VARCHAR(512), this, "前端调用的函数脚本");

    /**
     * The column <code>SMETA_APP.dict_event_entities.entity_pk</code>. 实体关联主键（用于离线分析计算）
     */
    public final TableField<DictEventEntitiesRecord, String> ENTITY_PK = createField("entity_pk", org.jooq.impl.SQLDataType.VARCHAR(128), this, "实体关联主键（用于离线分析计算）");

    /**
     * The column <code>SMETA_APP.dict_event_entities.offline_table_pk</code>. 实体对应离线表关联主键（用于离线分析计算）
     */
    public final TableField<DictEventEntitiesRecord, String> OFFLINE_TABLE_PK = createField("offline_table_pk", org.jooq.impl.SQLDataType.VARCHAR(128), this, "实体对应离线表关联主键（用于离线分析计算）");

    /**
     * Create a <code>SMETA_APP.dict_event_entities</code> table reference
     */
    public DictEventEntities() {
        this(DSL.name("dict_event_entities"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.dict_event_entities</code> table reference
     */
    public DictEventEntities(String alias) {
        this(DSL.name(alias), DICT_EVENT_ENTITIES);
    }

    /**
     * Create an aliased <code>SMETA_APP.dict_event_entities</code> table reference
     */
    public DictEventEntities(Name alias) {
        this(alias, DICT_EVENT_ENTITIES);
    }

    private DictEventEntities(Name alias, Table<DictEventEntitiesRecord> aliased) {
        this(alias, aliased, null);
    }

    private DictEventEntities(Name alias, Table<DictEventEntitiesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "事件实体关系");
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
        return Arrays.<Index>asList(Indexes.DICT_EVENT_ENTITIES_IDX_ENTITY_CODE, Indexes.DICT_EVENT_ENTITIES_IDX_EVENT_CODE_ENTITY_CODE, Indexes.DICT_EVENT_ENTITIES_PRIMARY, Indexes.DICT_EVENT_ENTITIES_UK_EVENT_CODE_ENTITY_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DictEventEntitiesRecord, ULong> getIdentity() {
        return Keys.IDENTITY_DICT_EVENT_ENTITIES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DictEventEntitiesRecord> getPrimaryKey() {
        return Keys.KEY_DICT_EVENT_ENTITIES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictEventEntitiesRecord>> getKeys() {
        return Arrays.<UniqueKey<DictEventEntitiesRecord>>asList(Keys.KEY_DICT_EVENT_ENTITIES_PRIMARY, Keys.KEY_DICT_EVENT_ENTITIES_UK_EVENT_CODE_ENTITY_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictEventEntities as(String alias) {
        return new DictEventEntities(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictEventEntities as(Name alias) {
        return new DictEventEntities(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictEventEntities rename(String name) {
        return new DictEventEntities(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DictEventEntities rename(Name name) {
        return new DictEventEntities(name, null);
    }
}