/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;

import test.generated.tables.UfTaskInstance;


/**
 * 任务实例表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfTaskInstanceRecord extends UpdatableRecordImpl<UfTaskInstanceRecord> implements Record12<ULong, Timestamp, Timestamp, String, String, UInteger, UInteger, String, String, ULong, String, UInteger> {

    private static final long serialVersionUID = -1164865601;

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.gmt_modified</code>. 完成时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.gmt_modified</code>. 完成时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.title</code>. 标题
     */
    public void setTitle(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.title</code>. 标题
     */
    public String getTitle() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.status</code>. 状态
     */
    public void setStatus(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.status</code>. 状态
     */
    public String getStatus() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.instance_id</code>. 实例id
     */
    public void setInstanceId(UInteger value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.instance_id</code>. 实例id
     */
    public UInteger getInstanceId() {
        return (UInteger) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.node_id</code>. 节点id
     */
    public void setNodeId(UInteger value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.node_id</code>. 节点id
     */
    public UInteger getNodeId() {
        return (UInteger) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.suggest</code>. 建议
     */
    public void setSuggest(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.suggest</code>. 建议
     */
    public String getSuggest() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.tenant_code</code>. 租户编码
     */
    public void setTenantCode(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.tenant_code</code>. 租户编码
     */
    public String getTenantCode() {
        return (String) get(8);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.operator_id</code>. 操作人id
     */
    public void setOperatorId(ULong value) {
        set(9, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.operator_id</code>. 操作人id
     */
    public ULong getOperatorId() {
        return (ULong) get(9);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.approvers</code>. 审批人列表，以逗号分隔
     */
    public void setApprovers(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.approvers</code>. 审批人列表，以逗号分隔
     */
    public String getApprovers() {
        return (String) get(10);
    }

    /**
     * Setter for <code>SMETA_APP.uf_task_instance.node_order</code>. 节点顺序
     */
    public void setNodeOrder(UInteger value) {
        set(11, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_task_instance.node_order</code>. 节点顺序
     */
    public UInteger getNodeOrder() {
        return (UInteger) get(11);
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
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<ULong, Timestamp, Timestamp, String, String, UInteger, UInteger, String, String, ULong, String, UInteger> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<ULong, Timestamp, Timestamp, String, String, UInteger, UInteger, String, String, ULong, String, UInteger> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return UfTaskInstance.UF_TASK_INSTANCE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return UfTaskInstance.UF_TASK_INSTANCE.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return UfTaskInstance.UF_TASK_INSTANCE.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return UfTaskInstance.UF_TASK_INSTANCE.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return UfTaskInstance.UF_TASK_INSTANCE.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field6() {
        return UfTaskInstance.UF_TASK_INSTANCE.INSTANCE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field7() {
        return UfTaskInstance.UF_TASK_INSTANCE.NODE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return UfTaskInstance.UF_TASK_INSTANCE.SUGGEST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return UfTaskInstance.UF_TASK_INSTANCE.TENANT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field10() {
        return UfTaskInstance.UF_TASK_INSTANCE.OPERATOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return UfTaskInstance.UF_TASK_INSTANCE.APPROVERS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field12() {
        return UfTaskInstance.UF_TASK_INSTANCE.NODE_ORDER;
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
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component6() {
        return getInstanceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component7() {
        return getNodeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getSuggest();
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
    public ULong component10() {
        return getOperatorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getApprovers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component12() {
        return getNodeOrder();
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
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value6() {
        return getInstanceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value7() {
        return getNodeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getSuggest();
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
    public ULong value10() {
        return getOperatorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getApprovers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value12() {
        return getNodeOrder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value4(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value5(String value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value6(UInteger value) {
        setInstanceId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value7(UInteger value) {
        setNodeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value8(String value) {
        setSuggest(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value9(String value) {
        setTenantCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value10(ULong value) {
        setOperatorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value11(String value) {
        setApprovers(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord value12(UInteger value) {
        setNodeOrder(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfTaskInstanceRecord values(ULong value1, Timestamp value2, Timestamp value3, String value4, String value5, UInteger value6, UInteger value7, String value8, String value9, ULong value10, String value11, UInteger value12) {
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
        value11(value11);
        value12(value12);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UfTaskInstanceRecord
     */
    public UfTaskInstanceRecord() {
        super(UfTaskInstance.UF_TASK_INSTANCE);
    }

    /**
     * Create a detached, initialised UfTaskInstanceRecord
     */
    public UfTaskInstanceRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String title, String status, UInteger instanceId, UInteger nodeId, String suggest, String tenantCode, ULong operatorId, String approvers, UInteger nodeOrder) {
        super(UfTaskInstance.UF_TASK_INSTANCE);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, title);
        set(4, status);
        set(5, instanceId);
        set(6, nodeId);
        set(7, suggest);
        set(8, tenantCode);
        set(9, operatorId);
        set(10, approvers);
        set(11, nodeOrder);
    }
}
