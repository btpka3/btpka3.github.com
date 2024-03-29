/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import test.generated.tables.RaySqlJob;


/**
 * 一大段sql中一小段片段执行的日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RaySqlJobRecord extends UpdatableRecordImpl<RaySqlJobRecord> implements Record10<ULong, Timestamp, Timestamp, String, String, ULong, String, String, String, String> {

    private static final long serialVersionUID = 2127045981;

    /**
     * Setter for <code>SMETA_APP.ray_sql_job.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_sql_job.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.ray_sql_job.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_sql_job.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.ray_sql_job.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_sql_job.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.ray_sql_job.biz_id</code>. 创建者工号
     */
    public void setBizId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_sql_job.biz_id</code>. 创建者工号
     */
    public String getBizId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.ray_sql_job.tenant_code</code>. 租户id
     */
    public void setTenantCode(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_sql_job.tenant_code</code>. 租户id
     */
    public String getTenantCode() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.ray_sql_job.sql_log_id</code>. 外键
     */
    public void setSqlLogId(ULong value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_sql_job.sql_log_id</code>. 外键
     */
    public ULong getSqlLogId() {
        return (ULong) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.ray_sql_job.sql_text</code>. sql文本
     */
    public void setSqlText(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_sql_job.sql_text</code>. sql文本
     */
    public String getSqlText() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.ray_sql_job.status</code>. 状态
     */
    public void setStatus(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_sql_job.status</code>. 状态
     */
    public String getStatus() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.ray_sql_job.extra_info</code>. 额外信息
     */
    public void setExtraInfo(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_sql_job.extra_info</code>. 额外信息
     */
    public String getExtraInfo() {
        return (String) get(8);
    }

    /**
     * Setter for <code>SMETA_APP.ray_sql_job.nick_name</code>. 花名
     */
    public void setNickName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_sql_job.nick_name</code>. 花名
     */
    public String getNickName() {
        return (String) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<ULong, Timestamp, Timestamp, String, String, ULong, String, String, String, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<ULong, Timestamp, Timestamp, String, String, ULong, String, String, String, String> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return RaySqlJob.RAY_SQL_JOB.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return RaySqlJob.RAY_SQL_JOB.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return RaySqlJob.RAY_SQL_JOB.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return RaySqlJob.RAY_SQL_JOB.BIZ_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return RaySqlJob.RAY_SQL_JOB.TENANT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field6() {
        return RaySqlJob.RAY_SQL_JOB.SQL_LOG_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return RaySqlJob.RAY_SQL_JOB.SQL_TEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return RaySqlJob.RAY_SQL_JOB.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return RaySqlJob.RAY_SQL_JOB.EXTRA_INFO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return RaySqlJob.RAY_SQL_JOB.NICK_NAME;
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
    public String component4() {
        return getBizId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component6() {
        return getSqlLogId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getSqlText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getExtraInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getNickName();
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
    public String value4() {
        return getBizId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value6() {
        return getSqlLogId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getSqlText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getExtraInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getNickName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord value4(String value) {
        setBizId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord value5(String value) {
        setTenantCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord value6(ULong value) {
        setSqlLogId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord value7(String value) {
        setSqlText(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord value8(String value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord value9(String value) {
        setExtraInfo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord value10(String value) {
        setNickName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RaySqlJobRecord values(ULong value1, Timestamp value2, Timestamp value3, String value4, String value5, ULong value6, String value7, String value8, String value9, String value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RaySqlJobRecord
     */
    public RaySqlJobRecord() {
        super(RaySqlJob.RAY_SQL_JOB);
    }

    /**
     * Create a detached, initialised RaySqlJobRecord
     */
    public RaySqlJobRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String bizId, String tenantCode, ULong sqlLogId, String sqlText, String status, String extraInfo, String nickName) {
        super(RaySqlJob.RAY_SQL_JOB);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, bizId);
        set(4, tenantCode);
        set(5, sqlLogId);
        set(6, sqlText);
        set(7, status);
        set(8, extraInfo);
        set(9, nickName);
    }
}
