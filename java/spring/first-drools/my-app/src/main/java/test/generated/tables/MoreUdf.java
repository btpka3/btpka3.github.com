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

import test.generated.Indexes;
import test.generated.Keys;
import test.generated.SmetaApp;
import test.generated.tables.records.MoreUdfRecord;


/**
 * udf信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MoreUdf extends TableImpl<MoreUdfRecord> {

    private static final long serialVersionUID = -1724127846;

    /**
     * The reference instance of <code>SMETA_APP.more_udf</code>
     */
    public static final MoreUdf MORE_UDF = new MoreUdf();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MoreUdfRecord> getRecordType() {
        return MoreUdfRecord.class;
    }

    /**
     * The column <code>SMETA_APP.more_udf.id</code>. 主键
     */
    public final TableField<MoreUdfRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.more_udf.gmt_create</code>. 创建时间
     */
    public final TableField<MoreUdfRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.more_udf.gmt_modified</code>. 修改时间
     */
    public final TableField<MoreUdfRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.more_udf.operator_id</code>. 操作人_id
     */
    public final TableField<MoreUdfRecord, String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "操作人_id");

    /**
     * The column <code>SMETA_APP.more_udf.operator</code>. 操作人
     */
    public final TableField<MoreUdfRecord, String> OPERATOR = createField("operator", org.jooq.impl.SQLDataType.VARCHAR(64), this, "操作人");

    /**
     * The column <code>SMETA_APP.more_udf.code</code>. 名称，做显示使用
     */
    public final TableField<MoreUdfRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "名称，做显示使用");

    /**
     * The column <code>SMETA_APP.more_udf.class_path</code>. 类的路径，包括包名和类名
     */
    public final TableField<MoreUdfRecord, String> CLASS_PATH = createField("class_path", org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "类的路径，包括包名和类名");

    /**
     * The column <code>SMETA_APP.more_udf.description</code>. 描述
     */
    public final TableField<MoreUdfRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "描述");

    /**
     * The column <code>SMETA_APP.more_udf.status</code>. 状态, 1-有效， -1-删除
     */
    public final TableField<MoreUdfRecord, Short> STATUS = createField("status", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "状态, 1-有效， -1-删除");

    /**
     * The column <code>SMETA_APP.more_udf.type</code>. 类型，1-用户定义，2-系统定义
     */
    public final TableField<MoreUdfRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "类型，1-用户定义，2-系统定义");

    /**
     * The column <code>SMETA_APP.more_udf.resource_type</code>. 资源类型，jar-jar包
     */
    public final TableField<MoreUdfRecord, String> RESOURCE_TYPE = createField("resource_type", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "资源类型，jar-jar包");

    /**
     * The column <code>SMETA_APP.more_udf.resource_id</code>. 对应的资源id
     */
    public final TableField<MoreUdfRecord, Long> RESOURCE_ID = createField("resource_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "对应的资源id");

    /**
     * The column <code>SMETA_APP.more_udf.resource_url</code>. 对应的资源的url地址
     */
    public final TableField<MoreUdfRecord, String> RESOURCE_URL = createField("resource_url", org.jooq.impl.SQLDataType.VARCHAR(512).nullable(false), this, "对应的资源的url地址");

    /**
     * The column <code>SMETA_APP.more_udf.blink_resource_id</code>. 对应的blink的资源id
     */
    public final TableField<MoreUdfRecord, String> BLINK_RESOURCE_ID = createField("blink_resource_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "对应的blink的资源id");

    /**
     * Create a <code>SMETA_APP.more_udf</code> table reference
     */
    public MoreUdf() {
        this(DSL.name("more_udf"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.more_udf</code> table reference
     */
    public MoreUdf(String alias) {
        this(DSL.name(alias), MORE_UDF);
    }

    /**
     * Create an aliased <code>SMETA_APP.more_udf</code> table reference
     */
    public MoreUdf(Name alias) {
        this(alias, MORE_UDF);
    }

    private MoreUdf(Name alias, Table<MoreUdfRecord> aliased) {
        this(alias, aliased, null);
    }

    private MoreUdf(Name alias, Table<MoreUdfRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "udf信息表");
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
        return Arrays.<Index>asList(Indexes.MORE_UDF_IDX_CODE, Indexes.MORE_UDF_PRIMARY, Indexes.MORE_UDF_UK_CLASSPATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MoreUdfRecord, Long> getIdentity() {
        return Keys.IDENTITY_MORE_UDF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MoreUdfRecord> getPrimaryKey() {
        return Keys.KEY_MORE_UDF_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MoreUdfRecord>> getKeys() {
        return Arrays.<UniqueKey<MoreUdfRecord>>asList(Keys.KEY_MORE_UDF_PRIMARY, Keys.KEY_MORE_UDF_UK_CLASSPATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MoreUdf as(String alias) {
        return new MoreUdf(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MoreUdf as(Name alias) {
        return new MoreUdf(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MoreUdf rename(String name) {
        return new MoreUdf(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MoreUdf rename(Name name) {
        return new MoreUdf(name, null);
    }
}
