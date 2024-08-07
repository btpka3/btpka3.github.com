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
import test.generated.tables.records.UfUserRoleRefRecord;


/**
 * 用户角色关联表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfUserRoleRef extends TableImpl<UfUserRoleRefRecord> {

    private static final long serialVersionUID = -755997485;

    /**
     * The reference instance of <code>SMETA_APP.uf_user_role_ref</code>
     */
    public static final UfUserRoleRef UF_USER_ROLE_REF = new UfUserRoleRef();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UfUserRoleRefRecord> getRecordType() {
        return UfUserRoleRefRecord.class;
    }

    /**
     * The column <code>SMETA_APP.uf_user_role_ref.id</code>. 主键
     */
    public final TableField<UfUserRoleRefRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "主键");

    /**
     * The column <code>SMETA_APP.uf_user_role_ref.gmt_create</code>. 创建时间
     */
    public final TableField<UfUserRoleRefRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>SMETA_APP.uf_user_role_ref.gmt_modified</code>. 修改时间
     */
    public final TableField<UfUserRoleRefRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "修改时间");

    /**
     * The column <code>SMETA_APP.uf_user_role_ref.role_id</code>. 角色id
     */
    public final TableField<UfUserRoleRefRecord, ULong> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "角色id");

    /**
     * The column <code>SMETA_APP.uf_user_role_ref.user_id</code>. 用户id
     */
    public final TableField<UfUserRoleRefRecord, ULong> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "用户id");

    /**
     * The column <code>SMETA_APP.uf_user_role_ref.expired_time</code>. 失效日期
     */
    public final TableField<UfUserRoleRefRecord, Timestamp> EXPIRED_TIME = createField("expired_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "失效日期");

    /**
     * The column <code>SMETA_APP.uf_user_role_ref.last_operator</code>. 最后修改者
     */
    public final TableField<UfUserRoleRefRecord, ULong> LAST_OPERATOR = createField("last_operator", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "最后修改者");

    /**
     * The column <code>SMETA_APP.uf_user_role_ref.status</code>. 状态（0正常 1冻结）
     */
    public final TableField<UfUserRoleRefRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "状态（0正常 1冻结）");

    /**
     * The column <code>SMETA_APP.uf_user_role_ref.tenant_code</code>. 租户code
     */
    public final TableField<UfUserRoleRefRecord, String> TENANT_CODE = createField("tenant_code", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "租户code");

    /**
     * Create a <code>SMETA_APP.uf_user_role_ref</code> table reference
     */
    public UfUserRoleRef() {
        this(DSL.name("uf_user_role_ref"), null);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_user_role_ref</code> table reference
     */
    public UfUserRoleRef(String alias) {
        this(DSL.name(alias), UF_USER_ROLE_REF);
    }

    /**
     * Create an aliased <code>SMETA_APP.uf_user_role_ref</code> table reference
     */
    public UfUserRoleRef(Name alias) {
        this(alias, UF_USER_ROLE_REF);
    }

    private UfUserRoleRef(Name alias, Table<UfUserRoleRefRecord> aliased) {
        this(alias, aliased, null);
    }

    private UfUserRoleRef(Name alias, Table<UfUserRoleRefRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "用户角色关联表");
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
        return Arrays.<Index>asList(Indexes.UF_USER_ROLE_REF_PRIMARY, Indexes.UF_USER_ROLE_REF_UK_ROLE_USER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UfUserRoleRefRecord, ULong> getIdentity() {
        return Keys.IDENTITY_UF_USER_ROLE_REF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UfUserRoleRefRecord> getPrimaryKey() {
        return Keys.KEY_UF_USER_ROLE_REF_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UfUserRoleRefRecord>> getKeys() {
        return Arrays.<UniqueKey<UfUserRoleRefRecord>>asList(Keys.KEY_UF_USER_ROLE_REF_PRIMARY, Keys.KEY_UF_USER_ROLE_REF_UK_ROLE_USER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRef as(String alias) {
        return new UfUserRoleRef(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRef as(Name alias) {
        return new UfUserRoleRef(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UfUserRoleRef rename(String name) {
        return new UfUserRoleRef(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UfUserRoleRef rename(Name name) {
        return new UfUserRoleRef(name, null);
    }
}
