/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import test.generated.tables.UfExperimentConfig;


/**
 * 规则实验室实验配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UfExperimentConfigRecord extends UpdatableRecordImpl<UfExperimentConfigRecord> {

    private static final long serialVersionUID = 88959875;

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.rule_set_id</code>. 规则集编号
     */
    public void setRuleSetId(ULong value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.rule_set_id</code>. 规则集编号
     */
    public ULong getRuleSetId() {
        return (ULong) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.rule_id</code>. 规则编号
     */
    public void setRuleId(ULong value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.rule_id</code>. 规则编号
     */
    public ULong getRuleId() {
        return (ULong) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.rule_version</code>. 规则版本号
     */
    public void setRuleVersion(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.rule_version</code>. 规则版本号
     */
    public Integer getRuleVersion() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.rule_title</code>. 规则标题
     */
    public void setRuleTitle(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.rule_title</code>. 规则标题
     */
    public String getRuleTitle() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.rule_status</code>. 规则状态
     */
    public void setRuleStatus(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.rule_status</code>. 规则状态
     */
    public Integer getRuleStatus() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.event_code</code>. 事件code
     */
    public void setEventCode(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.event_code</code>. 事件code
     */
    public String getEventCode() {
        return (String) get(8);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.event_title</code>. 事件名称
     */
    public void setEventTitle(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.event_title</code>. 事件名称
     */
    public String getEventTitle() {
        return (String) get(9);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.visual_settings</code>. 规则结构
     */
    public void setVisualSettings(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.visual_settings</code>. 规则结构
     */
    public String getVisualSettings() {
        return (String) get(10);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.creator</code>. 实验创建人
     */
    public void setCreator(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.creator</code>. 实验创建人
     */
    public String getCreator() {
        return (String) get(11);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.last_operator</code>. 实验最近一次修改人
     */
    public void setLastOperator(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.last_operator</code>. 实验最近一次修改人
     */
    public String getLastOperator() {
        return (String) get(12);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.start_time</code>. 实验开始时间
     */
    public void setStartTime(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.start_time</code>. 实验开始时间
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(13);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.prepare_data_end_time</code>. 取数阶段完成时间
     */
    public void setPrepareDataEndTime(Timestamp value) {
        set(14, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.prepare_data_end_time</code>. 取数阶段完成时间
     */
    public Timestamp getPrepareDataEndTime() {
        return (Timestamp) get(14);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.end_time</code>. 结束时间
     */
    public void setEndTime(Timestamp value) {
        set(15, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.end_time</code>. 结束时间
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(15);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.sample_info</code>. 样本信息
     */
    public void setSampleInfo(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.sample_info</code>. 样本信息
     */
    public String getSampleInfo() {
        return (String) get(16);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.status</code>. 实验状态,0:失败,1:成功,2:实验运行中
     */
    public void setStatus(Integer value) {
        set(17, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.status</code>. 实验状态,0:失败,1:成功,2:实验运行中
     */
    public Integer getStatus() {
        return (Integer) get(17);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.task_info</code>. 任务信息
     */
    public void setTaskInfo(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.task_info</code>. 任务信息
     */
    public String getTaskInfo() {
        return (String) get(18);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.extra_task_info</code>. 变量任务信息
     */
    public void setExtraTaskInfo(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.extra_task_info</code>. 变量任务信息
     */
    public String getExtraTaskInfo() {
        return (String) get(19);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.confusion_matrix</code>. 样本命中情况(混淆矩阵),json格式
     */
    public void setConfusionMatrix(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.confusion_matrix</code>. 样本命中情况(混淆矩阵),json格式
     */
    public String getConfusionMatrix() {
        return (String) get(20);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.hit_rate</code>. 规则样本命中情况,json格式
     */
    public void setHitRate(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.hit_rate</code>. 规则样本命中情况,json格式
     */
    public String getHitRate() {
        return (String) get(21);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.tenant_code</code>. 租户号
     */
    public void setTenantCode(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.tenant_code</code>. 租户号
     */
    public String getTenantCode() {
        return (String) get(22);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.rule_sub_version</code>. 规则副版本
     */
    public void setRuleSubVersion(Integer value) {
        set(23, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.rule_sub_version</code>. 规则副版本
     */
    public Integer getRuleSubVersion() {
        return (Integer) get(23);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.emp_id</code>. 工号
     */
    public void setEmpId(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.emp_id</code>. 工号
     */
    public String getEmpId() {
        return (String) get(24);
    }

    /**
     * Setter for <code>SMETA_APP.uf_experiment_config.open_account_id</code>. 统一标志编码
     */
    public void setOpenAccountId(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>SMETA_APP.uf_experiment_config.open_account_id</code>. 统一标志编码
     */
    public String getOpenAccountId() {
        return (String) get(25);
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
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UfExperimentConfigRecord
     */
    public UfExperimentConfigRecord() {
        super(UfExperimentConfig.UF_EXPERIMENT_CONFIG);
    }

    /**
     * Create a detached, initialised UfExperimentConfigRecord
     */
    public UfExperimentConfigRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, ULong ruleSetId, ULong ruleId, Integer ruleVersion, String ruleTitle, Integer ruleStatus, String eventCode, String eventTitle, String visualSettings, String creator, String lastOperator, Timestamp startTime, Timestamp prepareDataEndTime, Timestamp endTime, String sampleInfo, Integer status, String taskInfo, String extraTaskInfo, String confusionMatrix, String hitRate, String tenantCode, Integer ruleSubVersion, String empId, String openAccountId) {
        super(UfExperimentConfig.UF_EXPERIMENT_CONFIG);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, ruleSetId);
        set(4, ruleId);
        set(5, ruleVersion);
        set(6, ruleTitle);
        set(7, ruleStatus);
        set(8, eventCode);
        set(9, eventTitle);
        set(10, visualSettings);
        set(11, creator);
        set(12, lastOperator);
        set(13, startTime);
        set(14, prepareDataEndTime);
        set(15, endTime);
        set(16, sampleInfo);
        set(17, status);
        set(18, taskInfo);
        set(19, extraTaskInfo);
        set(20, confusionMatrix);
        set(21, hitRate);
        set(22, tenantCode);
        set(23, ruleSubVersion);
        set(24, empId);
        set(25, openAccountId);
    }
}
