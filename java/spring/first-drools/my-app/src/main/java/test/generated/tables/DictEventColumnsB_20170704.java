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
import test.generated.tables.records.DictEventColumnsB_20170704Record;


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
public class DictEventColumnsB_20170704 extends TableImpl<DictEventColumnsB_20170704Record> {

    private static final long serialVersionUID = 1192182266;

    /**
     * The reference instance of <code>SMETA_APP.dict_event_columns_b_20170704</code>
     */
    public static final DictEventColumnsB_20170704 DICT_EVENT_COLUMNS_B_20170704 = new DictEventColumnsB_20170704();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictEventColumnsB_20170704Record> getRecordType() {
        return DictEventColumnsB_20170704Record.class;
    }

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.id</code>. 主键
     */
    public final TableField<DictEventColumnsB_20170704Record, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.gmt_create</code>. 创建时间
     */
    public final TableField<DictEventColumnsB_20170704Record, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.gmt_modified</code>. 修改时间
     */
    public final TableField<DictEventColumnsB_20170704Record, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.event_code</code>. 事件编码
     */
    public final TableField<DictEventColumnsB_20170704Record, String> EVENT_CODE = createField("event_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "事件编码");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.column_code</code>. 字段编码
     */
    public final TableField<DictEventColumnsB_20170704Record, String> COLUMN_CODE = createField("column_code", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "字段编码");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.event_column_code</code>. 事件下唯一字段。有实体：实体code_字段code，没实体：和字段code一样
     */
    public final TableField<DictEventColumnsB_20170704Record, String> EVENT_COLUMN_CODE = createField("event_column_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "事件下唯一字段。有实体：实体code_字段code，没实体：和字段code一样");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.status</code>. 状态：1有效，2草稿，3审批中
     */
    public final TableField<DictEventColumnsB_20170704Record, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "状态：1有效，2草稿，3审批中");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.operator_id</code>. 操作人ID
     */
    public final TableField<DictEventColumnsB_20170704Record, String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人ID");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.operator</code>. 操作人
     */
    public final TableField<DictEventColumnsB_20170704Record, String> OPERATOR = createField("operator", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.empty_status</code>. 是否非空，1：是，0：否
     */
    public final TableField<DictEventColumnsB_20170704Record, Byte> EMPTY_STATUS = createField("empty_status", org.jooq.impl.SQLDataType.TINYINT, this, "是否非空，1：是，0：否");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.enum_status</code>. 是否可枚举，1：是，0：否
     */
    public final TableField<DictEventColumnsB_20170704Record, Byte> ENUM_STATUS = createField("enum_status", org.jooq.impl.SQLDataType.TINYINT, this, "是否可枚举，1：是，0：否");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.enum_value</code>. 枚举值，key：value格式存储
     */
    public final TableField<DictEventColumnsB_20170704Record, String> ENUM_VALUE = createField("enum_value", org.jooq.impl.SQLDataType.CLOB, this, "枚举值，key：value格式存储");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.script</code>. 自定义脚本
     */
    public final TableField<DictEventColumnsB_20170704Record, String> SCRIPT = createField("script", org.jooq.impl.SQLDataType.CLOB, this, "自定义脚本");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.attr_type</code>. 属性分类：1原生属性，2函数属性，3实体属性
     */
    public final TableField<DictEventColumnsB_20170704Record, Byte> ATTR_TYPE = createField("attr_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "属性分类：1原生属性，2函数属性，3实体属性");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.comment</code>. 备注
     */
    public final TableField<DictEventColumnsB_20170704Record, String> COMMENT = createField("comment", org.jooq.impl.SQLDataType.CLOB, this, "备注");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.entity_code</code>. 实体编码
     */
    public final TableField<DictEventColumnsB_20170704Record, String> ENTITY_CODE = createField("entity_code", org.jooq.impl.SQLDataType.VARCHAR(64), this, "实体编码");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.indi_property</code>. 指标的时间和主键信息
     */
    public final TableField<DictEventColumnsB_20170704Record, String> INDI_PROPERTY = createField("indi_property", org.jooq.impl.SQLDataType.CLOB, this, "指标的时间和主键信息");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.indi_code</code>. 指标中心指标code
     */
    public final TableField<DictEventColumnsB_20170704Record, String> INDI_CODE = createField("indi_code", org.jooq.impl.SQLDataType.VARCHAR(128), this, "指标中心指标code");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.indi_type</code>. 指标类型
     */
    public final TableField<DictEventColumnsB_20170704Record, String> INDI_TYPE = createField("indi_type", org.jooq.impl.SQLDataType.VARCHAR(32), this, "指标类型");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.tenant_code</code>. 租户code
     */
    public final TableField<DictEventColumnsB_20170704Record, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(64).defaultValue(org.jooq.impl.DSL.inline("ali_taobao", org.jooq.impl.SQLDataType.VARCHAR)), this, "租户code");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.tag_code</code>. 属性打标code
     */
    public final TableField<DictEventColumnsB_20170704Record, String> TAG_CODE = createField("tag_code", org.jooq.impl.SQLDataType.VARCHAR(64), this, "属性打标code");

    /**
     * The column <code>SMETA_APP.dict_event_columns_b_20170704.type</code>. 字段类型
     */
    public final TableField<DictEventColumnsB_20170704Record, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(64), this, "字段类型");

    /**
     * Create a <code>SMETA_APP.dict_event_columns_b_20170704</code> table reference
     */
    public DictEventColumnsB_20170704() {
        this(DSL.name("dict_event_columns_b_20170704"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.dict_event_columns_b_20170704</code> table reference
     */
    public DictEventColumnsB_20170704(String alias) {
        this(DSL.name(alias), DICT_EVENT_COLUMNS_B_20170704);
    }

    /**
     * Create an aliased <code>SMETA_APP.dict_event_columns_b_20170704</code> table reference
     */
    public DictEventColumnsB_20170704(Name alias) {
        this(alias, DICT_EVENT_COLUMNS_B_20170704);
    }

    private DictEventColumnsB_20170704(Name alias, Table<DictEventColumnsB_20170704Record> aliased) {
        this(alias, aliased, null);
    }

    private DictEventColumnsB_20170704(Name alias, Table<DictEventColumnsB_20170704Record> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.DICT_EVENT_COLUMNS_B_20170704_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DictEventColumnsB_20170704Record, ULong> getIdentity() {
        return Keys.IDENTITY_DICT_EVENT_COLUMNS_B_20170704;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DictEventColumnsB_20170704Record> getPrimaryKey() {
        return Keys.KEY_DICT_EVENT_COLUMNS_B_20170704_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictEventColumnsB_20170704Record>> getKeys() {
        return Arrays.<UniqueKey<DictEventColumnsB_20170704Record>>asList(Keys.KEY_DICT_EVENT_COLUMNS_B_20170704_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictEventColumnsB_20170704 as(String alias) {
        return new DictEventColumnsB_20170704(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictEventColumnsB_20170704 as(Name alias) {
        return new DictEventColumnsB_20170704(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictEventColumnsB_20170704 rename(String name) {
        return new DictEventColumnsB_20170704(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DictEventColumnsB_20170704 rename(Name name) {
        return new DictEventColumnsB_20170704(name, null);
    }
}