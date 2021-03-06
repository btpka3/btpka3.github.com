/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record16;
import org.jooq.Row16;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import test.generated.tables.CalcIndicatorAccRecordNode;


/**
 * 特征周期计算任务节点表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CalcIndicatorAccRecordNodeRecord extends UpdatableRecordImpl<CalcIndicatorAccRecordNodeRecord> implements Record16<ULong, Timestamp, Timestamp, ULong, String, String, String, String, Integer, String, String, String, String, String, String, Integer> {

    private static final long serialVersionUID = -459710122;

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.record_id</code>. acc_record关联ID
     */
    public void setRecordId(ULong value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.record_id</code>. acc_record关联ID
     */
    public ULong getRecordId() {
        return (ULong) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.operator_id</code>. 操作人工号
     */
    public void setOperatorId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.operator_id</code>. 操作人工号
     */
    public String getOperatorId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.operator</code>. 操作人
     */
    public void setOperator(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.operator</code>. 操作人
     */
    public String getOperator() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.access_start_time</code>. 取数开始时间，格式：2015-12-23
     */
    public void setAccessStartTime(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.access_start_time</code>. 取数开始时间，格式：2015-12-23
     */
    public String getAccessStartTime() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.access_end_time</code>. 取数结束时间，格式：2015-12-23
     */
    public void setAccessEndTime(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.access_end_time</code>. 取数结束时间，格式：2015-12-23
     */
    public String getAccessEndTime() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.node_step</code>. 节点计算阶段。1：仅最终计算一个节点；11：预处理，22：指标计算，33：分批join，44：最终join
     */
    public void setNodeStep(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.node_step</code>. 节点计算阶段。1：仅最终计算一个节点；11：预处理，22：指标计算，33：分批join，44：最终join
     */
    public Integer getNodeStep() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.resulte_table_name</code>. 各阶段结果表名
     */
    public void setResulteTableName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.resulte_table_name</code>. 各阶段结果表名
     */
    public String getResulteTableName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.ddl_node_id</code>. DDL节点ID
     */
    public void setDdlNodeId(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.ddl_node_id</code>. DDL节点ID
     */
    public String getDdlNodeId() {
        return (String) get(10);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.ddl_node_context</code>. DDL节点内容
     */
    public void setDdlNodeContext(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.ddl_node_context</code>. DDL节点内容
     */
    public String getDdlNodeContext() {
        return (String) get(11);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.node_id</code>. 节点ID
     */
    public void setNodeId(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.node_id</code>. 节点ID
     */
    public String getNodeId() {
        return (String) get(12);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.node_context</code>. 节点内容
     */
    public void setNodeContext(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.node_context</code>. 节点内容
     */
    public String getNodeContext() {
        return (String) get(13);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.failure_msg</code>. 节点创建失败原因
     */
    public void setFailureMsg(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.failure_msg</code>. 节点创建失败原因
     */
    public String getFailureMsg() {
        return (String) get(14);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_acc_record_node.status</code>. 状态：1正常，2失败
     */
    public void setStatus(Integer value) {
        set(15, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_acc_record_node.status</code>. 状态：1正常，2失败
     */
    public Integer getStatus() {
        return (Integer) get(15);
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
    // Record16 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row16<ULong, Timestamp, Timestamp, ULong, String, String, String, String, Integer, String, String, String, String, String, String, Integer> fieldsRow() {
        return (Row16) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row16<ULong, Timestamp, Timestamp, ULong, String, String, String, String, Integer, String, String, String, String, String, String, Integer> valuesRow() {
        return (Row16) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field4() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.RECORD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.OPERATOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.OPERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.ACCESS_START_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.ACCESS_END_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.NODE_STEP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.RESULTE_TABLE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.DDL_NODE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.DDL_NODE_CONTEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.NODE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.NODE_CONTEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.FAILURE_MSG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field16() {
        return CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE.STATUS;
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
        return getRecordId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getOperatorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getAccessStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getAccessEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getNodeStep();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getResulteTableName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getDdlNodeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getDdlNodeContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getNodeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getNodeContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component15() {
        return getFailureMsg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component16() {
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
        return getRecordId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getOperatorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getAccessStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getAccessEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getNodeStep();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getResulteTableName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getDdlNodeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getDdlNodeContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getNodeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getNodeContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getFailureMsg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value16() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value4(ULong value) {
        setRecordId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value5(String value) {
        setOperatorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value6(String value) {
        setOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value7(String value) {
        setAccessStartTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value8(String value) {
        setAccessEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value9(Integer value) {
        setNodeStep(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value10(String value) {
        setResulteTableName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value11(String value) {
        setDdlNodeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value12(String value) {
        setDdlNodeContext(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value13(String value) {
        setNodeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value14(String value) {
        setNodeContext(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value15(String value) {
        setFailureMsg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord value16(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcIndicatorAccRecordNodeRecord values(ULong value1, Timestamp value2, Timestamp value3, ULong value4, String value5, String value6, String value7, String value8, Integer value9, String value10, String value11, String value12, String value13, String value14, String value15, Integer value16) {
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
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CalcIndicatorAccRecordNodeRecord
     */
    public CalcIndicatorAccRecordNodeRecord() {
        super(CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE);
    }

    /**
     * Create a detached, initialised CalcIndicatorAccRecordNodeRecord
     */
    public CalcIndicatorAccRecordNodeRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, ULong recordId, String operatorId, String operator, String accessStartTime, String accessEndTime, Integer nodeStep, String resulteTableName, String ddlNodeId, String ddlNodeContext, String nodeId, String nodeContext, String failureMsg, Integer status) {
        super(CalcIndicatorAccRecordNode.CALC_INDICATOR_ACC_RECORD_NODE);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, recordId);
        set(4, operatorId);
        set(5, operator);
        set(6, accessStartTime);
        set(7, accessEndTime);
        set(8, nodeStep);
        set(9, resulteTableName);
        set(10, ddlNodeId);
        set(11, ddlNodeContext);
        set(12, nodeId);
        set(13, nodeContext);
        set(14, failureMsg);
        set(15, status);
    }
}
