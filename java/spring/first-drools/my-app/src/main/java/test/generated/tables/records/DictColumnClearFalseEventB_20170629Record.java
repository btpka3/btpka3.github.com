/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record20;
import org.jooq.Row20;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import test.generated.tables.DictColumnClearFalseEventB_20170629;


/**
 * 数据字典可清除
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictColumnClearFalseEventB_20170629Record extends UpdatableRecordImpl<DictColumnClearFalseEventB_20170629Record> implements Record20<ULong, Timestamp, Timestamp, String, String, String, String, String, String, String, String, String, String, String, String, String, String, ULong, String, String> {

    private static final long serialVersionUID = 1954660158;

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.dict_code</code>. 属性代码
     */
    public void setDictCode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.dict_code</code>. 属性代码
     */
    public String getDictCode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.dict_name</code>. 属性名称
     */
    public void setDictName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.dict_name</code>. 属性名称
     */
    public String getDictName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.dict_operator</code>. 操作人
     */
    public void setDictOperator(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.dict_operator</code>. 操作人
     */
    public String getDictOperator() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.dict_status</code>. 状态
     */
    public void setDictStatus(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.dict_status</code>. 状态
     */
    public String getDictStatus() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.tag_code</code>. 标志位代码:0-无任何下游；1-下游：有且只有事件；2-下游：有且只有指标；3-下游：有且只有规则；4-下游：有且只有脚本；5-下游：有且只有实体；6-下游：有且只有odps；
     */
    public void setTagCode(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.tag_code</code>. 标志位代码:0-无任何下游；1-下游：有且只有事件；2-下游：有且只有指标；3-下游：有且只有规则；4-下游：有且只有脚本；5-下游：有且只有实体；6-下游：有且只有odps；
     */
    public String getTagCode() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.tag_name</code>. 标志位名称
     */
    public void setTagName(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.tag_name</code>. 标志位名称
     */
    public String getTagName() {
        return (String) get(8);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.blood_code</code>. 血缘代码
     */
    public void setBloodCode(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.blood_code</code>. 血缘代码
     */
    public String getBloodCode() {
        return (String) get(9);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.blood_name</code>. 血缘名称
     */
    public void setBloodName(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.blood_name</code>. 血缘名称
     */
    public String getBloodName() {
        return (String) get(10);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.blood_operator</code>. 下游操作人
     */
    public void setBloodOperator(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.blood_operator</code>. 下游操作人
     */
    public String getBloodOperator() {
        return (String) get(11);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.event_code</code>. 事件编码
     */
    public void setEventCode(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.event_code</code>. 事件编码
     */
    public String getEventCode() {
        return (String) get(12);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.event_name</code>. 事件名称
     */
    public void setEventName(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.event_name</code>. 事件名称
     */
    public String getEventName() {
        return (String) get(13);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.dict_cost</code>. 属性加工成本
     */
    public void setDictCost(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.dict_cost</code>. 属性加工成本
     */
    public String getDictCost() {
        return (String) get(14);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.reserve</code>. 备用字段
     */
    public void setReserve(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.reserve</code>. 备用字段
     */
    public String getReserve() {
        return (String) get(15);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.tenant_code</code>. 租户
     */
    public void setTenantCode(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.tenant_code</code>. 租户
     */
    public String getTenantCode() {
        return (String) get(16);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.clear_rank</code>. 清除排序
     */
    public void setClearRank(ULong value) {
        set(17, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.clear_rank</code>. 清除排序
     */
    public ULong getClearRank() {
        return (ULong) get(17);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.clear_batch</code>. 清除批次
     */
    public void setClearBatch(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.clear_batch</code>. 清除批次
     */
    public String getClearBatch() {
        return (String) get(18);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.clear_date</code>. 清除日期
     */
    public void setClearDate(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event_b_20170629.clear_date</code>. 清除日期
     */
    public String getClearDate() {
        return (String) get(19);
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
    // Record20 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row20<ULong, Timestamp, Timestamp, String, String, String, String, String, String, String, String, String, String, String, String, String, String, ULong, String, String> fieldsRow() {
        return (Row20) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row20<ULong, Timestamp, Timestamp, String, String, String, String, String, String, String, String, String, String, String, String, String, String, ULong, String, String> valuesRow() {
        return (Row20) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.DICT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.DICT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.DICT_OPERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.DICT_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.TAG_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.TAG_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.BLOOD_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.BLOOD_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.BLOOD_OPERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.EVENT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.EVENT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.DICT_COST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field16() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.RESERVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field17() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.TENANT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field18() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.CLEAR_RANK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field19() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.CLEAR_BATCH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field20() {
        return DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629.CLEAR_DATE;
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
        return getDictCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getDictName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getDictOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getDictStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getTagCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getTagName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getBloodCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getBloodName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getBloodOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getEventCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getEventName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component15() {
        return getDictCost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component16() {
        return getReserve();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component17() {
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component18() {
        return getClearRank();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component19() {
        return getClearBatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component20() {
        return getClearDate();
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
        return getDictCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getDictName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getDictOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getDictStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getTagCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getTagName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getBloodCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getBloodName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getBloodOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getEventCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getEventName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getDictCost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value16() {
        return getReserve();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value17() {
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value18() {
        return getClearRank();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value19() {
        return getClearBatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value20() {
        return getClearDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value4(String value) {
        setDictCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value5(String value) {
        setDictName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value6(String value) {
        setDictOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value7(String value) {
        setDictStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value8(String value) {
        setTagCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value9(String value) {
        setTagName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value10(String value) {
        setBloodCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value11(String value) {
        setBloodName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value12(String value) {
        setBloodOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value13(String value) {
        setEventCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value14(String value) {
        setEventName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value15(String value) {
        setDictCost(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value16(String value) {
        setReserve(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value17(String value) {
        setTenantCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value18(ULong value) {
        setClearRank(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value19(String value) {
        setClearBatch(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record value20(String value) {
        setClearDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventB_20170629Record values(ULong value1, Timestamp value2, Timestamp value3, String value4, String value5, String value6, String value7, String value8, String value9, String value10, String value11, String value12, String value13, String value14, String value15, String value16, String value17, ULong value18, String value19, String value20) {
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
        value17(value17);
        value18(value18);
        value19(value19);
        value20(value20);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DictColumnClearFalseEventB_20170629Record
     */
    public DictColumnClearFalseEventB_20170629Record() {
        super(DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629);
    }

    /**
     * Create a detached, initialised DictColumnClearFalseEventB_20170629Record
     */
    public DictColumnClearFalseEventB_20170629Record(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String dictCode, String dictName, String dictOperator, String dictStatus, String tagCode, String tagName, String bloodCode, String bloodName, String bloodOperator, String eventCode, String eventName, String dictCost, String reserve, String tenantCode, ULong clearRank, String clearBatch, String clearDate) {
        super(DictColumnClearFalseEventB_20170629.DICT_COLUMN_CLEAR_FALSE_EVENT_B_20170629);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, dictCode);
        set(4, dictName);
        set(5, dictOperator);
        set(6, dictStatus);
        set(7, tagCode);
        set(8, tagName);
        set(9, bloodCode);
        set(10, bloodName);
        set(11, bloodOperator);
        set(12, eventCode);
        set(13, eventName);
        set(14, dictCost);
        set(15, reserve);
        set(16, tenantCode);
        set(17, clearRank);
        set(18, clearBatch);
        set(19, clearDate);
    }
}
