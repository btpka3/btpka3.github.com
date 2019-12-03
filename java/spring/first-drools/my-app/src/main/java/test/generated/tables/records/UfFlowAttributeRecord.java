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

import test.generated.tables.UfFlowAttribute;


/**
 * 租户流程属性表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfFlowAttributeRecord extends UpdatableRecordImpl<UfFlowAttributeRecord> implements Record7<ULong, Timestamp, Timestamp, ULong, String, String, String> {

    private static final long serialVersionUID = -731400852;

    /**
     * Setter for <code>SMETA_APP.uf_flow_attribute.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_flow_attribute.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.uf_flow_attribute.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_flow_attribute.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.uf_flow_attribute.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_flow_attribute.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.uf_flow_attribute.last_operator</code>. 最近修改人
     */
    public void setLastOperator(ULong value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_flow_attribute.last_operator</code>. 最近修改人
     */
    public ULong getLastOperator() {
        return (ULong) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.uf_flow_attribute.tenant_code</code>. 租户code
     */
    public void setTenantCode(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_flow_attribute.tenant_code</code>. 租户code
     */
    public String getTenantCode() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.uf_flow_attribute.flow_switch</code>. 审批开关（on打开 off关闭）
     */
    public void setFlowSwitch(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_flow_attribute.flow_switch</code>. 审批开关（on打开 off关闭）
     */
    public String getFlowSwitch() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.uf_flow_attribute.flow_code</code>. 工作流code
     */
    public void setFlowCode(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_flow_attribute.flow_code</code>. 工作流code
     */
    public String getFlowCode() {
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
    public Row7<ULong, Timestamp, Timestamp, ULong, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<ULong, Timestamp, Timestamp, ULong, String, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return UfFlowAttribute.UF_FLOW_ATTRIBUTE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return UfFlowAttribute.UF_FLOW_ATTRIBUTE.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return UfFlowAttribute.UF_FLOW_ATTRIBUTE.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field4() {
        return UfFlowAttribute.UF_FLOW_ATTRIBUTE.LAST_OPERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return UfFlowAttribute.UF_FLOW_ATTRIBUTE.TENANT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return UfFlowAttribute.UF_FLOW_ATTRIBUTE.FLOW_SWITCH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return UfFlowAttribute.UF_FLOW_ATTRIBUTE.FLOW_CODE;
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
        return getLastOperator();
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
    public String component6() {
        return getFlowSwitch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getFlowCode();
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
        return getLastOperator();
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
    public String value6() {
        return getFlowSwitch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getFlowCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfFlowAttributeRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfFlowAttributeRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfFlowAttributeRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfFlowAttributeRecord value4(ULong value) {
        setLastOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfFlowAttributeRecord value5(String value) {
        setTenantCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfFlowAttributeRecord value6(String value) {
        setFlowSwitch(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfFlowAttributeRecord value7(String value) {
        setFlowCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfFlowAttributeRecord values(ULong value1, Timestamp value2, Timestamp value3, ULong value4, String value5, String value6, String value7) {
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
     * Create a detached UfFlowAttributeRecord
     */
    public UfFlowAttributeRecord() {
        super(UfFlowAttribute.UF_FLOW_ATTRIBUTE);
    }

    /**
     * Create a detached, initialised UfFlowAttributeRecord
     */
    public UfFlowAttributeRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, ULong lastOperator, String tenantCode, String flowSwitch, String flowCode) {
        super(UfFlowAttribute.UF_FLOW_ATTRIBUTE);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, lastOperator);
        set(4, tenantCode);
        set(5, flowSwitch);
        set(6, flowCode);
    }
}