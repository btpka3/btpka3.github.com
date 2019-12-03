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
import test.generated.tables.records.IndiAdlEunomiaOdpsTablesDeltaFdtRecord;


/**
 * 自动计算离线表的信息变化，由ODPS同步
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IndiAdlEunomiaOdpsTablesDeltaFdt extends TableImpl<IndiAdlEunomiaOdpsTablesDeltaFdtRecord> {

    private static final long serialVersionUID = -122853144;

    /**
     * The reference instance of <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt</code>
     */
    public static final IndiAdlEunomiaOdpsTablesDeltaFdt INDI_ADL_EUNOMIA_ODPS_TABLES_DELTA_FDT = new IndiAdlEunomiaOdpsTablesDeltaFdt();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IndiAdlEunomiaOdpsTablesDeltaFdtRecord> getRecordType() {
        return IndiAdlEunomiaOdpsTablesDeltaFdtRecord.class;
    }

    /**
     * The column <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt.id</code>. 主键
     */
    public final TableField<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt.gmt_create</code>. 创建时间
     */
    public final TableField<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt.gmt_modified</code>. 修改时间
     */
    public final TableField<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt.table_name</code>. 表名：项目.表名
     */
    public final TableField<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, String> TABLE_NAME = createField("table_name", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "表名：项目.表名");

    /**
     * The column <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt.dev_owner</code>. 开发人员6位工号
     */
    public final TableField<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, String> DEV_OWNER = createField("dev_owner", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "开发人员6位工号");

    /**
     * The column <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt.dev_owner_name</code>. 开发人员名称
     */
    public final TableField<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, String> DEV_OWNER_NAME = createField("dev_owner_name", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "开发人员名称");

    /**
     * The column <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt.partition_col</code>. 分区字段
     */
    public final TableField<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, String> PARTITION_COL = createField("partition_col", org.jooq.impl.SQLDataType.VARCHAR(32), this, "分区字段");

    /**
     * The column <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt.life_cycle</code>. 生命周期天数
     */
    public final TableField<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, Long> LIFE_CYCLE = createField("life_cycle", org.jooq.impl.SQLDataType.BIGINT, this, "生命周期天数");

    /**
     * The column <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt.granularity</code>. 调度粒度：1 分钟，2 小时，3 天，4 周，5 月
     */
    public final TableField<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, Long> GRANULARITY = createField("granularity", org.jooq.impl.SQLDataType.BIGINT, this, "调度粒度：1 分钟，2 小时，3 天，4 周，5 月");

    /**
     * The column <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt.delta_tag</code>. 表增量标签：新增表，新增字段，删除表，删除字段，更改表信息\nadd_table,add_column,drop_table,delete_column,update_table
     */
    public final TableField<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, String> DELTA_TAG = createField("delta_tag", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "表增量标签：新增表，新增字段，删除表，删除字段，更改表信息\\nadd_table,add_column,drop_table,delete_column,update_table");

    /**
     * Create a <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt</code> table reference
     */
    public IndiAdlEunomiaOdpsTablesDeltaFdt() {
        this(DSL.name("indi_adl_eunomia_odps_tables_delta_fdt"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt</code> table reference
     */
    public IndiAdlEunomiaOdpsTablesDeltaFdt(String alias) {
        this(DSL.name(alias), INDI_ADL_EUNOMIA_ODPS_TABLES_DELTA_FDT);
    }

    /**
     * Create an aliased <code>SMETA_APP.indi_adl_eunomia_odps_tables_delta_fdt</code> table reference
     */
    public IndiAdlEunomiaOdpsTablesDeltaFdt(Name alias) {
        this(alias, INDI_ADL_EUNOMIA_ODPS_TABLES_DELTA_FDT);
    }

    private IndiAdlEunomiaOdpsTablesDeltaFdt(Name alias, Table<IndiAdlEunomiaOdpsTablesDeltaFdtRecord> aliased) {
        this(alias, aliased, null);
    }

    private IndiAdlEunomiaOdpsTablesDeltaFdt(Name alias, Table<IndiAdlEunomiaOdpsTablesDeltaFdtRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "自动计算离线表的信息变化，由ODPS同步");
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
        return Arrays.<Index>asList(Indexes.INDI_ADL_EUNOMIA_ODPS_TABLES_DELTA_FDT_IDX_TABLE_NAME, Indexes.INDI_ADL_EUNOMIA_ODPS_TABLES_DELTA_FDT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<IndiAdlEunomiaOdpsTablesDeltaFdtRecord, ULong> getIdentity() {
        return Keys.IDENTITY_INDI_ADL_EUNOMIA_ODPS_TABLES_DELTA_FDT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<IndiAdlEunomiaOdpsTablesDeltaFdtRecord> getPrimaryKey() {
        return Keys.KEY_INDI_ADL_EUNOMIA_ODPS_TABLES_DELTA_FDT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<IndiAdlEunomiaOdpsTablesDeltaFdtRecord>> getKeys() {
        return Arrays.<UniqueKey<IndiAdlEunomiaOdpsTablesDeltaFdtRecord>>asList(Keys.KEY_INDI_ADL_EUNOMIA_ODPS_TABLES_DELTA_FDT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiAdlEunomiaOdpsTablesDeltaFdt as(String alias) {
        return new IndiAdlEunomiaOdpsTablesDeltaFdt(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiAdlEunomiaOdpsTablesDeltaFdt as(Name alias) {
        return new IndiAdlEunomiaOdpsTablesDeltaFdt(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public IndiAdlEunomiaOdpsTablesDeltaFdt rename(String name) {
        return new IndiAdlEunomiaOdpsTablesDeltaFdt(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public IndiAdlEunomiaOdpsTablesDeltaFdt rename(Name name) {
        return new IndiAdlEunomiaOdpsTablesDeltaFdt(name, null);
    }
}