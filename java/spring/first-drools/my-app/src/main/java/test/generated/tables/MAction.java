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
import test.generated.tables.records.MActionRecord;


/**
 * 处置动作表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MAction extends TableImpl<MActionRecord> {

    private static final long serialVersionUID = 2591650;

    /**
     * The reference instance of <code>SMETA_APP.m_action</code>
     */
    public static final MAction M_ACTION = new MAction();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MActionRecord> getRecordType() {
        return MActionRecord.class;
    }

    /**
     * The column <code>SMETA_APP.m_action.id</code>. id
     */
    public final TableField<MActionRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "id");

    /**
     * The column <code>SMETA_APP.m_action.code</code>. 动作名称、code
     */
    public final TableField<MActionRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR(100), this, "动作名称、code");

    /**
     * The column <code>SMETA_APP.m_action.friendlyname</code>. 友好的名称
     */
    public final TableField<MActionRecord, String> FRIENDLYNAME = createField("friendlyname", org.jooq.impl.SQLDataType.VARCHAR(100), this, "友好的名称");

    /**
     * The column <code>SMETA_APP.m_action.catalogcode</code>. 类别
     */
    public final TableField<MActionRecord, String> CATALOGCODE = createField("catalogcode", org.jooq.impl.SQLDataType.VARCHAR(100), this, "类别");

    /**
     * The column <code>SMETA_APP.m_action.catalogname</code>. 类别名称 2012-08-09
     */
    public final TableField<MActionRecord, String> CATALOGNAME = createField("catalogname", org.jooq.impl.SQLDataType.VARCHAR(50), this, "类别名称 2012-08-09");

    /**
     * The column <code>SMETA_APP.m_action.returntype</code>. 返回值类型，注意要能在meta里找到，以实现自动提示
     */
    public final TableField<MActionRecord, String> RETURNTYPE = createField("returntype", org.jooq.impl.SQLDataType.VARCHAR(100), this, "返回值类型，注意要能在meta里找到，以实现自动提示");

    /**
     * The column <code>SMETA_APP.m_action.comment</code>. 动作功能说明，返回值说明
     */
    public final TableField<MActionRecord, String> COMMENT = createField("comment", org.jooq.impl.SQLDataType.VARCHAR(500), this, "动作功能说明，返回值说明");

    /**
     * The column <code>SMETA_APP.m_action.args</code>. 动作参数说明，要包括动作参数名称、类型、参数说明
     */
    public final TableField<MActionRecord, String> ARGS = createField("args", org.jooq.impl.SQLDataType.CLOB, this, "动作参数说明，要包括动作参数名称、类型、参数说明");

    /**
     * The column <code>SMETA_APP.m_action.announcement</code>. 该动作注意事项
     */
    public final TableField<MActionRecord, String> ANNOUNCEMENT = createField("announcement", org.jooq.impl.SQLDataType.CLOB, this, "该动作注意事项");

    /**
     * The column <code>SMETA_APP.m_action.metadata</code>. 动作元数据，比如用到了哪些组件
     */
    public final TableField<MActionRecord, String> METADATA = createField("metadata", org.jooq.impl.SQLDataType.VARCHAR(500), this, "动作元数据，比如用到了哪些组件");

    /**
     * The column <code>SMETA_APP.m_action.initializationneeded</code>. 是否需要初始化，一般脚本动作则需要
     */
    public final TableField<MActionRecord, Integer> INITIALIZATIONNEEDED = createField("initializationneeded", org.jooq.impl.SQLDataType.INTEGER, this, "是否需要初始化，一般脚本动作则需要");

    /**
     * The column <code>SMETA_APP.m_action.initializationinfo</code>. 初始化信息，比如何种脚本，如何初始化
     */
    public final TableField<MActionRecord, String> INITIALIZATIONINFO = createField("initializationinfo", org.jooq.impl.SQLDataType.VARCHAR(100), this, "初始化信息，比如何种脚本，如何初始化");

    /**
     * The column <code>SMETA_APP.m_action.cacheincluded</code>. 是否包含缓存
     */
    public final TableField<MActionRecord, Integer> CACHEINCLUDED = createField("cacheincluded", org.jooq.impl.SQLDataType.INTEGER, this, "是否包含缓存");

    /**
     * The column <code>SMETA_APP.m_action.createtime</code>. 创建时间
     */
    public final TableField<MActionRecord, Timestamp> CREATETIME = createField("createtime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>SMETA_APP.m_action.creator</code>. 添加人
     */
    public final TableField<MActionRecord, String> CREATOR = createField("creator", org.jooq.impl.SQLDataType.VARCHAR(50), this, "添加人");

    /**
     * The column <code>SMETA_APP.m_action.body</code>. 动作体，用于基本动作，需要初始化
     */
    public final TableField<MActionRecord, String> BODY = createField("body", org.jooq.impl.SQLDataType.CLOB, this, "动作体，用于基本动作，需要初始化");

    /**
     * The column <code>SMETA_APP.m_action.lastupdatetime</code>. 最后修改时间
     */
    public final TableField<MActionRecord, Timestamp> LASTUPDATETIME = createField("lastupdatetime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "最后修改时间");

    /**
     * The column <code>SMETA_APP.m_action.lastoperator</code>. 最后操作人
     */
    public final TableField<MActionRecord, String> LASTOPERATOR = createField("lastoperator", org.jooq.impl.SQLDataType.VARCHAR(100), this, "最后操作人");

    /**
     * The column <code>SMETA_APP.m_action.editor</code>. 2013-02-27 何种编辑器编辑的
     */
    public final TableField<MActionRecord, String> EDITOR = createField("editor", org.jooq.impl.SQLDataType.VARCHAR(100), this, "2013-02-27 何种编辑器编辑的");

    /**
     * The column <code>SMETA_APP.m_action.authorizationcode</code>. 授权码 2013-03-05
     */
    public final TableField<MActionRecord, String> AUTHORIZATIONCODE = createField("authorizationcode", org.jooq.impl.SQLDataType.VARCHAR(500), this, "授权码 2013-03-05");

    /**
     * The column <code>SMETA_APP.m_action.status</code>. 状态,0表示正常,-100表示废弃
     */
    public final TableField<MActionRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "状态,0表示正常,-100表示废弃");

    /**
     * The column <code>SMETA_APP.m_action.testcasescript</code>. 测试用例脚本
     */
    public final TableField<MActionRecord, String> TESTCASESCRIPT = createField("testcasescript", org.jooq.impl.SQLDataType.CLOB, this, "测试用例脚本");

    /**
     * The column <code>SMETA_APP.m_action.tags</code>. 标记 2014-01-23
     */
    public final TableField<MActionRecord, String> TAGS = createField("tags", org.jooq.impl.SQLDataType.VARCHAR(500), this, "标记 2014-01-23");

    /**
     * The column <code>SMETA_APP.m_action.weight</code>. 权重 2014-05-12
     */
    public final TableField<MActionRecord, Integer> WEIGHT = createField("weight", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "权重 2014-05-12");

    /**
     * The column <code>SMETA_APP.m_action.snapshot</code>. 动作快照
     */
    public final TableField<MActionRecord, String> SNAPSHOT = createField("snapshot", org.jooq.impl.SQLDataType.CLOB, this, "动作快照");

    /**
     * The column <code>SMETA_APP.m_action.contacter</code>. 动作负责人
     */
    public final TableField<MActionRecord, String> CONTACTER = createField("contacter", org.jooq.impl.SQLDataType.VARCHAR(32), this, "动作负责人");

    /**
     * Create a <code>SMETA_APP.m_action</code> table reference
     */
    public MAction() {
        this(DSL.name("m_action"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.m_action</code> table reference
     */
    public MAction(String alias) {
        this(DSL.name(alias), M_ACTION);
    }

    /**
     * Create an aliased <code>SMETA_APP.m_action</code> table reference
     */
    public MAction(Name alias) {
        this(alias, M_ACTION);
    }

    private MAction(Name alias, Table<MActionRecord> aliased) {
        this(alias, aliased, null);
    }

    private MAction(Name alias, Table<MActionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "处置动作表");
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
        return Arrays.<Index>asList(Indexes.M_ACTION_IDX_COMMENT, Indexes.M_ACTION_PRIMARY, Indexes.M_ACTION_UK_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MActionRecord, Long> getIdentity() {
        return Keys.IDENTITY_M_ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MActionRecord> getPrimaryKey() {
        return Keys.KEY_M_ACTION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MActionRecord>> getKeys() {
        return Arrays.<UniqueKey<MActionRecord>>asList(Keys.KEY_M_ACTION_PRIMARY, Keys.KEY_M_ACTION_UK_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MAction as(String alias) {
        return new MAction(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MAction as(Name alias) {
        return new MAction(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MAction rename(String name) {
        return new MAction(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MAction rename(Name name) {
        return new MAction(name, null);
    }
}