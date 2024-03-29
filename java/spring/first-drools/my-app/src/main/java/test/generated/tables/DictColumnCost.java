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
import org.jooq.types.UInteger;
import org.jooq.types.ULong;

import test.generated.Indexes;
import test.generated.Keys;
import test.generated.SmetaApp;
import test.generated.tables.records.DictColumnCostRecord;


/**
 * 数据字典属性脚本耗时
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictColumnCost extends TableImpl<DictColumnCostRecord> {

    private static final long serialVersionUID = 2132539171;

    /**
     * The reference instance of <code>SMETA_APP.dict_column_cost</code>
     */
    public static final DictColumnCost DICT_COLUMN_COST = new DictColumnCost();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictColumnCostRecord> getRecordType() {
        return DictColumnCostRecord.class;
    }

    /**
     * The column <code>SMETA_APP.dict_column_cost.id</code>. 主键
     */
    public final TableField<DictColumnCostRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.dict_column_cost.gmt_create</code>. 创建时间
     */
    public final TableField<DictColumnCostRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.dict_column_cost.gmt_modified</code>. 修改时间
     */
    public final TableField<DictColumnCostRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.dict_column_cost.event_code</code>. 事件code
     */
    public final TableField<DictColumnCostRecord, String> EVENT_CODE = createField("event_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "事件code");

    /**
     * The column <code>SMETA_APP.dict_column_cost.event_column_code</code>. event_column_code
     */
    public final TableField<DictColumnCostRecord, String> EVENT_COLUMN_CODE = createField("event_column_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "event_column_code");

    /**
     * The column <code>SMETA_APP.dict_column_cost.cost</code>. 脚本耗时
     */
    public final TableField<DictColumnCostRecord, UInteger> COST = createField("cost", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "脚本耗时");

    /**
     * The column <code>SMETA_APP.dict_column_cost.call_rate</code>. 数据字典访问频率(乘100取整)
     */
    public final TableField<DictColumnCostRecord, Integer> CALL_RATE = createField("call_rate", org.jooq.impl.SQLDataType.INTEGER, this, "数据字典访问频率(乘100取整)");

    /**
     * Create a <code>SMETA_APP.dict_column_cost</code> table reference
     */
    public DictColumnCost() {
        this(DSL.name("dict_column_cost"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.dict_column_cost</code> table reference
     */
    public DictColumnCost(String alias) {
        this(DSL.name(alias), DICT_COLUMN_COST);
    }

    /**
     * Create an aliased <code>SMETA_APP.dict_column_cost</code> table reference
     */
    public DictColumnCost(Name alias) {
        this(alias, DICT_COLUMN_COST);
    }

    private DictColumnCost(Name alias, Table<DictColumnCostRecord> aliased) {
        this(alias, aliased, null);
    }

    private DictColumnCost(Name alias, Table<DictColumnCostRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "数据字典属性脚本耗时");
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
        return Arrays.<Index>asList(Indexes.DICT_COLUMN_COST_IDX_COLUMN_CODE, Indexes.DICT_COLUMN_COST_IDX_EVENT_CODE, Indexes.DICT_COLUMN_COST_IDX_GMT_MODIFIED, Indexes.DICT_COLUMN_COST_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DictColumnCostRecord, ULong> getIdentity() {
        return Keys.IDENTITY_DICT_COLUMN_COST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DictColumnCostRecord> getPrimaryKey() {
        return Keys.KEY_DICT_COLUMN_COST_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictColumnCostRecord>> getKeys() {
        return Arrays.<UniqueKey<DictColumnCostRecord>>asList(Keys.KEY_DICT_COLUMN_COST_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnCost as(String alias) {
        return new DictColumnCost(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnCost as(Name alias) {
        return new DictColumnCost(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictColumnCost rename(String name) {
        return new DictColumnCost(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DictColumnCost rename(Name name) {
        return new DictColumnCost(name, null);
    }
}
