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
import test.generated.tables.records.VbsFunctionRecord;


/**
 * 补全函数
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VbsFunction extends TableImpl<VbsFunctionRecord> {

    private static final long serialVersionUID = -1636834082;

    /**
     * The reference instance of <code>SMETA_APP.vbs_function</code>
     */
    public static final VbsFunction VBS_FUNCTION = new VbsFunction();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VbsFunctionRecord> getRecordType() {
        return VbsFunctionRecord.class;
    }

    /**
     * The column <code>SMETA_APP.vbs_function.id</code>. 主键
     */
    public final TableField<VbsFunctionRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.vbs_function.gmt_create</code>. 创建时间
     */
    public final TableField<VbsFunctionRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.vbs_function.gmt_modified</code>. 修改时间
     */
    public final TableField<VbsFunctionRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.vbs_function.code</code>. 函数名称、code
     */
    public final TableField<VbsFunctionRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "函数名称、code");

    /**
     * The column <code>SMETA_APP.vbs_function.name</code>. 函数名称
     */
    public final TableField<VbsFunctionRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "函数名称");

    /**
     * The column <code>SMETA_APP.vbs_function.catalogcode</code>. 函数类别code
     */
    public final TableField<VbsFunctionRecord, String> CATALOGCODE = createField("catalogcode", org.jooq.impl.SQLDataType.VARCHAR(100), this, "函数类别code");

    /**
     * The column <code>SMETA_APP.vbs_function.returntype</code>. 返回类型
     */
    public final TableField<VbsFunctionRecord, String> RETURNTYPE = createField("returntype", org.jooq.impl.SQLDataType.VARCHAR(100), this, "返回类型");

    /**
     * The column <code>SMETA_APP.vbs_function.comment</code>. 注释、描述
     */
    public final TableField<VbsFunctionRecord, String> COMMENT = createField("comment", org.jooq.impl.SQLDataType.VARCHAR(500), this, "注释、描述");

    /**
     * The column <code>SMETA_APP.vbs_function.args</code>. 参数签名
     */
    public final TableField<VbsFunctionRecord, String> ARGS = createField("args", org.jooq.impl.SQLDataType.CLOB, this, "参数签名");

    /**
     * The column <code>SMETA_APP.vbs_function.creator</code>. 创建人
     */
    public final TableField<VbsFunctionRecord, String> CREATOR = createField("creator", org.jooq.impl.SQLDataType.VARCHAR(50), this, "创建人");

    /**
     * The column <code>SMETA_APP.vbs_function.body</code>. 函数体
     */
    public final TableField<VbsFunctionRecord, String> BODY = createField("body", org.jooq.impl.SQLDataType.CLOB, this, "函数体");

    /**
     * The column <code>SMETA_APP.vbs_function.lastoperator</code>. 最后更新人
     */
    public final TableField<VbsFunctionRecord, String> LASTOPERATOR = createField("lastoperator", org.jooq.impl.SQLDataType.VARCHAR(50), this, "最后更新人");

    /**
     * The column <code>SMETA_APP.vbs_function.status</code>. 状态
     */
    public final TableField<VbsFunctionRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER, this, "状态");

    /**
     * The column <code>SMETA_APP.vbs_function.testscript</code>. 测试脚本
     */
    public final TableField<VbsFunctionRecord, String> TESTSCRIPT = createField("testscript", org.jooq.impl.SQLDataType.CLOB, this, "测试脚本");

    /**
     * The column <code>SMETA_APP.vbs_function.weight</code>. 成本
     */
    public final TableField<VbsFunctionRecord, Integer> WEIGHT = createField("weight", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "成本");

    /**
     * Create a <code>SMETA_APP.vbs_function</code> table reference
     */
    public VbsFunction() {
        this(DSL.name("vbs_function"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.vbs_function</code> table reference
     */
    public VbsFunction(String alias) {
        this(DSL.name(alias), VBS_FUNCTION);
    }

    /**
     * Create an aliased <code>SMETA_APP.vbs_function</code> table reference
     */
    public VbsFunction(Name alias) {
        this(alias, VBS_FUNCTION);
    }

    private VbsFunction(Name alias, Table<VbsFunctionRecord> aliased) {
        this(alias, aliased, null);
    }

    private VbsFunction(Name alias, Table<VbsFunctionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "补全函数");
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
        return Arrays.<Index>asList(Indexes.VBS_FUNCTION_IDX_CODE_INDEX, Indexes.VBS_FUNCTION_IDX_MODIFIED_DATE, Indexes.VBS_FUNCTION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<VbsFunctionRecord, ULong> getIdentity() {
        return Keys.IDENTITY_VBS_FUNCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<VbsFunctionRecord> getPrimaryKey() {
        return Keys.KEY_VBS_FUNCTION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<VbsFunctionRecord>> getKeys() {
        return Arrays.<UniqueKey<VbsFunctionRecord>>asList(Keys.KEY_VBS_FUNCTION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VbsFunction as(String alias) {
        return new VbsFunction(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VbsFunction as(Name alias) {
        return new VbsFunction(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public VbsFunction rename(String name) {
        return new VbsFunction(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public VbsFunction rename(Name name) {
        return new VbsFunction(name, null);
    }
}
