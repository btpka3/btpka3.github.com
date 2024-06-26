/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record22;
import org.jooq.Row22;
import org.jooq.impl.TableRecordImpl;
import org.jooq.types.ULong;

import test.generated.tables.UfRulesetRunning;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfRulesetRunningRecord extends TableRecordImpl<UfRulesetRunningRecord> implements Record22<ULong, ULong, Timestamp, Timestamp, String, String, ULong, Integer, String, Integer, String, Integer, Integer, Integer, String, String, String, String, Integer, String, String, String> {

    private static final long serialVersionUID = 601378960;

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.rule_set_id</code>. 规则集Id
     */
    public void setRuleSetId(ULong value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.rule_set_id</code>. 规则集Id
     */
    public ULong getRuleSetId() {
        return (ULong) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.title</code>. 规则集标题
     */
    public void setTitle(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.title</code>. 规则集标题
     */
    public String getTitle() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.contacts</code>. 联系人
     */
    public void setContacts(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.contacts</code>. 联系人
     */
    public String getContacts() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.last_operator</code>. 最后操作人
     */
    public void setLastOperator(ULong value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.last_operator</code>. 最后操作人
     */
    public ULong getLastOperator() {
        return (ULong) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.version</code>. 当前版本
     */
    public void setVersion(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.version</code>. 当前版本
     */
    public Integer getVersion() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.comment</code>. 版本备注
     */
    public void setComment(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.comment</code>. 版本备注
     */
    public String getComment() {
        return (String) get(8);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.status</code>. 状态
     */
    public void setStatus(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.status</code>. 状态
     */
    public Integer getStatus() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.tenant_code</code>. 租户
     */
    public void setTenantCode(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.tenant_code</code>. 租户
     */
    public String getTenantCode() {
        return (String) get(10);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.type</code>. 规则集类型
     */
    public void setType(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.type</code>. 规则集类型
     */
    public Integer getType() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.risk_type_id</code>. 风险分类
     */
    public void setRiskTypeId(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.risk_type_id</code>. 风险分类
     */
    public Integer getRiskTypeId() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.risk_level</code>. 风险等级
     */
    public void setRiskLevel(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.risk_level</code>. 风险等级
     */
    public Integer getRiskLevel() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.settings</code>. 规则集设置
     */
    public void setSettings(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.settings</code>. 规则集设置
     */
    public String getSettings() {
        return (String) get(14);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.content</code>. 规则集内容
     */
    public void setContent(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.content</code>. 规则集内容
     */
    public String getContent() {
        return (String) get(15);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.source_code</code>. 数据来源
     */
    public void setSourceCode(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.source_code</code>. 数据来源
     */
    public String getSourceCode() {
        return (String) get(16);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.description</code>. 规则集描述
     */
    public void setDescription(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.description</code>. 规则集描述
     */
    public String getDescription() {
        return (String) get(17);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.level</code>. 规则集分层
     */
    public void setLevel(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.level</code>. 规则集分层
     */
    public Integer getLevel() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.source_name</code>. 数据来源名称
     */
    public void setSourceName(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.source_name</code>. 数据来源名称
     */
    public String getSourceName() {
        return (String) get(19);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.visual_settings</code>. 规则集可视化设置
     */
    public void setVisualSettings(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.visual_settings</code>. 规则集可视化设置
     */
    public String getVisualSettings() {
        return (String) get(20);
    }

    /**
     * Setter for <code>SMETA_APP.uf_ruleset_running.risk_type_path</code>. 风险分类路径
     */
    public void setRiskTypePath(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_ruleset_running.risk_type_path</code>. 风险分类路径
     */
    public String getRiskTypePath() {
        return (String) get(21);
    }

    // -------------------------------------------------------------------------
    // Record22 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row22<ULong, ULong, Timestamp, Timestamp, String, String, ULong, Integer, String, Integer, String, Integer, Integer, Integer, String, String, String, String, Integer, String, String, String> fieldsRow() {
        return (Row22) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row22<ULong, ULong, Timestamp, Timestamp, String, String, ULong, Integer, String, Integer, String, Integer, Integer, Integer, String, String, String, String, Integer, String, String, String> valuesRow() {
        return (Row22) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return UfRulesetRunning.UF_RULESET_RUNNING.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field2() {
        return UfRulesetRunning.UF_RULESET_RUNNING.RULE_SET_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return UfRulesetRunning.UF_RULESET_RUNNING.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return UfRulesetRunning.UF_RULESET_RUNNING.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return UfRulesetRunning.UF_RULESET_RUNNING.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return UfRulesetRunning.UF_RULESET_RUNNING.CONTACTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field7() {
        return UfRulesetRunning.UF_RULESET_RUNNING.LAST_OPERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return UfRulesetRunning.UF_RULESET_RUNNING.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return UfRulesetRunning.UF_RULESET_RUNNING.COMMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return UfRulesetRunning.UF_RULESET_RUNNING.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return UfRulesetRunning.UF_RULESET_RUNNING.TENANT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return UfRulesetRunning.UF_RULESET_RUNNING.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return UfRulesetRunning.UF_RULESET_RUNNING.RISK_TYPE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return UfRulesetRunning.UF_RULESET_RUNNING.RISK_LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return UfRulesetRunning.UF_RULESET_RUNNING.SETTINGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field16() {
        return UfRulesetRunning.UF_RULESET_RUNNING.CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field17() {
        return UfRulesetRunning.UF_RULESET_RUNNING.SOURCE_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field18() {
        return UfRulesetRunning.UF_RULESET_RUNNING.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field19() {
        return UfRulesetRunning.UF_RULESET_RUNNING.LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field20() {
        return UfRulesetRunning.UF_RULESET_RUNNING.SOURCE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field21() {
        return UfRulesetRunning.UF_RULESET_RUNNING.VISUAL_SETTINGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field22() {
        return UfRulesetRunning.UF_RULESET_RUNNING.RISK_TYPE_PATH;
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
    public ULong component2() {
        return getRuleSetId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getGmtCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getGmtModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getContacts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component7() {
        return getLastOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getComment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component13() {
        return getRiskTypeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component14() {
        return getRiskLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component15() {
        return getSettings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component16() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component17() {
        return getSourceCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component18() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component19() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component20() {
        return getSourceName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component21() {
        return getVisualSettings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component22() {
        return getRiskTypePath();
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
    public ULong value2() {
        return getRuleSetId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getGmtCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getGmtModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getContacts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value7() {
        return getLastOperator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getComment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getRiskTypeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value14() {
        return getRiskLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getSettings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value16() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value17() {
        return getSourceCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value18() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value19() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value20() {
        return getSourceName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value21() {
        return getVisualSettings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value22() {
        return getRiskTypePath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value2(ULong value) {
        setRuleSetId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value3(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value4(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value5(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value6(String value) {
        setContacts(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value7(ULong value) {
        setLastOperator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value8(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value9(String value) {
        setComment(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value10(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value11(String value) {
        setTenantCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value12(Integer value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value13(Integer value) {
        setRiskTypeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value14(Integer value) {
        setRiskLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value15(String value) {
        setSettings(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value16(String value) {
        setContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value17(String value) {
        setSourceCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value18(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value19(Integer value) {
        setLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value20(String value) {
        setSourceName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value21(String value) {
        setVisualSettings(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord value22(String value) {
        setRiskTypePath(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UfRulesetRunningRecord values(ULong value1, ULong value2, Timestamp value3, Timestamp value4, String value5, String value6, ULong value7, Integer value8, String value9, Integer value10, String value11, Integer value12, Integer value13, Integer value14, String value15, String value16, String value17, String value18, Integer value19, String value20, String value21, String value22) {
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
        value21(value21);
        value22(value22);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UfRulesetRunningRecord
     */
    public UfRulesetRunningRecord() {
        super(UfRulesetRunning.UF_RULESET_RUNNING);
    }

    /**
     * Create a detached, initialised UfRulesetRunningRecord
     */
    public UfRulesetRunningRecord(ULong id, ULong ruleSetId, Timestamp gmtCreate, Timestamp gmtModified, String title, String contacts, ULong lastOperator, Integer version, String comment, Integer status, String tenantCode, Integer type, Integer riskTypeId, Integer riskLevel, String settings, String content, String sourceCode, String description, Integer level, String sourceName, String visualSettings, String riskTypePath) {
        super(UfRulesetRunning.UF_RULESET_RUNNING);

        set(0, id);
        set(1, ruleSetId);
        set(2, gmtCreate);
        set(3, gmtModified);
        set(4, title);
        set(5, contacts);
        set(6, lastOperator);
        set(7, version);
        set(8, comment);
        set(9, status);
        set(10, tenantCode);
        set(11, type);
        set(12, riskTypeId);
        set(13, riskLevel);
        set(14, settings);
        set(15, content);
        set(16, sourceCode);
        set(17, description);
        set(18, level);
        set(19, sourceName);
        set(20, visualSettings);
        set(21, riskTypePath);
    }
}
