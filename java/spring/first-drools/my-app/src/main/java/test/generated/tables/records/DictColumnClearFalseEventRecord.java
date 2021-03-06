/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record17;
import org.jooq.Row17;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import test.generated.tables.DictColumnClearFalseEvent;


/**
 * 数据字典可清除属性信息统计_未挂到事件
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictColumnClearFalseEventRecord extends UpdatableRecordImpl<DictColumnClearFalseEventRecord> implements Record17<ULong, Timestamp, Timestamp, String, String, String, String, String, String, String, String, String, String, String, String, String, String> {

    private static final long serialVersionUID = -1829069386;

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.dict_code</code>. 属性代码
     */
    public void setDictCode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.dict_code</code>. 属性代码
     */
    public String getDictCode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.dict_name</code>. 属性名称
     */
    public void setDictName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.dict_name</code>. 属性名称
     */
    public String getDictName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.dict_operator</code>. 操作人
     */
    public void setDictOperator(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.dict_operator</code>. 操作人
     */
    public String getDictOperator() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.dict_status</code>. 状态
     */
    public void setDictStatus(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.dict_status</code>. 状态
     */
    public String getDictStatus() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.tag_code</code>. 标志位代码:0-无任何下游；1-下游：有且只有事件；2-下游：有且只有指标；3-下游：有且只有规则；4-下游：有且只有脚本；5-下游：有且只有实体；6-下游：有且只有odps；
     */
    public void setTagCode(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.tag_code</code>. 标志位代码:0-无任何下游；1-下游：有且只有事件；2-下游：有且只有指标；3-下游：有且只有规则；4-下游：有且只有脚本；5-下游：有且只有实体；6-下游：有且只有odps；
     */
    public String getTagCode() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.tag_name</code>. 标志位名称
     */
    public void setTagName(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.tag_name</code>. 标志位名称
     */
    public String getTagName() {
        return (String) get(8);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.blood_code</code>. 血缘代码
     */
    public void setBloodCode(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.blood_code</code>. 血缘代码
     */
    public String getBloodCode() {
        return (String) get(9);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.blood_name</code>. 血缘名称
     */
    public void setBloodName(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.blood_name</code>. 血缘名称
     */
    public String getBloodName() {
        return (String) get(10);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.blood_operator</code>. 下游操作人
     */
    public void setBloodOperator(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.blood_operator</code>. 下游操作人
     */
    public String getBloodOperator() {
        return (String) get(11);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.event_code</code>. 事件编码
     */
    public void setEventCode(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.event_code</code>. 事件编码
     */
    public String getEventCode() {
        return (String) get(12);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.event_name</code>. 事件名称
     */
    public void setEventName(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.event_name</code>. 事件名称
     */
    public String getEventName() {
        return (String) get(13);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.dict_cost</code>. 属性加工成本
     */
    public void setDictCost(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.dict_cost</code>. 属性加工成本
     */
    public String getDictCost() {
        return (String) get(14);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.reserve</code>. 备用字段
     */
    public void setReserve(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.reserve</code>. 备用字段
     */
    public String getReserve() {
        return (String) get(15);
    }

    /**
     * Setter for <code>SMETA_APP.dict_column_clear_false_event.tenant_code</code>. 租户
     */
    public void setTenantCode(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>SMETA_APP.dict_column_clear_false_event.tenant_code</code>. 租户
     */
    public String getTenantCode() {
        return (String) get(16);
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
    // Record17 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row17<ULong, Timestamp, Timestamp, String, String, String, String, String, String, String, String, String, String, String, String, String, String> fieldsRow() {
        return (Row17) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row17<ULong, Timestamp, Timestamp, String, String, String, String, String, String, String, String, String, String, String, String, String, String> valuesRow() {
        return (Row17) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.DICT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.DICT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.DICT_OPERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.DICT_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.TAG_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.TAG_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.BLOOD_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.BLOOD_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.BLOOD_OPERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.EVENT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.EVENT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.DICT_COST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field16() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.RESERVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field17() {
        return DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT.TENANT_CODE;
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
    public DictColumnClearFalseEventRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value4(String value) {
        setDictCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value5(String value) {
        setDictName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value6(String value) {
        setDictOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value7(String value) {
        setDictStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value8(String value) {
        setTagCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value9(String value) {
        setTagName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value10(String value) {
        setBloodCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value11(String value) {
        setBloodName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value12(String value) {
        setBloodOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value13(String value) {
        setEventCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value14(String value) {
        setEventName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value15(String value) {
        setDictCost(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value16(String value) {
        setReserve(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord value17(String value) {
        setTenantCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictColumnClearFalseEventRecord values(ULong value1, Timestamp value2, Timestamp value3, String value4, String value5, String value6, String value7, String value8, String value9, String value10, String value11, String value12, String value13, String value14, String value15, String value16, String value17) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DictColumnClearFalseEventRecord
     */
    public DictColumnClearFalseEventRecord() {
        super(DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT);
    }

    /**
     * Create a detached, initialised DictColumnClearFalseEventRecord
     */
    public DictColumnClearFalseEventRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String dictCode, String dictName, String dictOperator, String dictStatus, String tagCode, String tagName, String bloodCode, String bloodName, String bloodOperator, String eventCode, String eventName, String dictCost, String reserve, String tenantCode) {
        super(DictColumnClearFalseEvent.DICT_COLUMN_CLEAR_FALSE_EVENT);

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
    }
}
