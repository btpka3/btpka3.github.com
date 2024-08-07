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

import test.generated.tables.IndiCrossWing;


/**
 * 指标wing报表关系表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IndiCrossWingRecord extends UpdatableRecordImpl<IndiCrossWingRecord> implements Record9<ULong, Timestamp, Timestamp, String, String, String, String, String, String> {

    private static final long serialVersionUID = -138925803;

    /**
     * Setter for <code>SMETA_APP.indi_cross_wing.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_cross_wing.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.indi_cross_wing.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_cross_wing.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.indi_cross_wing.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_cross_wing.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.indi_cross_wing.indi_code</code>. 指标code
     */
    public void setIndiCode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_cross_wing.indi_code</code>. 指标code
     */
    public String getIndiCode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.indi_cross_wing.indi_name</code>. 指标名称
     */
    public void setIndiName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_cross_wing.indi_name</code>. 指标名称
     */
    public String getIndiName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.indi_cross_wing.wing_indi_code</code>. wing的对应指标code
     */
    public void setWingIndiCode(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_cross_wing.wing_indi_code</code>. wing的对应指标code
     */
    public String getWingIndiCode() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.indi_cross_wing.wing_indi_name</code>. wing的指标name
     */
    public void setWingIndiName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_cross_wing.wing_indi_name</code>. wing的指标name
     */
    public String getWingIndiName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.indi_cross_wing.wing_report_id</code>. 	
wing报表编码
     */
    public void setWingReportId(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_cross_wing.wing_report_id</code>. 	
wing报表编码
     */
    public String getWingReportId() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.indi_cross_wing.wing_report_name</code>. wing报表名称	
     */
    public void setWingReportName(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.indi_cross_wing.wing_report_name</code>. wing报表名称	
     */
    public String getWingReportName() {
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
        return IndiCrossWing.INDI_CROSS_WING.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return IndiCrossWing.INDI_CROSS_WING.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return IndiCrossWing.INDI_CROSS_WING.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return IndiCrossWing.INDI_CROSS_WING.INDI_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return IndiCrossWing.INDI_CROSS_WING.INDI_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return IndiCrossWing.INDI_CROSS_WING.WING_INDI_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return IndiCrossWing.INDI_CROSS_WING.WING_INDI_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return IndiCrossWing.INDI_CROSS_WING.WING_REPORT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return IndiCrossWing.INDI_CROSS_WING.WING_REPORT_NAME;
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
        return getIndiCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getIndiName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getWingIndiCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getWingIndiName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getWingReportId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getWingReportName();
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
        return getIndiCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getIndiName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getWingIndiCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getWingIndiName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getWingReportId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getWingReportName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiCrossWingRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiCrossWingRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiCrossWingRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiCrossWingRecord value4(String value) {
        setIndiCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiCrossWingRecord value5(String value) {
        setIndiName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiCrossWingRecord value6(String value) {
        setWingIndiCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiCrossWingRecord value7(String value) {
        setWingIndiName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiCrossWingRecord value8(String value) {
        setWingReportId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiCrossWingRecord value9(String value) {
        setWingReportName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndiCrossWingRecord values(ULong value1, Timestamp value2, Timestamp value3, String value4, String value5, String value6, String value7, String value8, String value9) {
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
     * Create a detached IndiCrossWingRecord
     */
    public IndiCrossWingRecord() {
        super(IndiCrossWing.INDI_CROSS_WING);
    }

    /**
     * Create a detached, initialised IndiCrossWingRecord
     */
    public IndiCrossWingRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String indiCode, String indiName, String wingIndiCode, String wingIndiName, String wingReportId, String wingReportName) {
        super(IndiCrossWing.INDI_CROSS_WING);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, indiCode);
        set(4, indiName);
        set(5, wingIndiCode);
        set(6, wingIndiName);
        set(7, wingReportId);
        set(8, wingReportName);
    }
}
