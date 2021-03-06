/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import test.generated.tables.EventOdpsStat;


/**
 * 事件上云状态统
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventOdpsStatRecord extends UpdatableRecordImpl<EventOdpsStatRecord> implements Record7<ULong, Timestamp, Timestamp, String, Integer, Integer, String> {

    private static final long serialVersionUID = -853713359;

    /**
     * Setter for <code>SMETA_APP.event_odps_stat.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.event_odps_stat.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.event_odps_stat.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.event_odps_stat.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.event_odps_stat.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.event_odps_stat.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.event_odps_stat.event_code</code>. 事件编码
     */
    public void setEventCode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.event_odps_stat.event_code</code>. 事件编码
     */
    public String getEventCode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.event_odps_stat.status</code>. 上云状态，0：未上云，1：已上云
     */
    public void setStatus(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.event_odps_stat.status</code>. 上云状态，0：未上云，1：已上云
     */
    public Integer getStatus() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.event_odps_stat.operate_status</code>. 操作状态，0：未操作，1：执行中，2：操作成功，3：操作失败
     */
    public void setOperateStatus(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.event_odps_stat.operate_status</code>. 操作状态，0：未操作，1：执行中，2：操作成功，3：操作失败
     */
    public Integer getOperateStatus() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.event_odps_stat.error_log</code>. 错误日志
     */
    public void setErrorLog(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.event_odps_stat.error_log</code>. 错误日志
     */
    public String getErrorLog() {
        return (String) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<ULong, Timestamp, Timestamp, String, Integer, Integer, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<ULong, Timestamp, Timestamp, String, Integer, Integer, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return EventOdpsStat.EVENT_ODPS_STAT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return EventOdpsStat.EVENT_ODPS_STAT.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return EventOdpsStat.EVENT_ODPS_STAT.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return EventOdpsStat.EVENT_ODPS_STAT.EVENT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return EventOdpsStat.EVENT_ODPS_STAT.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return EventOdpsStat.EVENT_ODPS_STAT.OPERATE_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return EventOdpsStat.EVENT_ODPS_STAT.ERROR_LOG;
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
        return getEventCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getOperateStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getErrorLog();
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
        return getEventCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getOperateStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getErrorLog();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOdpsStatRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOdpsStatRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOdpsStatRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOdpsStatRecord value4(String value) {
        setEventCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOdpsStatRecord value5(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOdpsStatRecord value6(Integer value) {
        setOperateStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOdpsStatRecord value7(String value) {
        setErrorLog(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOdpsStatRecord values(ULong value1, Timestamp value2, Timestamp value3, String value4, Integer value5, Integer value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EventOdpsStatRecord
     */
    public EventOdpsStatRecord() {
        super(EventOdpsStat.EVENT_ODPS_STAT);
    }

    /**
     * Create a detached, initialised EventOdpsStatRecord
     */
    public EventOdpsStatRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String eventCode, Integer status, Integer operateStatus, String errorLog) {
        super(EventOdpsStat.EVENT_ODPS_STAT);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, eventCode);
        set(4, status);
        set(5, operateStatus);
        set(6, errorLog);
    }
}
