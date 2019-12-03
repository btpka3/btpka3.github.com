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
import test.generated.tables.records.UfCanvasRecord;


/**
 * 策略画布配置存储表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfCanvas extends TableImpl<UfCanvasRecord> {

    private static final long serialVersionUID = 2096852800;

    /**
     * The reference instance of <code>SMETA_APP.uf_canvas</code>
     */
    public static final UfCanvas UF_CANVAS = new UfCanvas();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UfCanvasRecord> getRecordType() {
        return UfCanvasRecord.class;
    }

    /**
     * The column <code>SMETA_APP.uf_canvas.id</code>. 主键
     */
    public final TableField<UfCanvasRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.uf_canvas.gmt_create</code>. 创建时间
     */
    public final TableField<UfCanvasRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.uf_canvas.gmt_modified</code>. 修改时间
     */
    public final TableField<UfCanvasRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.uf_canvas.type</code>. 策略画布类型
     */
    public final TableField<UfCanvasRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(100), this, "策略画布类型");

    /**
     * The column <code>SMETA_APP.uf_canvas.name</code>. 策略画布名称
     */
    public final TableField<UfCanvasRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "策略画布名称");

    /**
     * The column <code>SMETA_APP.uf_canvas.risk_type_id</code>. 风险类型id
     */
    public final TableField<UfCanvasRecord, Integer> RISK_TYPE_ID = createField("risk_type_id", org.jooq.impl.SQLDataType.INTEGER, this, "风险类型id");

    /**
     * The column <code>SMETA_APP.uf_canvas.contacts</code>. 联系人
     */
    public final TableField<UfCanvasRecord, String> CONTACTS = createField("contacts", org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "联系人");

    /**
     * The column <code>SMETA_APP.uf_canvas.last_operator</code>. 最后修改的用户
     */
    public final TableField<UfCanvasRecord, ULong> LAST_OPERATOR = createField("last_operator", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "最后修改的用户");

    /**
     * The column <code>SMETA_APP.uf_canvas.status</code>. 画布状态，1表示线上，0表示线下
     */
    public final TableField<UfCanvasRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "画布状态，1表示线上，0表示线下");

    /**
     * The column <code>SMETA_APP.uf_canvas.version</code>. 当前版本
     */
    public final TableField<UfCanvasRecord, Integer> VERSION = createField("version", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "当前版本");

    /**
     * The column <code>SMETA_APP.uf_canvas.strategy_layer</code>. 策略画布逻辑层详细配置信息，JSON
     */
    public final TableField<UfCanvasRecord, String> STRATEGY_LAYER = createField("strategy_layer", org.jooq.impl.SQLDataType.CLOB, this, "策略画布逻辑层详细配置信息，JSON");

    /**
     * The column <code>SMETA_APP.uf_canvas.tenant_code</code>. 租户code
     */
    public final TableField<UfCanvasRecord, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "租户code");

    /**
     * The column <code>SMETA_APP.uf_canvas.event_codes</code>. 关联的事件code集合
     */
    public final TableField<UfCanvasRecord, String> EVENT_CODES = createField("event_codes", org.jooq.impl.SQLDataType.VARCHAR(200), this, "关联的事件code集合");

    /**
     * The column <code>SMETA_APP.uf_canvas.rulesets</code>. 关联的规则集合列表
     */
    public final TableField<UfCanvasRecord, String> RULESETS = createField("rulesets", org.jooq.impl.SQLDataType.VARCHAR(200), this, "关联的规则集合列表");

    /**
     * Create a <code>SMETA_APP.uf_canvas</code> table reference
     */
    public UfCanvas() {
        this(DSL.name("uf_canvas"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_canvas</code> table reference
     */
    public UfCanvas(String alias) {
        this(DSL.name(alias), UF_CANVAS);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_canvas</code> table reference
     */
    public UfCanvas(Name alias) {
        this(alias, UF_CANVAS);
    }

    private UfCanvas(Name alias, Table<UfCanvasRecord> aliased) {
        this(alias, aliased, null);
    }

    private UfCanvas(Name alias, Table<UfCanvasRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "策略画布配置存储表");
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
        return Arrays.<Index>asList(Indexes.UF_CANVAS_IDX_STATUS_RULESETID, Indexes.UF_CANVAS_PRIMARY, Indexes.UF_CANVAS_UK_NAME_TENANTCODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UfCanvasRecord, ULong> getIdentity() {
        return Keys.IDENTITY_UF_CANVAS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UfCanvasRecord> getPrimaryKey() {
        return Keys.KEY_UF_CANVAS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UfCanvasRecord>> getKeys() {
        return Arrays.<UniqueKey<UfCanvasRecord>>asList(Keys.KEY_UF_CANVAS_PRIMARY, Keys.KEY_UF_CANVAS_UK_NAME_TENANTCODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfCanvas as(String alias) {
        return new UfCanvas(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfCanvas as(Name alias) {
        return new UfCanvas(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UfCanvas rename(String name) {
        return new UfCanvas(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UfCanvas rename(Name name) {
        return new UfCanvas(name, null);
    }
}