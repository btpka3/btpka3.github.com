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

import test.generated.tables.IndiAlgorithmMeta;


/**
 * 指标计算信息的meta元信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IndiAlgorithmMetaRecord extends UpdatableRecordImpl<IndiAlgorithmMetaRecord> implements Record7<ULong, Timestamp, Timestamp, String, String, String, Integer> {

    private static final long serialVersionUID = -1640779635;

    /**
     * Setter for <code>SMETA_APP.indi_algorithm_meta.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_algorithm_meta.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.indi_algorithm_meta.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_algorithm_meta.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.indi_algorithm_meta.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_algorithm_meta.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.indi_algorithm_meta.meta_type</code>. meta类型
     */
    public void setMetaType(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_algorithm_meta.meta_type</code>. meta类型
     */
    public String getMetaType() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.indi_algorithm_meta.meta_value</code>. meta元信息值
     */
    public void setMetaValue(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_algorithm_meta.meta_value</code>. meta元信息值
     */
    public String getMetaValue() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.indi_algorithm_meta.indicator_code</code>. 指标code
     */
    public void setIndicatorCode(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_algorithm_meta.indicator_code</code>. 指标code
     */
    public String getIndicatorCode() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.indi_algorithm_meta.status</code>. 指标算法字段状态位：0无效 1有效
     */
    public void setStatus(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_algorithm_meta.status</code>. 指标算法字段状态位：0无效 1有效
     */
    public Integer getStatus() {
        return (Integer) get(6);
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
    public Row7<ULong, Timestamp, Timestamp, String, String, String, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<ULong, Timestamp, Timestamp, String, String, String, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return IndiAlgorithmMeta.INDI_ALGORITHM_META.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return IndiAlgorithmMeta.INDI_ALGORITHM_META.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return IndiAlgorithmMeta.INDI_ALGORITHM_META.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return IndiAlgorithmMeta.INDI_ALGORITHM_META.META_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return IndiAlgorithmMeta.INDI_ALGORITHM_META.META_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return IndiAlgorithmMeta.INDI_ALGORITHM_META.INDICATOR_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return IndiAlgorithmMeta.INDI_ALGORITHM_META.STATUS;
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
        return getMetaType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getMetaValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getIndicatorCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
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
    public String value4() {
        return getMetaType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getMetaValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getIndicatorCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiAlgorithmMetaRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiAlgorithmMetaRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiAlgorithmMetaRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiAlgorithmMetaRecord value4(String value) {
        setMetaType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiAlgorithmMetaRecord value5(String value) {
        setMetaValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiAlgorithmMetaRecord value6(String value) {
        setIndicatorCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiAlgorithmMetaRecord value7(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiAlgorithmMetaRecord values(ULong value1, Timestamp value2, Timestamp value3, String value4, String value5, String value6, Integer value7) {
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
     * Create a detached IndiAlgorithmMetaRecord
     */
    public IndiAlgorithmMetaRecord() {
        super(IndiAlgorithmMeta.INDI_ALGORITHM_META);
    }

    /**
     * Create a detached, initialised IndiAlgorithmMetaRecord
     */
    public IndiAlgorithmMetaRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String metaType, String metaValue, String indicatorCode, Integer status) {
        super(IndiAlgorithmMeta.INDI_ALGORITHM_META);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, metaType);
        set(4, metaValue);
        set(5, indicatorCode);
        set(6, status);
    }
}
