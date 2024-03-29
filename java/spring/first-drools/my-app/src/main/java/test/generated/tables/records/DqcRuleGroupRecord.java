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

import test.generated.tables.DqcRuleGroup;


/**
 * dqc规则分组
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DqcRuleGroupRecord extends UpdatableRecordImpl<DqcRuleGroupRecord> implements Record7<ULong, Timestamp, Timestamp, String, Integer, String, String> {

    private static final long serialVersionUID = -552209905;

    /**
     * Setter for <code>SMETA_APP.dqc_rule_group.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_rule_group.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_rule_group.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_rule_group.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_rule_group.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_rule_group.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_rule_group.group_name</code>. 规则分组名
     */
    public void setGroupName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_rule_group.group_name</code>. 规则分组名
     */
    public String getGroupName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_rule_group.status</code>. 规则分组状态，1-可用，0-不可用
     */
    public void setStatus(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_rule_group.status</code>. 规则分组状态，1-可用，0-不可用
     */
    public Integer getStatus() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_rule_group.last_operator</code>. 分组最后编辑人
     */
    public void setLastOperator(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_rule_group.last_operator</code>. 分组最后编辑人
     */
    public String getLastOperator() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.dqc_rule_group.group_code</code>. 分组code，不可重复
     */
    public void setGroupCode(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.dqc_rule_group.group_code</code>. 分组code，不可重复
     */
    public String getGroupCode() {
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
    public Row7<ULong, Timestamp, Timestamp, String, Integer, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<ULong, Timestamp, Timestamp, String, Integer, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return DqcRuleGroup.DQC_RULE_GROUP.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return DqcRuleGroup.DQC_RULE_GROUP.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return DqcRuleGroup.DQC_RULE_GROUP.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return DqcRuleGroup.DQC_RULE_GROUP.GROUP_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return DqcRuleGroup.DQC_RULE_GROUP.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return DqcRuleGroup.DQC_RULE_GROUP.LAST_OPERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return DqcRuleGroup.DQC_RULE_GROUP.GROUP_CODE;
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
        return getGroupName();
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
    public String component6() {
        return getLastOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getGroupCode();
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
        return getGroupName();
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
    public String value6() {
        return getLastOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getGroupCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcRuleGroupRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcRuleGroupRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcRuleGroupRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcRuleGroupRecord value4(String value) {
        setGroupName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcRuleGroupRecord value5(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcRuleGroupRecord value6(String value) {
        setLastOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcRuleGroupRecord value7(String value) {
        setGroupCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DqcRuleGroupRecord values(ULong value1, Timestamp value2, Timestamp value3, String value4, Integer value5, String value6, String value7) {
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
     * Create a detached DqcRuleGroupRecord
     */
    public DqcRuleGroupRecord() {
        super(DqcRuleGroup.DQC_RULE_GROUP);
    }

    /**
     * Create a detached, initialised DqcRuleGroupRecord
     */
    public DqcRuleGroupRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String groupName, Integer status, String lastOperator, String groupCode) {
        super(DqcRuleGroup.DQC_RULE_GROUP);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, groupName);
        set(4, status);
        set(5, lastOperator);
        set(6, groupCode);
    }
}
