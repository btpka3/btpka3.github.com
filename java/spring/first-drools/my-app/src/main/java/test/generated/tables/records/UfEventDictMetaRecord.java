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

import test.generated.tables.UfEventDictMeta;


/**
 * 事件数据字典信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfEventDictMetaRecord extends UpdatableRecordImpl<UfEventDictMetaRecord> implements Record9<ULong, Timestamp, Timestamp, String, String, String, String, String, ULong> {

    private static final long serialVersionUID = 1861652351;

    /**
     * Setter for <code>SMETA_APP.uf_event_dict_meta.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_event_dict_meta.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.uf_event_dict_meta.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_event_dict_meta.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.uf_event_dict_meta.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_event_dict_meta.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.uf_event_dict_meta.tenant_code</code>. 租户
     */
    public void setTenantCode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_event_dict_meta.tenant_code</code>. 租户
     */
    public String getTenantCode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.uf_event_dict_meta.namespace</code>. 命名空间
     */
    public void setNamespace(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_event_dict_meta.namespace</code>. 命名空间
     */
    public String getNamespace() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.uf_event_dict_meta.event_code</code>. 事件编码
     */
    public void setEventCode(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_event_dict_meta.event_code</code>. 事件编码
     */
    public String getEventCode() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.uf_event_dict_meta.dict_code</code>. 数据字典
     */
    public void setDictCode(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_event_dict_meta.dict_code</code>. 数据字典
     */
    public String getDictCode() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.uf_event_dict_meta.dict_type</code>. 数据字典对应的类型
     */
    public void setDictType(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_event_dict_meta.dict_type</code>. 数据字典对应的类型
     */
    public String getDictType() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.uf_event_dict_meta.operator</code>. 操作人
     */
    public void setOperator(ULong value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_event_dict_meta.operator</code>. 操作人
     */
    public ULong getOperator() {
        return (ULong) get(8);
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
    public Row9<ULong, Timestamp, Timestamp, String, String, String, String, String, ULong> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<ULong, Timestamp, Timestamp, String, String, String, String, String, ULong> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return UfEventDictMeta.UF_EVENT_DICT_META.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return UfEventDictMeta.UF_EVENT_DICT_META.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return UfEventDictMeta.UF_EVENT_DICT_META.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return UfEventDictMeta.UF_EVENT_DICT_META.TENANT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return UfEventDictMeta.UF_EVENT_DICT_META.NAMESPACE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return UfEventDictMeta.UF_EVENT_DICT_META.EVENT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return UfEventDictMeta.UF_EVENT_DICT_META.DICT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return UfEventDictMeta.UF_EVENT_DICT_META.DICT_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field9() {
        return UfEventDictMeta.UF_EVENT_DICT_META.OPERATOR;
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
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getNamespace();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getEventCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getDictCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getDictType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component9() {
        return getOperator();
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
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getNamespace();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getEventCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getDictCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getDictType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value9() {
        return getOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfEventDictMetaRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfEventDictMetaRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfEventDictMetaRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfEventDictMetaRecord value4(String value) {
        setTenantCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfEventDictMetaRecord value5(String value) {
        setNamespace(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfEventDictMetaRecord value6(String value) {
        setEventCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfEventDictMetaRecord value7(String value) {
        setDictCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfEventDictMetaRecord value8(String value) {
        setDictType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfEventDictMetaRecord value9(ULong value) {
        setOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfEventDictMetaRecord values(ULong value1, Timestamp value2, Timestamp value3, String value4, String value5, String value6, String value7, String value8, ULong value9) {
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
     * Create a detached UfEventDictMetaRecord
     */
    public UfEventDictMetaRecord() {
        super(UfEventDictMeta.UF_EVENT_DICT_META);
    }

    /**
     * Create a detached, initialised UfEventDictMetaRecord
     */
    public UfEventDictMetaRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String tenantCode, String namespace, String eventCode, String dictCode, String dictType, ULong operator) {
        super(UfEventDictMeta.UF_EVENT_DICT_META);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, tenantCode);
        set(4, namespace);
        set(5, eventCode);
        set(6, dictCode);
        set(7, dictType);
        set(8, operator);
    }
}