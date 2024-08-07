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

import test.generated.tables.IndicatorSourceTable;


/**
 * 指标源表关系
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IndicatorSourceTableRecord extends UpdatableRecordImpl<IndicatorSourceTableRecord> implements Record9<ULong, Timestamp, Timestamp, ULong, String, String, String, String, Integer> {

    private static final long serialVersionUID = 434967829;

    /**
     * Setter for <code>SMETA_APP.indicator_source_table.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.indicator_source_table.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.indicator_source_table.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.indicator_source_table.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.indicator_source_table.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.indicator_source_table.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.indicator_source_table.source_table_id</code>. 源表主键
     */
    public void setSourceTableId(ULong value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.indicator_source_table.source_table_id</code>. 源表主键
     */
    public ULong getSourceTableId() {
        return (ULong) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.indicator_source_table.indicator_code</code>. 指标Code
     */
    public void setIndicatorCode(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.indicator_source_table.indicator_code</code>. 指标Code
     */
    public String getIndicatorCode() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.indicator_source_table.source_table_field</code>. 源表字段
     */
    public void setSourceTableField(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.indicator_source_table.source_table_field</code>. 源表字段
     */
    public String getSourceTableField() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.indicator_source_table.field_type</code>. 字段类型
     */
    public void setFieldType(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.indicator_source_table.field_type</code>. 字段类型
     */
    public String getFieldType() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.indicator_source_table.source_table_field_script</code>. 源表字段处理脚本
     */
    public void setSourceTableFieldScript(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.indicator_source_table.source_table_field_script</code>. 源表字段处理脚本
     */
    public String getSourceTableFieldScript() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.indicator_source_table.status</code>. 状态 0无效 1有效
     */
    public void setStatus(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.indicator_source_table.status</code>. 状态 0无效 1有效
     */
    public Integer getStatus() {
        return (Integer) get(8);
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
    public Row9<ULong, Timestamp, Timestamp, ULong, String, String, String, String, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<ULong, Timestamp, Timestamp, ULong, String, String, String, String, Integer> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return IndicatorSourceTable.INDICATOR_SOURCE_TABLE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return IndicatorSourceTable.INDICATOR_SOURCE_TABLE.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return IndicatorSourceTable.INDICATOR_SOURCE_TABLE.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field4() {
        return IndicatorSourceTable.INDICATOR_SOURCE_TABLE.SOURCE_TABLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return IndicatorSourceTable.INDICATOR_SOURCE_TABLE.INDICATOR_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return IndicatorSourceTable.INDICATOR_SOURCE_TABLE.SOURCE_TABLE_FIELD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return IndicatorSourceTable.INDICATOR_SOURCE_TABLE.FIELD_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return IndicatorSourceTable.INDICATOR_SOURCE_TABLE.SOURCE_TABLE_FIELD_SCRIPT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return IndicatorSourceTable.INDICATOR_SOURCE_TABLE.STATUS;
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
        return getSourceTableId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getIndicatorCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getSourceTableField();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getFieldType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getSourceTableFieldScript();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getStatus();
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
        return getSourceTableId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getIndicatorCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getSourceTableField();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getFieldType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getSourceTableFieldScript();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTableRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTableRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTableRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTableRecord value4(ULong value) {
        setSourceTableId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTableRecord value5(String value) {
        setIndicatorCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTableRecord value6(String value) {
        setSourceTableField(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTableRecord value7(String value) {
        setFieldType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTableRecord value8(String value) {
        setSourceTableFieldScript(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTableRecord value9(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorSourceTableRecord values(ULong value1, Timestamp value2, Timestamp value3, ULong value4, String value5, String value6, String value7, String value8, Integer value9) {
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
     * Create a detached IndicatorSourceTableRecord
     */
    public IndicatorSourceTableRecord() {
        super(IndicatorSourceTable.INDICATOR_SOURCE_TABLE);
    }

    /**
     * Create a detached, initialised IndicatorSourceTableRecord
     */
    public IndicatorSourceTableRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, ULong sourceTableId, String indicatorCode, String sourceTableField, String fieldType, String sourceTableFieldScript, Integer status) {
        super(IndicatorSourceTable.INDICATOR_SOURCE_TABLE);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, sourceTableId);
        set(4, indicatorCode);
        set(5, sourceTableField);
        set(6, fieldType);
        set(7, sourceTableFieldScript);
        set(8, status);
    }
}
