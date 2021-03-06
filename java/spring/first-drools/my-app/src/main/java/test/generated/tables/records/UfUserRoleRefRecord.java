/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import test.generated.tables.UfUserRoleRef;


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
public class UfUserRoleRefRecord extends UpdatableRecordImpl<UfUserRoleRefRecord> implements Record9<ULong, Timestamp, Timestamp, ULong, ULong, Timestamp, ULong, Integer, String> {

    private static final long serialVersionUID = -85124559;

    /**
     * Setter for <code>SMETA_APP.uf_user_role_ref.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_user_role_ref.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.uf_user_role_ref.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_user_role_ref.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.uf_user_role_ref.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_user_role_ref.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.uf_user_role_ref.role_id</code>. 角色id
     */
    public void setRoleId(ULong value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_user_role_ref.role_id</code>. 角色id
     */
    public ULong getRoleId() {
        return (ULong) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.uf_user_role_ref.user_id</code>. 用户id
     */
    public void setUserId(ULong value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_user_role_ref.user_id</code>. 用户id
     */
    public ULong getUserId() {
        return (ULong) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.uf_user_role_ref.expired_time</code>. 失效日期
     */
    public void setExpiredTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_user_role_ref.expired_time</code>. 失效日期
     */
    public Timestamp getExpiredTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.uf_user_role_ref.last_operator</code>. 最后修改者
     */
    public void setLastOperator(ULong value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_user_role_ref.last_operator</code>. 最后修改者
     */
    public ULong getLastOperator() {
        return (ULong) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.uf_user_role_ref.status</code>. 状态（0正常 1冻结）
     */
    public void setStatus(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_user_role_ref.status</code>. 状态（0正常 1冻结）
     */
    public Integer getStatus() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.uf_user_role_ref.tenant_code</code>. 租户code
     */
    public void setTenantCode(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_user_role_ref.tenant_code</code>. 租户code
     */
    public String getTenantCode() {
        return (String) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<ULong> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<ULong, Timestamp, Timestamp, ULong, ULong, Timestamp, ULong, Integer, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<ULong, Timestamp, Timestamp, ULong, ULong, Timestamp, ULong, Integer, String> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return UfUserRoleRef.UF_USER_ROLE_REF.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return UfUserRoleRef.UF_USER_ROLE_REF.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return UfUserRoleRef.UF_USER_ROLE_REF.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field4() {
        return UfUserRoleRef.UF_USER_ROLE_REF.ROLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field5() {
        return UfUserRoleRef.UF_USER_ROLE_REF.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return UfUserRoleRef.UF_USER_ROLE_REF.EXPIRED_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field7() {
        return UfUserRoleRef.UF_USER_ROLE_REF.LAST_OPERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return UfUserRoleRef.UF_USER_ROLE_REF.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return UfUserRoleRef.UF_USER_ROLE_REF.TENANT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component2() {
        return getGmtCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getGmtModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component4() {
        return getRoleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component5() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getExpiredTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component7() {
        return getLastOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value2() {
        return getGmtCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getGmtModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value4() {
        return getRoleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value5() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getExpiredTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value7() {
        return getLastOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRefRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRefRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRefRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRefRecord value4(ULong value) {
        setRoleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRefRecord value5(ULong value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRefRecord value6(Timestamp value) {
        setExpiredTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRefRecord value7(ULong value) {
        setLastOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRefRecord value8(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRefRecord value9(String value) {
        setTenantCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfUserRoleRefRecord values(ULong value1, Timestamp value2, Timestamp value3, ULong value4, ULong value5, Timestamp value6, ULong value7, Integer value8, String value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UfUserRoleRefRecord
     */
    public UfUserRoleRefRecord() {
        super(UfUserRoleRef.UF_USER_ROLE_REF);
    }

    /**
     * Create a detached, initialised UfUserRoleRefRecord
     */
    public UfUserRoleRefRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, ULong roleId, ULong userId, Timestamp expiredTime, ULong lastOperator, Integer status, String tenantCode) {
        super(UfUserRoleRef.UF_USER_ROLE_REF);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, roleId);
        set(4, userId);
        set(5, expiredTime);
        set(6, lastOperator);
        set(7, status);
        set(8, tenantCode);
    }
}
