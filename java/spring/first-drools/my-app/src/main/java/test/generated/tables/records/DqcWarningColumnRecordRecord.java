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

import test.generated.tables.DqcWarningColumnRecord;


/**
 * dqc监控字段报警记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DqcWarningColumnRecordRecord extends UpdatableRecordImpl<DqcWarningColumnRecordRecord> implements Record9<ULong, Timestamp, Timestamp, String, String, String, String, String, String> {

    private static final long serialVersionUID = -1481042648;

    /**
     * Setter for <code>SMETA_APP.dqc_warning_column_record.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_warning_column_record.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_warning_column_record.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_warning_column_record.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_warning_column_record.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_warning_column_record.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_warning_column_record.table_name</code>. 表名
     */
    public void setTableName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_warning_column_record.table_name</code>. 表名
     */
    public String getTableName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_warning_column_record.event_column_code</code>. 字段
     */
    public void setEventColumnCode(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_warning_column_record.event_column_code</code>. 字段
     */
    public String getEventColumnCode() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_warning_column_record.actual_expression</code>. 分区
     */
    public void setActualExpression(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_warning_column_record.actual_expression</code>. 分区
     */
    public String getActualExpression() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_warning_column_record.template_name</code>. 监控模板名
     */
    public void setTemplateName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_warning_column_record.template_name</code>. 监控模板名
     */
    public String getTemplateName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_warning_column_record.result</code>. 计算结果
     */
    public void setResult(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_warning_column_record.result</code>. 计算结果
     */
    public String getResult() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_warning_column_record.recommend_threshold</code>. 推荐阀值
     */
    public void setRecommendThreshold(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_warning_column_record.recommend_threshold</code>. 推荐阀值
     */
    public String getRecommendThreshold() {
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
    public Row9<ULong, Timestamp, Timestamp, String, String, String, String, String, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<ULong, Timestamp, Timestamp, String, String, String, String, String, String> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD.TABLE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD.EVENT_COLUMN_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD.ACTUAL_EXPRESSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD.TEMPLATE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD.RESULT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD.RECOMMEND_THRESHOLD;
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
        return getTableName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getEventColumnCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getActualExpression();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getTemplateName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getRecommendThreshold();
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
        return getTableName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getEventColumnCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getActualExpression();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getTemplateName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getRecommendThreshold();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcWarningColumnRecordRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcWarningColumnRecordRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcWarningColumnRecordRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcWarningColumnRecordRecord value4(String value) {
        setTableName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcWarningColumnRecordRecord value5(String value) {
        setEventColumnCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcWarningColumnRecordRecord value6(String value) {
        setActualExpression(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcWarningColumnRecordRecord value7(String value) {
        setTemplateName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcWarningColumnRecordRecord value8(String value) {
        setResult(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcWarningColumnRecordRecord value9(String value) {
        setRecommendThreshold(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcWarningColumnRecordRecord values(ULong value1, Timestamp value2, Timestamp value3, String value4, String value5, String value6, String value7, String value8, String value9) {
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
     * Create a detached DqcWarningColumnRecordRecord
     */
    public DqcWarningColumnRecordRecord() {
        super(DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD);
    }

    /**
     * Create a detached, initialised DqcWarningColumnRecordRecord
     */
    public DqcWarningColumnRecordRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String tableName, String eventColumnCode, String actualExpression, String templateName, String result, String recommendThreshold) {
        super(DqcWarningColumnRecord.DQC_WARNING_COLUMN_RECORD);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, tableName);
        set(4, eventColumnCode);
        set(5, actualExpression);
        set(6, templateName);
        set(7, result);
        set(8, recommendThreshold);
    }
}
