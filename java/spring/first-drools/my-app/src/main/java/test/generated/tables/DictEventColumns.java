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
import test.generated.tables.records.DictEventColumnsRecord;


/**
 * 事件字段关系表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictEventColumns extends TableImpl<DictEventColumnsRecord> {

    private static final long serialVersionUID = 303998426;

    /**
     * The reference instance of <code>SMETA_APP.dict_event_columns</code>
     */
    public static final DictEventColumns DICT_EVENT_COLUMNS = new DictEventColumns();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictEventColumnsRecord> getRecordType() {
        return DictEventColumnsRecord.class;
    }

    /**
     * The column <code>SMETA_APP.dict_event_columns.id</code>. 主键
     */
    public final TableField<DictEventColumnsRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.dict_event_columns.gmt_create</code>. 创建时间
     */
    public final TableField<DictEventColumnsRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.dict_event_columns.gmt_modified</code>. 修改时间
     */
    public final TableField<DictEventColumnsRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.dict_event_columns.event_code</code>. 事件编码
     */
    public final TableField<DictEventColumnsRecord, String> EVENT_CODE = createField("event_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "事件编码");

    /**
     * The column <code>SMETA_APP.dict_event_columns.column_code</code>. 字段编码
     */
    public final TableField<DictEventColumnsRecord, String> COLUMN_CODE = createField("column_code", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "字段编码");

    /**
     * The column <code>SMETA_APP.dict_event_columns.event_column_code</code>. 事件下唯一字段。有实体：实体code_字段code，没实体：和字段code一样
     */
    public final TableField<DictEventColumnsRecord, String> EVENT_COLUMN_CODE = createField("event_column_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "事件下唯一字段。有实体：实体code_字段code，没实体：和字段code一样");

    /**
     * The column <code>SMETA_APP.dict_event_columns.status</code>. 状态：1有效，2草稿，3审批中 5伪删除的无效状态
     */
    public final TableField<DictEventColumnsRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "状态：1有效，2草稿，3审批中 5伪删除的无效状态");

    /**
     * The column <code>SMETA_APP.dict_event_columns.operator_id</code>. 操作人ID
     */
    public final TableField<DictEventColumnsRecord, String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人ID");

    /**
     * The column <code>SMETA_APP.dict_event_columns.operator</code>. 操作人
     */
    public final TableField<DictEventColumnsRecord, String> OPERATOR = createField("operator", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人");

    /**
     * The column <code>SMETA_APP.dict_event_columns.empty_status</code>. 是否非空，1：是，0：否
     */
    public final TableField<DictEventColumnsRecord, Byte> EMPTY_STATUS = createField("empty_status", org.jooq.impl.SQLDataType.TINYINT, this, "是否非空，1：是，0：否");

    /**
     * The column <code>SMETA_APP.dict_event_columns.enum_status</code>. 是否可枚举，1：是，0：否
     */
    public final TableField<DictEventColumnsRecord, Byte> ENUM_STATUS = createField("enum_status", org.jooq.impl.SQLDataType.TINYINT, this, "是否可枚举，1：是，0：否");

    /**
     * The column <code>SMETA_APP.dict_event_columns.enum_value</code>. 枚举值，key：value格式存储
     */
    public final TableField<DictEventColumnsRecord, String> ENUM_VALUE = createField("enum_value", org.jooq.impl.SQLDataType.CLOB, this, "枚举值，key：value格式存储");

    /**
     * The column <code>SMETA_APP.dict_event_columns.script</code>. 自定义脚本
     */
    public final TableField<DictEventColumnsRecord, String> SCRIPT = createField("script", org.jooq.impl.SQLDataType.CLOB, this, "自定义脚本");

    /**
     * The column <code>SMETA_APP.dict_event_columns.attr_type</code>. 属性分类：1原生属性，2函数属性，3实体属性
     */
    public final TableField<DictEventColumnsRecord, Byte> ATTR_TYPE = createField("attr_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "属性分类：1原生属性，2函数属性，3实体属性");

    /**
     * The column <code>SMETA_APP.dict_event_columns.comment</code>. 备注
     */
    public final TableField<DictEventColumnsRecord, String> COMMENT = createField("comment", org.jooq.impl.SQLDataType.CLOB, this, "备注");

    /**
     * The column <code>SMETA_APP.dict_event_columns.entity_code</code>. 实体编码
     */
    public final TableField<DictEventColumnsRecord, String> ENTITY_CODE = createField("entity_code", org.jooq.impl.SQLDataType.VARCHAR(64), this, "实体编码");

    /**
     * The column <code>SMETA_APP.dict_event_columns.indi_property</code>. 指标的时间和主键信息
     */
    public final TableField<DictEventColumnsRecord, String> INDI_PROPERTY = createField("indi_property", org.jooq.impl.SQLDataType.CLOB, this, "指标的时间和主键信息");

    /**
     * The column <code>SMETA_APP.dict_event_columns.indi_code</code>. 指标中心指标code
     */
    public final TableField<DictEventColumnsRecord, String> INDI_CODE = createField("indi_code", org.jooq.impl.SQLDataType.VARCHAR(128), this, "指标中心指标code");

    /**
     * The column <code>SMETA_APP.dict_event_columns.indi_type</code>. 指标类型
     */
    public final TableField<DictEventColumnsRecord, String> INDI_TYPE = createField("indi_type", org.jooq.impl.SQLDataType.VARCHAR(32), this, "指标类型");

    /**
     * The column <code>SMETA_APP.dict_event_columns.tenant_code</code>. 租户code
     */
    public final TableField<DictEventColumnsRecord, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(64).defaultValue(org.jooq.impl.DSL.inline("ali_taobao", org.jooq.impl.SQLDataType.VARCHAR)), this, "租户code");

    /**
     * The column <code>SMETA_APP.dict_event_columns.tag_code</code>. 属性打标code
     */
    public final TableField<DictEventColumnsRecord, String> TAG_CODE = createField("tag_code", org.jooq.impl.SQLDataType.VARCHAR(64), this, "属性打标code");

    /**
     * The column <code>SMETA_APP.dict_event_columns.type</code>. 字段类型
     */
    public final TableField<DictEventColumnsRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(64), this, "字段类型");

    /**
     * The column <code>SMETA_APP.dict_event_columns.offline_script</code>. 离线取数脚本
     */
    public final TableField<DictEventColumnsRecord, String> OFFLINE_SCRIPT = createField("offline_script", org.jooq.impl.SQLDataType.CLOB, this, "离线取数脚本");

    /**
     * The column <code>SMETA_APP.dict_event_columns.biz_level</code>. 业务等级
     */
    public final TableField<DictEventColumnsRecord, String> BIZ_LEVEL = createField("biz_level", org.jooq.impl.SQLDataType.VARCHAR(32), this, "业务等级");

    /**
     * The column <code>SMETA_APP.dict_event_columns.offline_column</code>. 离线表里对应的列名
     */
    public final TableField<DictEventColumnsRecord, String> OFFLINE_COLUMN = createField("offline_column", org.jooq.impl.SQLDataType.VARCHAR(512), this, "离线表里对应的列名");

    /**
     * The column <code>SMETA_APP.dict_event_columns.func_script</code>. 取数函数脚本
     */
    public final TableField<DictEventColumnsRecord, String> FUNC_SCRIPT = createField("func_script", org.jooq.impl.SQLDataType.VARCHAR(512), this, "取数函数脚本");

    /**
     * Create a <code>SMETA_APP.dict_event_columns</code> table reference
     */
    public DictEventColumns() {
        this(DSL.name("dict_event_columns"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.dict_event_columns</code> table reference
     */
    public DictEventColumns(String alias) {
        this(DSL.name(alias), DICT_EVENT_COLUMNS);
    }

    /**
     * Create an aliased <code>SMETA_APP.dict_event_columns</code> table reference
     */
    public DictEventColumns(Name alias) {
        this(alias, DICT_EVENT_COLUMNS);
    }

    private DictEventColumns(Name alias, Table<DictEventColumnsRecord> aliased) {
        this(alias, aliased, null);
    }

    private DictEventColumns(Name alias, Table<DictEventColumnsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "事件字段关系表");
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
        return Arrays.<Index>asList(Indexes.DICT_EVENT_COLUMNS_IDX_COLUMN_CODE_EVENT_CODE, Indexes.DICT_EVENT_COLUMNS_IDX_CO_EN_EV, Indexes.DICT_EVENT_COLUMNS_IDX_ENTITY_CODE_EVENT_CODE, Indexes.DICT_EVENT_COLUMNS_IDX_EVENT_CODE, Indexes.DICT_EVENT_COLUMNS_IDX_EVENT_COLUMN_CODE, Indexes.DICT_EVENT_COLUMNS_PRIMARY, Indexes.DICT_EVENT_COLUMNS_UK_EVENT_COLUMN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DictEventColumnsRecord, ULong> getIdentity() {
        return Keys.IDENTITY_DICT_EVENT_COLUMNS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DictEventColumnsRecord> getPrimaryKey() {
        return Keys.KEY_DICT_EVENT_COLUMNS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictEventColumnsRecord>> getKeys() {
        return Arrays.<UniqueKey<DictEventColumnsRecord>>asList(Keys.KEY_DICT_EVENT_COLUMNS_PRIMARY, Keys.KEY_DICT_EVENT_COLUMNS_UK_EVENT_COLUMN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictEventColumns as(String alias) {
        return new DictEventColumns(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictEventColumns as(Name alias) {
        return new DictEventColumns(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictEventColumns rename(String name) {
        return new DictEventColumns(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DictEventColumns rename(Name name) {
        return new DictEventColumns(name, null);
    }
}
