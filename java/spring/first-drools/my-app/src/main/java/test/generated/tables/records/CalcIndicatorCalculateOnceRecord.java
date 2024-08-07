/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import test.generated.tables.CalcIndicatorCalculateOnce;


/**
 * 指标一次性计算
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CalcIndicatorCalculateOnceRecord extends UpdatableRecordImpl<CalcIndicatorCalculateOnceRecord> {

    private static final long serialVersionUID = 1156214854;

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.created_by</code>. 创建人
     */
    public void setCreatedBy(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.created_by</code>. 创建人
     */
    public String getCreatedBy() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.updated_by</code>. 修改人
     */
    public void setUpdatedBy(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.updated_by</code>. 修改人
     */
    public String getUpdatedBy() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.indicator_code</code>. 指标编码
     */
    public void setIndicatorCode(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.indicator_code</code>. 指标编码
     */
    public String getIndicatorCode() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.indicator_name</code>. 指标名称
     */
    public void setIndicatorName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.indicator_name</code>. 指标名称
     */
    public String getIndicatorName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.table_name</code>. 计算事件上云表
     */
    public void setTableName(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.table_name</code>. 计算事件上云表
     */
    public String getTableName() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.calculate_event</code>. 计算事件编码
     */
    public void setCalculateEvent(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.calculate_event</code>. 计算事件编码
     */
    public String getCalculateEvent() {
        return (String) get(8);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.calculate_model</code>. 计算模式：事件级，窗口级
     */
    public void setCalculateModel(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.calculate_model</code>. 计算模式：事件级，窗口级
     */
    public String getCalculateModel() {
        return (String) get(9);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.calc_frequency</code>. 计算频率：110一次性按天，112一次性按小时
     */
    public void setCalcFrequency(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.calc_frequency</code>. 计算频率：110一次性按天，112一次性按小时
     */
    public Integer getCalcFrequency() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.groupby</code>. 指标主键集合
     */
    public void setGroupby(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.groupby</code>. 指标主键集合
     */
    public String getGroupby() {
        return (String) get(11);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.store_table</code>. 指标结果存储表
     */
    public void setStoreTable(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.store_table</code>. 指标结果存储表
     */
    public String getStoreTable() {
        return (String) get(12);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.calculate_code</code>. 计算编码
     */
    public void setCalculateCode(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.calculate_code</code>. 计算编码
     */
    public String getCalculateCode() {
        return (String) get(13);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.ddl_node_id</code>. DDL建表任务节点ID
     */
    public void setDdlNodeId(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.ddl_node_id</code>. DDL建表任务节点ID
     */
    public String getDdlNodeId() {
        return (String) get(14);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.ddl_sql</code>. DDL建表脚本
     */
    public void setDdlSql(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.ddl_sql</code>. DDL建表脚本
     */
    public String getDdlSql() {
        return (String) get(15);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.calc_node_id</code>. 计算任务节点ID
     */
    public void setCalcNodeId(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.calc_node_id</code>. 计算任务节点ID
     */
    public String getCalcNodeId() {
        return (String) get(16);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.calc_sql</code>. 计算任务脚本
     */
    public void setCalcSql(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.calc_sql</code>. 计算任务脚本
     */
    public String getCalcSql() {
        return (String) get(17);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.task_id</code>. 任务实例ID
     */
    public void setTaskId(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.task_id</code>. 任务实例ID
     */
    public String getTaskId() {
        return (String) get(18);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.calc_start_time</code>. 计算数据开始时间
     */
    public void setCalcStartTime(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.calc_start_time</code>. 计算数据开始时间
     */
    public String getCalcStartTime() {
        return (String) get(19);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.calc_end_time</code>. 计算数据结束时间
     */
    public void setCalcEndTime(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.calc_end_time</code>. 计算数据结束时间
     */
    public String getCalcEndTime() {
        return (String) get(20);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.life_cycle</code>. 生命周期
     */
    public void setLifeCycle(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.life_cycle</code>. 生命周期
     */
    public String getLifeCycle() {
        return (String) get(21);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.time_window</code>. 时间窗口
     */
    public void setTimeWindow(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.time_window</code>. 时间窗口
     */
    public String getTimeWindow() {
        return (String) get(22);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.calculate_status</code>. 任务状态： 1: "未运行",2: "等待时间", 3: "等待资源",4: "运行中",5: "运行失败",6: "运行成功"
     */
    public void setCalculateStatus(Integer value) {
        set(23, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.calculate_status</code>. 任务状态： 1: "未运行",2: "等待时间", 3: "等待资源",4: "运行中",5: "运行失败",6: "运行成功"
     */
    public Integer getCalculateStatus() {
        return (Integer) get(23);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.indicator_uniq_code</code>. 指标唯一编码
     */
    public void setIndicatorUniqCode(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.indicator_uniq_code</code>. 指标唯一编码
     */
    public String getIndicatorUniqCode() {
        return (String) get(24);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.indicator_rela_table</code>. 过滤表名
     */
    public void setIndicatorRelaTable(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.indicator_rela_table</code>. 过滤表名
     */
    public String getIndicatorRelaTable() {
        return (String) get(25);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.indicator_rela_key</code>. 指标主键映射字段集合
     */
    public void setIndicatorRelaKey(String value) {
        set(26, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.indicator_rela_key</code>. 指标主键映射字段集合
     */
    public String getIndicatorRelaKey() {
        return (String) get(26);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.scene_id</code>. 所属分析场景
     */
    public void setSceneId(Long value) {
        set(27, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.scene_id</code>. 所属分析场景
     */
    public Long getSceneId() {
        return (Long) get(27);
    }

    /**
     * Setter for <code>SMETA_APP.calc_indicator_calculate_once.table_status</code>. 指标结果表状态 1: "有效",0: "已过期"
     */
    public void setTableStatus(Integer value) {
        set(28, value);
    }

    /**
     * Getter for <code>SMETA_APP.calc_indicator_calculate_once.table_status</code>. 指标结果表状态 1: "有效",0: "已过期"
     */
    public Integer getTableStatus() {
        return (Integer) get(28);
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
     * Create a detached CalcIndicatorCalculateOnceRecord
     */
    public CalcIndicatorCalculateOnceRecord() {
        super(CalcIndicatorCalculateOnce.CALC_INDICATOR_CALCULATE_ONCE);
    }

    /**
     * Create a detached, initialised CalcIndicatorCalculateOnceRecord
     */
    public CalcIndicatorCalculateOnceRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String createdBy, String updatedBy, String indicatorCode, String indicatorName, String tableName, String calculateEvent, String calculateModel, Integer calcFrequency, String groupby, String storeTable, String calculateCode, String ddlNodeId, String ddlSql, String calcNodeId, String calcSql, String taskId, String calcStartTime, String calcEndTime, String lifeCycle, String timeWindow, Integer calculateStatus, String indicatorUniqCode, String indicatorRelaTable, String indicatorRelaKey, Long sceneId, Integer tableStatus) {
        super(CalcIndicatorCalculateOnce.CALC_INDICATOR_CALCULATE_ONCE);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, createdBy);
        set(4, updatedBy);
        set(5, indicatorCode);
        set(6, indicatorName);
        set(7, tableName);
        set(8, calculateEvent);
        set(9, calculateModel);
        set(10, calcFrequency);
        set(11, groupby);
        set(12, storeTable);
        set(13, calculateCode);
        set(14, ddlNodeId);
        set(15, ddlSql);
        set(16, calcNodeId);
        set(17, calcSql);
        set(18, taskId);
        set(19, calcStartTime);
        set(20, calcEndTime);
        set(21, lifeCycle);
        set(22, timeWindow);
        set(23, calculateStatus);
        set(24, indicatorUniqCode);
        set(25, indicatorRelaTable);
        set(26, indicatorRelaKey);
        set(27, sceneId);
        set(28, tableStatus);
    }
}
